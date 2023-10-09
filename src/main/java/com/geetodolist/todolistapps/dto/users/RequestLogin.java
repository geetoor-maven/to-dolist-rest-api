package com.geetodolist.todolistapps.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {

    @NotNull(message = "Email should not be empty")
    @Email(message = "Enter a valid email")
    public String email;

    @NotNull(message = "Password should not be empty")
    @Size(min = 5, message = "Password should be atleast 5 char")
    public String password;

}
