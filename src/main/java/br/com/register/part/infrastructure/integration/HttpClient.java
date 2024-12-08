package br.com.register.part.infrastructure.integration;

import br.com.register.part.domain.model.exception.BusinessException;
import br.com.register.part.infrastructure.InfrastructureException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;

@Slf4j
@Service
@AllArgsConstructor
public class HttpClient extends HttpProperties {
    public Upload upload(MultipartFile file) {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", file.getResource());

        Upload upload;
        try {
            upload = super.getWebClient()
                    .post()
                    .uri(super.getUpload())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                            clientResponse.bodyToMono(Error.class)
                                    .map(Error::getMessage)
                                    .map(IntegrationBadRequest::new))
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                            clientResponse.bodyToMono(Error.class)
                                    .map(Error::getMessage)
                                    .map(InfrastructureException::new))
                    .bodyToMono(Upload.class)
                    .block();
        } catch (IntegrationBadRequest ex) {
            throw new BusinessException(ex.getMessage());
        } catch (Exception ex) {
            throw new InfrastructureException(ex.getMessage());
        }

        return upload;
    }

    public Download download(Long fileId) {
        Download download;
        try {
            download = super.getWebClient()
                    .post()
                    .uri(super.getDownload())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("id", String.valueOf(fileId))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                            clientResponse.bodyToMono(Error.class)
                                    .map(Error::getMessage)
                                    .map(IntegrationBadRequest::new))
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                            clientResponse.bodyToMono(Error.class)
                                    .map(Error::getMessage)
                                    .map(InfrastructureException::new))
                    .bodyToMono(Download.class)
                    .block();
        } catch (IntegrationBadRequest ex) {
            throw new BusinessException(ex.getMessage());
        } catch (Exception ex) {
            throw new InfrastructureException(ex.getMessage());
        }

        return download;
    }
}
