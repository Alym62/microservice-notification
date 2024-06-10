package org.ms.email.services;

import org.ms.email.enums.StatusEmail;
import org.ms.email.models.EmailModel;
import org.ms.email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailService {
    private final EmailRepository repository;

    private final JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public EmailService(EmailRepository repository, JavaMailSender sender) {
        this.repository = repository;
        this.sender = sender;
    }

    @Transactional
    public void sendEmail(EmailModel emailModel) {
        try {
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());

            sender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException exception) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            repository.save(emailModel);
        }
    }
}
