package com.connexion.cps.domain.game;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @since 2023-10-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("channel")
public class ChannelEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("parentID")
    private Integer parentID;

    @TableField("level")
    private Integer level;

    @NotEmpty
    @TableField("name")
    private String name;

    @TableField("fullName")
    private String fullName;

    @NotEmpty
    @Email
    @TableField("email")
    private String email;

    @TableField("phoneNum")
    private String phoneNum;

    @TableField("loginName")
    private String loginName;

    @TableField("loginPwd")
    private String loginPwd;

    @TableField("channelType")
    private Integer channelType;

    @TableField("settleType")
    private Integer settleType;

    @TableField("createChild")
    private Integer createChild;

    @TableField("maxChildNum")
    private Integer maxChildNum;

    @TableField("banState")
    private Integer banState;

    @TableField("address")
    private String address;

    @TableField("busName")
    private String busName;

    @TableField("busQQ")
    private String busQQ;

    @TableField("busWX")
    private String busWX;

    @TableField("busTel")
    private String busTel;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("lastLoginTime")
    private LocalDateTime lastLoginTime;

    @TableField("deleted")
    private Integer deleted;

    @NotEmpty
    @TableField("wallet_address")
    private String walletAddress;

    @NotEmpty
    @TableField("invitation_code")
    private String referralCode;

    /** Parent channel name, not stored in inventory */
    @TableField(exist = false)
    private String parentName;

    /** not stored in inventory */
    @TableField(exist = false)
    private String ids;

}
