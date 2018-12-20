package com.forum.main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
    private String token;
    private int status;
    private String imagePath;
    private Role role;

}
