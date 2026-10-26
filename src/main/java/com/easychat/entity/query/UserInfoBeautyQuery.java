package com.easychat.entity.query;

import lombok.Data;

/**
 * 靓号查询参数（对应表 user_info_beauty）
 */
@Data
public class UserInfoBeautyQuery extends BaseParam {

    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 邮箱（精确）
     */
    private String email;

    /**
     * 邮箱（模糊）
     */
    private String emailFuzzy;

    /**
     * 用户ID（精确）
     */
    private String userId;

    /**
     * 用户ID（模糊）
     */
    private String userIdFuzzy;

    /**
     * 状态0:未使用 1:已使用
     */
    private Integer status;
}
