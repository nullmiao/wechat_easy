package com.easychat.entity.query;

import lombok.Data;

/**
 * 联系人查询参数（对应表 user_contact）
 *
 * 四个 Boolean 是"联表开关"，本身不是 SQL 条件：
 *   queryContactUserInfo -> 是否联表补联系人昵称
 *   queryGroupInfo       -> 是否联表补群信息（群名/成员数）
 *   excludeMyGroup       -> 是否排除"自己创建的群"
 *   queryUserInfo        -> 是否联表查用户信息
 * XML 里用 <if test="query.queryUserInfo"> 控制是否拼接对应的 join/select 字段。
 */
@Data
public class UserContactQuery extends BaseParam {

    /**
     * 用户ID（精确）
     */
    private String userId;

    /**
     * 用户ID（模糊）
     */
    private String userIdFuzzy;

    /**
     * 联系人ID/群组ID（精确）
     */
    private String contactId;

    /**
     * 联系人ID/群组ID（模糊）
     */
    private String contactIdFuzzy;

    /**
     * 联系人类型 0:好友 1:群组
     */
    private Integer contactType;

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
     * 状态 0:非好友 1:好友 2:已删除 3:拉黑
     */
    private Integer status;

    /**
     * 最后更新时间（精确）
     */
    private String lastUpdateTime;

    /**
     * 最后更新时间范围起始（>=）
     */
    private String lastUpdateTimeStart;

    /**
     * 最后更新时间范围结束（<=）
     */
    private String lastUpdateTimeEnd;

    /**
     * 联表开关：是否补联系人用户信息
     */
    private Boolean queryContactUserInfo;

    /**
     * 联表开关：是否补群信息
     */
    private Boolean queryGroupInfo;

    /**
     * 是否排除自己创建的群
     */
    private Boolean excludeMyGroup;

    /**
     * 联表开关：是否查用户信息
     */
    private Boolean queryUserInfo;

    /**
     * 状态批量条件：XML 里用 <foreach> 生成 status in (...)
     */
    private Integer[] statusArray;
}
