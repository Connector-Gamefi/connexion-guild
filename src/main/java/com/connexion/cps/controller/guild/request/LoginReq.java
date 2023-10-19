package com.connexion.cps.controller.guild.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginReq {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
