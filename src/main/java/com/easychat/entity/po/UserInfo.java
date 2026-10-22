package com.easychat.entity.po;

import com.easychat.entity.constants.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 */
@Data
public class UserInfo implements Serializable {

    /**
     * 用户ID
     */
    @NotEmpty
    private String userId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 0:直接加入 1:同意后加好友
     */
    private Integer joinType;

    /**
     * 0:女 1:男
     */
    private Integer sex;

    /**
     * 密码（@JsonIgnore：序列化给前端时不输出）
     */
    @JsonIgnore
    private String password;

    /**
     * 个性签名
     */
    private String personalSignature;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 省份
     */
    private String areaName;

    /**
     * 城市
     */
    private String areaCode;

    /**
     * 最后离开时间
     */
    private Long lastOffTime;

    /**
     * 在线状态：派生字段，数据库里没有这一列
     */
    private Integer onlineType;

    /**
     * 派生字段的 getter 由手写逻辑计算，Lombok 不会再生成同名方法
     */
    public Integer getOnlineType() {
        if (lastLoginTime != null && lastOffTime != null && lastLoginTime.getTime() > lastOffTime) {
            return Constants.ONE;
        }
        return Constants.ZERO;
    }
}
