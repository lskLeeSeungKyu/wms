package com.server.boot.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String id;
    private String pw;
    private String name;
    private String email;
    private String hTel;
}
