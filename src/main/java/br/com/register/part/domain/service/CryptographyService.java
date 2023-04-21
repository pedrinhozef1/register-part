package br.com.register.part.domain.service;

import br.com.register.part.domain.model.exception.CryptographyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

@Slf4j
@Service
public class CryptographyService {

    @Value("cryptography.secret-key")
    private String SECRET_KEY;
    @Value("cryptography.salt")
    private String SALT;

    public String encrypt(String text) {
        String encrypted = "";
        try {
            byte[] ivSpec = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec iv = new IvParameterSpec(ivSpec);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

            encrypted = Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception ex) {
            log.error("Error when encrypting: {}", ex.getMessage());
            throw new CryptographyException("Error when encrypting " + ex.getMessage());
        }

        return encrypted;
    }
    public String decrypt(String strToDecrypt) {
        String decrypted = "";
        try{
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);

            decrypted = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception ex) {
            log.error("Error when encrypting: {}" + ex.getMessage());
            throw new CryptographyException("Error when encrypting " + ex.getMessage());
        }

        return decrypted;
    }
}
