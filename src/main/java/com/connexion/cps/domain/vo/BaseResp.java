package com.connexion.cps.domain.vo;

import lombok.Data;

@Data
public class BaseResp<T> {

    public Integer code;

    public T data;

    public String msg;

    public Integer total;
}
