package com.connexion.cps.controller.guild.response;

import com.connexion.cps.domain.game.ChannelEntity;
import lombok.Data;

/**
 * CPS login result
 */
@Data
public class LoginResultVO {
    protected String uid;
    protected String username;
    protected String roleId;
    protected String token;
    protected String preToken;
    protected String email;
    protected String phone;

    protected Integer isActive;

    private Integer level;
    private Integer settleType;
    private String channelName;
    private Integer createChild;
    private Integer maxChildNum;

    public LoginResultVO(ChannelEntity channel, String token) {
        this.uid = channel.getId() + "";
        this.username = channel.getLoginName();
        this.channelName = channel.getName();
        this.roleId = "0";
        this.token = token;
        this.email = channel.getEmail();
        this.phone = channel.getPhoneNum();

        this.level = channel.getLevel();
        this.settleType = channel.getSettleType();

        this.createChild = channel.getCreateChild();
        this.maxChildNum = channel.getMaxChildNum();
    }
}
