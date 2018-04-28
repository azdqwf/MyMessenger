package com.danila.diplom.client;

import com.danila.diplom.config.StageManager;
import com.danila.diplom.entity.User;
import com.danila.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

abstract public class Controller {
    @Autowired
    UserRepository userRepository;

    abstract void initManager(StageManager manager) throws IOException;


}
