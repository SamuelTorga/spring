package br.com.samueltorga.spring.controller.dto.user;

import lombok.Data;

import java.time.Instant;

@Data
public class UserResponse {

    private Integer id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Instant created;
    private Instant updated;

}
