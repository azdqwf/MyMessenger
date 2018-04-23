package com.danila.diplom.service;

import com.danila.diplom.entity.User;
import com.danila.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User save(User entity) {
        return userRepository.save(entity);
    }

    public User update(User entity) {
        return userRepository.save(entity);
    }


    public void delete(User entity) {
        userRepository.delete(entity);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
