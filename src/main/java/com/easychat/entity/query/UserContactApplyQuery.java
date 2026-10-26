package com.easychat.entity.query;

import lombok.Data;

/**
 * 联系人申请查询参数（对应表 user_contact_apply）
 */
@Data
public class UserContactApplyQuery extends BaseParam {

    /**
     * 自增ID
     */
    private Integer applyId;

    /**
     * 申请人ID（精确）
     */
    private String applyUserId;

    /**
     * 申请人ID（模糊）
     */
    private String applyUserIdFuzzy;

    /**
     * 接收人ID（精确）
     */
    private String receiveUserId;

    /**
     * 接收人ID（模糊）
     */
    private String receiveUserIdFuzzy;

    /**
     * 联系人类型 0:好友 1:群组
     */
    private Integer contactType;

    /**
     * 联系人群组ID（精确）
     */
    private String contactId;

    /**
     * 联系人群组ID（模糊）
     */
    private String contactIdFuzzy;

    /**
     * 最后申请时间（毫秒时间戳）
     */
    private Long lastApplyTime;

    /**
     * 状态0:待同意 1:同意 2:已拒绝 3:拉黑
     */
    private Integer status;

    /**
     * 申请信息（精确）
     */
    private String applyInfo;

    /**
     * 申请信息（模糊）
     */
    private String applyInfoFuzzy;

    /**
     * 联表开关：是否补申请人/群信息
     */
    private Boolean queryContactInfo;

    /**
     * 最后申请时间戳（非表字段，部分查询/排序用）
     */
    private Long lastApplyTimestamp;
}
