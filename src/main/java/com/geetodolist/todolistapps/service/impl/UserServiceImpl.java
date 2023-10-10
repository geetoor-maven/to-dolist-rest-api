package com.geetodolist.todolistapps.service.impl;

import com.geetodolist.todolistapps.dto.users.RequestLogin;
import com.geetodolist.todolistapps.dto.users.RequestUser;
import com.geetodolist.todolistapps.entity.Users;
import com.geetodolist.todolistapps.exception.ItemAlreadyExistException;
import com.geetodolist.todolistapps.exception.ResourceNotFoundException;
import com.geetodolist.todolistapps.repository.UserRepository;
import com.geetodolist.todolistapps.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
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

    @Override
    public Users getUserLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    @Override
    public void validateEmailAndPassword(RequestLogin requestLogin) {
        // find userw with email
        Users theUser = userRepository.findByEmail(requestLogin.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User Not Found With Email : " + requestLogin.getEmail()));

        // validate password with passwordEncoder
        if (!passwordEncoder.matches(requestLogin.getPassword(), theUser.getPassword())){
            throw new ResourceNotFoundException("User With Email and Password not Found");
        }
    }

    @Override
    public Users readUserByUserId() {
        String user = getUserLogin().getUser_id();
        return userRepository.findById(user).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Transactional
    @Override
    public Users updateUser(RequestUser requestUser) {
        // validasi jika email sudah terdaftar di db
        if (userRepository.existsByEmail(requestUser.getEmail())){
            throw new ItemAlreadyExistException("User al ready register with email : " + requestUser.getEmail());
        }
        // validasi jika username sudah terdaftar di db
        if (userRepository.existsByUsername(requestUser.getUsername())){
            throw new ItemAlreadyExistException("User al ready register with username : " + requestUser.getUsername());
        }

        Users users = readUserByUserId();

        users.setName(requestUser.getName() != null ? requestUser.getName() : users.getName());
        users.setEmail(requestUser.getEmail() != null ? requestUser.getEmail() : users.getEmail() );
        users.setPassword(requestUser.getPassword() != null ? passwordEncoder.encode(requestUser.getPassword()) : users.getPassword());
        users.setUsername(requestUser.getUsername() != null ? requestUser.getUsername() : users.getUsername());
        users.setAddress(requestUser.getAddress() != null ? requestUser.getAddress() : users.getAddress());

        return userRepository.save(users);
    }

    @Transactional
    @Override
    public void deleteUser() {
        Users users = readUserByUserId();
        userRepository.delete(users);
    }
}
