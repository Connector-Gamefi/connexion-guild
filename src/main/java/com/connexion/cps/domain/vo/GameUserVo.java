package com.connexion.cps.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class GameUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private Integer appID;

    private Integer channelID;

    private String uid;

    private String name;

    private String appName;

    private String loginName;

    private Date lastLoginTime;

    private Long lastLoginTimeStamp;

    private String lastLoginTimeStr;

    private Date createTime;

    private Long createTimeStamp;

    private String createTimeStr;

    private Date updateTime;

    private Integer accountType;

    private String deviceID;

    private String ip;

    private Long totalOnlineTimeSecs;

    private Date lastOnlineTime;

    private Long lastOnlineTimeStamp;

    private Date monthStartTime;

    private Long monthTotalPay;

    private String wallet;

    private String channelName;

    private Integer status;

    private String email;

    private String roleName;

    private String roleLevel;

    private List<ChannelGameUserTotalDataVo> totalData;

    private String invitationCode;
}
