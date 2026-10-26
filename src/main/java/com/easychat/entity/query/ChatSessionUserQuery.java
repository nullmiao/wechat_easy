package com.easychat.entity.query;

import lombok.Data;

/**
 * 会话用户查询参数（对应表 chat_session_user）
 */
@Data
public class ChatSessionUserQuery extends BaseParam {

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
     * 会话ID（精确）
     */
    private String sessionId;

    /**
     * 会话ID（模糊）
     */
    private String sessionIdFuzzy;

    /**
     * 联系人名称（精确）
     */
    private String contactName;

    /**
     * 联系人名称（模糊）
     */
    private String contactNameFuzzy;

    /**
     * 最后接收消息时间（毫秒时间戳，非表字段，会话列表排序用）
     */
    private Long lastReceiveTime;
}
