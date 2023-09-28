package com.connexion.cps.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class DataDetail {
    private String wallet;

    private Long uid;

    private List<ErcData> erc20;

    private List<ErcData> erc721;

    private String updateTime;

    private List<ErcData> orderList;
}