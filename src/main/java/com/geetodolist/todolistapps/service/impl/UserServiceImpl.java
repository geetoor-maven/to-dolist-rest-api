package com.geetodolist.todolistapps.service.impl;

import com.geetodolist.todolistapps.dto.users.RequestUser;
import com.geetodolist.todolistapps.entity.Users;
import com.geetodolist.todolistapps.exception.ItemAlreadyExistException;
import com.geetodolist.todolistapps.repository.UserRepository;
import com.geetodolist.todolistapps.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Users createUser(RequestUser requestUser) {
        if (userRepository.existsByEmail(requestUser.getEmail())){
            throw new ItemAlreadyExistException("User al ready register with email : " + requestUser.getEmail());
        }
        Users theUser = new Users();
        BeanUtils.copyProperties(requestUser, theUser);

        theUser.setUser_id(UUID.randomUUID().toString());

        return userRepository.save(theUser);
    }
}
