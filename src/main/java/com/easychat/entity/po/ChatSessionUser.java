package com.easychat.entity.po;

import com.easychat.entity.enums.UserContactTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 会话用户（每个会话成员一行）
 */
@Data
public class ChatSessionUser implements Serializable {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 联系人ID
     */
    private String contactId;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 非表字段：会话列表联表带出来的最后一条消息
     */
    private String lastMessage;

    /**
     * 非表字段：最后接收时间
     */
    private Long lastReceiveTime;

    /**
     * 非表字段：联系人类型（0 好友 / 1 群），由 getter 根据 contactId 前缀推导
     */
    private Integer contactType;

    /**
     * 非表字段：群成员数
     */
    private Integer memberCount;

    /**
     * 派生 getter：U 开头=好友(0)，G 开头=群(1)；Lombok 不会再生成同名方法
     */
    public Integer getContactType() {
        UserContactTypeEnum contactTypeEnum = UserContactTypeEnum.getByPrefix(contactId);
        return contactTypeEnum == null ? null : contactTypeEnum.getType();
    }
}
