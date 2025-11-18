package org.example.saToken.dto;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest{

    private String telephone;

    private String inviteCode;

    private String password;

}