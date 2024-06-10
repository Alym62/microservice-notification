package org.ms.email.consumers;

import org.ms.email.dtos.EmailRecordDTO;
import org.ms.email.models.EmailModel;
import org.ms.email.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {
    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDTO dto) {
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(dto, emailModel);

        emailService.sendEmail(emailModel);
    }
}
