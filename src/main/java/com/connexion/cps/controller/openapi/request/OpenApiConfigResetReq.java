package com.connexion.cps.controller.openapi.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OpenApiConfigResetReq {

    @NotNull
    private Integer id;

}
