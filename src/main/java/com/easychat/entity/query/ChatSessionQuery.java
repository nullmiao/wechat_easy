package com.easychat.entity.query;

import lombok.Data;

/**
 * 会话查询参数（对应表 chat_session）
 */
@Data
public class ChatSessionQuery extends BaseParam {

    /**
     * 会话ID（精确）
     */
    private String sessionId;

    /**
     * 会话ID（模糊）
     */
    private String sessionIdFuzzy;

    /**
     * 最后一条消息（精确）
     */
    private String lastMessage;

    /**
     * 最后一条消息（模糊）
     */
    private String lastMessageFuzzy;

    /**
     * 最后接收消息时间（毫秒时间戳）
     */
    private Long lastReceiveTime;
}
