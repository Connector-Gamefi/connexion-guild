package com.connexion.cps.controller.openapi.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OpenApiConfigSaveReq {

    @NotNull
    private Integer appID;

    @NotNull
    private Integer channelID;

    @NotBlank
    private String label;

    @NotNull
    private String ipWhiteList;
}
