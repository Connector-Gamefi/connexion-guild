package com.connexion.cps.controller.openapi.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OpenApiConfigGetReq {

    @NotNull
    private Integer appID;

    @NotNull
    private Integer channelID;
}
