package com.studying.diploma.dto;

import com.studying.diploma.model.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {

    private String username;
    private String password;
    private Set<Role> roles;
    private String name;
    private String surname;
}