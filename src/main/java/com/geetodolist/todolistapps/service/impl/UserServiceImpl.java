package com.geetodolist.todolistapps.service.impl;

import com.geetodolist.todolistapps.dto.users.RequestUser;
import com.geetodolist.todolistapps.entity.Users;
import com.geetodolist.todolistapps.exception.ItemAlreadyExistException;
import com.geetodolist.todolistapps.repository.UserRepository;
import com.geetodolist.todolistapps.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public Users createUser(RequestUser requestUser) {
        // validasi jika email sudah terdaftar di db
        if (userRepository.existsByEmail(requestUser.getEmail())){
            throw new ItemAlreadyExistException("User al ready register with email : " + requestUser.getEmail());
        }
        // validasi jika username sudah terdaftar di db
        if (userRepository.existsByUsername(requestUser.getUsername())){
            throw new ItemAlreadyExistException("User al ready register with username : " + requestUser.getUsername());
        }
        Users theUser = new Users();
        BeanUtils.copyProperties(requestUser, theUser);

        theUser.setUser_id(UUID.randomUUID().toString());
        theUser.setPassword(passwordEncoder.encode(theUser.getPassword()));
        return userRepository.save(theUser);
    }
}
