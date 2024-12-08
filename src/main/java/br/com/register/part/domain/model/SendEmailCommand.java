package br.com.register.part.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailCommand {
    private List<String> recipients;
    private String subject;
    private String message;

    public static SendEmailCommand buildVerifyAccountEmail(User user, String verifyUserCallbackUrl) {
        var verifyUrl = verifyUserCallbackUrl.concat("?code=").concat(user.getVerificationCode());

        return SendEmailCommand.builder()
                .recipients(List.of(user.getEmail().getAddress()))
                .subject("Por favor, verifique o seu e-mail cadastrado!")
                .message(String.format("<p>Olá senhor(a) %s</p>" +
                        "<p>O seu cadastro no sistema foi realizado com sucesso, para utilizar o sistema é necessário habilitar o seu usuário clicando no botão abaixo!</p>" +
                        "<h3><a href=%s>Verificar!</a></h3>" +
                        "<strong>Ao clicar no botão o sistema irá automáticamente habilitar o seu usuário e lhe redirecionar para o sistema já autenticado!</strong>" +
                        "<p>Obrigado!</p>", user.getName(), verifyUrl))
                .build();
    }

    public static SendEmailCommand buildForgotPasswordEmail(User userJpaEntity, String token, String resetPasswordRedirectUrl) {
        var url = resetPasswordRedirectUrl.concat("?token=").concat(token);
        return SendEmailCommand.builder()
                .recipients(List.of(userJpaEntity.getEmail().getAddress()))
                .subject("Solicitação de redefinição de senha")
                .message(String.format("<p>Olá senhor(a) %s</p>" +
                        "<p>Foi solicitado por meio do sistema a redefinição de senha uma conta cadastrada com esse e-mail.</p>" +
                        "<strong>Importante: caso não tenha sido solicitado por você lhe orientamos a ignorar esse e-mail e entrar no sistema para atualizar a sua senha!</strong>" +
                        "<h3><a href=%s>Alterar senha</a></h3>" +
                        "<strong>Clique no botão acima para redefinir a sua senha.</strong>" +
                        "<p>Obrigado!</p>", userJpaEntity.getName(), url))
                .build();
    }
}
