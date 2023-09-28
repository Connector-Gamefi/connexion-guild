package com.connexion.cps.controller.openapi.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OpenApiConfigUpdateReq {

    @NotNull
    private Integer id;

    @NotNull
    private String ipWhiteList;
}
