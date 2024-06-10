package org.ms.user.services;

import org.ms.user.dtos.UserRecordDTO;
import org.ms.user.models.UserModel;
import org.ms.user.producers.UserProducer;
import org.ms.user.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository repository;

    private final UserProducer producer;

    public UserService(UserRepository repository, UserProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Transactional
    public UserModel insert(UserRecordDTO dto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(dto, userModel);

        var userSaved = repository.save(userModel);

        producer.publishMessageEmail(userModel);

        return userSaved;
    }
}
