package com.easychat.entity.query;

import lombok.Data;

/**
 * 用户信息查询参数（对应表 user_info）
 *
 * 命名约定：
 *   普通字段   -> 等值条件（where email = #{query.email}）
 *   XxxFuzzy   -> 模糊条件（where email like concat('%', #{query.emailFuzzy}, '%')）
 *   XxxStart / XxxEnd -> 范围条件（>= / <=）
 *
 * 注意：createTime 在这里是 String（不是 PO 里的 Date），配合 Start/End 做范围查询，
 *      这是原项目的写法，XML 里按字符串比较，保持类型一致即可。
 */
@Data
public class UserInfoQuery extends BaseParam {

    /**
     * 用户ID（精确）
     */
    private String userId;

    /**
     * 用户ID（模糊）
     */
    private String userIdFuzzy;

    /**
     * 邮箱（精确）
     */
    private String email;

    /**
     * 邮箱（模糊）
     */
    private String emailFuzzy;

    /**
     * 昵称（精确）
     */
    private String nickName;

    /**
     * 昵称（模糊）
     */
    private String nickNameFuzzy;

    /**
     * 0:直接加入 1:同意后加好友
     */
    private Integer joinType;

    /**
     * 0:女 1:男
     */
    private Integer sex;

    /**
     * 密码（精确）
     */
    private String password;

    /**
     * 密码（模糊）
     */
    private String passwordFuzzy;

    /**
     * 个性签名（精确）
     */
    private String personalSignature;

    /**
     * 个性签名（模糊）
     */
    private String personalSignatureFuzzy;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间（精确）
     */
    private String createTime;

    /**
     * 创建时间范围起始（>=）
     */
    private String createTimeStart;

    /**
     * 创建时间范围结束（<=）
     */
    private String createTimeEnd;

    /**
     * 最后登录时间（毫秒时间戳）
     */
    private Long lastLoginTime;

    /**
     * 省份（精确）
     */
    private String areaName;

    /**
     * 省份（模糊）
     */
    private String areaNameFuzzy;

    /**
     * 城市编号（精确）
     */
    private String areaCode;

    /**
     * 城市编号（模糊）
     */
    private String areaCodeFuzzy;

    /**
     * 最后离开时间（毫秒时间戳）
     */
    private Long lastOffTime;
}
