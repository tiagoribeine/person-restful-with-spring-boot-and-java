package github.com.tiagoribeine.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.com.tiagoribeine.config.EmailConfig;
import github.com.tiagoribeine.data.dto.request.EmailRequestDTO;
import github.com.tiagoribeine.mail.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class EmailService {

    @Autowired //Injetando instância
    private EmailSender emailSender;

    @Autowired
    private EmailConfig emailConfigs;

    //Email sem anexos
    public void sendSimpleEmail(EmailRequestDTO emailRequest){
        emailSender.
                to(emailRequest.getTo())
                .withSubject(emailRequest.getSubject())
                .withMessage(emailRequest.getSubject())
                .send(emailConfigs);
    }

    //Email com anexos
    public void setEmailWithAttachment(String emailRequestJson, MultipartFile attachment){
         //Desserializando:
        File tempFile = null;
        try {
            EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);
            tempFile = File.createTempFile("attachment", attachment.getOriginalFilename());
            attachment.transferTo(tempFile); //Destino

            emailSender.
                to(emailRequest.getTo())
                .withSubject(emailRequest.getSubject())
                .withMessage(emailRequest.getSubject())
                .attach(tempFile.getAbsolutePath())
                .send(emailConfigs);


        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing email request JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing the attachments", e);
        } finally {
            // Deletando o arquivo temporário em disco
            if(tempFile != null && tempFile.exists()) tempFile.delete();
        }

    }
}
