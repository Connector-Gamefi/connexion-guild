package com.connexion.cps.controller.common.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TokenListReqData {

    @NotNull
    private Integer appId;

    @NotEmpty
    private String type;
}
