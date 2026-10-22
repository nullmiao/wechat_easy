package com.easychat.entity.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 会话信息
 */
@Data
public class ChatSession implements Serializable {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 最后接受的消息
     */
    private String lastMessage;

    /**
     * 最后接受消息时间毫秒
     */
    private Long lastReceiveTime;
}
