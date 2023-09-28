package com.connexion.cps.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum DeletedEnum {

    //no del
    UNDELETED(0),

    //deleted
    DELETED(1);

    @Getter
    private int state;
}
