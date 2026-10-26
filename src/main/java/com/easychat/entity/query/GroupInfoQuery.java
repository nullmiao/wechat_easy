package com.easychat.entity.query;

import lombok.Data;

/**
 * 群组查询参数（对应表 group_info）
 *
 * 两个 Boolean 是联表开关：
 *   queryGroupOwnerName -> 是否补群主昵称
 *   queryMemberCount    -> 是否统计群成员数
 */
@Data
public class GroupInfoQuery extends BaseParam {

    /**
     * 群ID（精确）
     */
    private String groupId;

    /**
     * 群ID（模糊）
     */
    private String groupIdFuzzy;

    /**
     * 群名称（精确）
     */
    private String groupName;

    /**
     * 群名称（模糊）
     */
    private String groupNameFuzzy;

    /**
     * 群主ID（精确）
     */
    private String groupOwnerId;

    /**
     * 群主ID（模糊）
     */
    private String groupOwnerIdFuzzy;

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
     * 群公告（精确）
     */
    private String groupNotice;

    /**
     * 群公告（模糊）
     */
    private String groupNoticeFuzzy;

    /**
     * 0:直接加入 1:管理员同意后加入
     */
    private Integer joinType;

    /**
     * 状态 1:正常 0:解散
     */
    private Integer status;

    /**
     * 联表开关：是否补群主昵称
     */
    private Boolean queryGroupOwnerName;

    /**
     * 联表开关：是否统计群成员数
     */
    private Boolean queryMemberCount;
}
