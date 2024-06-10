package org.ms.user.producers;

import org.ms.user.dtos.EmailRecordDTO;
import org.ms.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {
    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(UserModel userModel) {
        var dto = new EmailRecordDTO(
                userModel.getUserId(),
                userModel.getEmail(),
                "Cadastro realizado com sucesso! Seja bem-vindo.",
                String.format("%s, seja bem-vindo(a)! Agradecemos o seu cadastro, aproveite todos os recursos da nossa plataforma Geek.",
                        userModel.getName())
        );

        rabbitTemplate.convertAndSend("", routingKey, dto);
    }
}
