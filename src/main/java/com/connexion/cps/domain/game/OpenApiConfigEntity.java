package com.connexion.cps.domain.game;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * openapi
 * </p>
 *
 * @author <a href="https://www.fengwenyi.com?code">Erwin Feng</a>
 * @since 2022-11-16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("open_api_config")
public class OpenApiConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("appID")
    private Integer appID;

    @TableField("channelID")
    private Integer channelID;

    /**
     * label
     */
    @TableField("label")
    private String label;

    /**
     * appkey
     */
    @TableField("appKey")
    private String appKey;

    /**
     * secretKey
     */
    @TableField("secretKey")
    private String secretKey;

    /**
     * ip
     */
    @TableField("ip_white_list")
    private String ipWhiteList;

    @TableField("deleted")
    private Integer deleted;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;


}
