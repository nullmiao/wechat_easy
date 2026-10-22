package com.easychat.entity.po;

import com.easychat.entity.enums.UserContactApplyStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 联系人申请
 */
@Data
public class UserContactApply implements Serializable {

    /**
     * 自增ID
     */
    private Integer applyId;

    /**
     * 申请人id
     */
    private String applyUserId;

    /**
     * 接收人ID
     */
    private String receiveUserId;

    /**
     * 联系人类型 0:好友 1:群组
     */
    private Integer contactType;

    /**
     * 联系人群组ID
     */
    private String contactId;

    /**
     * 最后申请时间（毫秒时间戳）
     */
    private Long lastApplyTime;

    /**
     * 状态0:待同意 1:同意 2:已拒绝 3:拉黑
     */
    private Integer status;

    /**
     * 申请信息
     */
    private String applyInfo;

    /**
     * 非表字段：申请人/群名称
     */
    private String contactName;

    /**
     * 非表字段：状态中文，由 getter 根据 status 推导
     */
    private String statusName;

    /**
     * 派生 getter：Lombok 不会再生成同名方法
     */
    public String getStatusName() {
        UserContactApplyStatusEnum statusEnum = UserContactApplyStatusEnum.getByStatus(status);
        return statusEnum == null ? null : statusEnum.getDesc();
    }
}
