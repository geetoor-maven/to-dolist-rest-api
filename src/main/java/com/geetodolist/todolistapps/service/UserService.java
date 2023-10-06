package com.geetodolist.todolistapps.service;

import com.geetodolist.todolistapps.dto.users.RequestUser;
import com.geetodolist.todolistapps.entity.Users;

public interface UserService {
    Users createUser(RequestUser requestUser);
}
