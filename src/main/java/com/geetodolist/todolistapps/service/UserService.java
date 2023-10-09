package com.geetodolist.todolistapps.service;

import com.geetodolist.todolistapps.dto.users.RequestLogin;
import com.geetodolist.todolistapps.dto.users.RequestUser;
import com.geetodolist.todolistapps.entity.Users;

public interface UserService {
    Users createUser(RequestUser requestUser);
    Users getUserLogin();
    void validateEmailAndPassword(RequestLogin requestLogin);
    Users readUserByUserId();
}
