package com.connexion.cps.domain.vo.paymentAddress;

import lombok.Data;

@Data
public class ConfigData {
    private Integer id;

    private Integer appId;

    private String type;

    private String address;

    private String paymentAddress;

    private String name;

    private String symbol;

    private Integer status;
}
