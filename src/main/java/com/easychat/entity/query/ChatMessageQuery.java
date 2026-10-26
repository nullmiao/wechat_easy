package com.easychat.entity.query;

import lombok.Data;

import java.util.List;

/**
 * 聊天消息查询参数（对应表 chat_message）
 *
 * 特殊字段说明：
 *   contactIdList    -> XML 里用 <foreach> 生成 contact_id in (...)
 *   lastReceiveTime  -> 非表字段，会话列表查询/排序用
 */
@Data
public class ChatMessageQuery extends BaseParam {

    /**
     * 消息自增ID
     */
    private Long messageId;

    /**
     * 会话ID（精确）
     */
    private String sessionId;

    /**
     * 会话ID（模糊）
     */
    private String sessionIdFuzzy;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 消息内容（精确）
     */
    private String messageContent;

    /**
     * 消息内容（模糊）
     */
    private String messageContentFuzzy;

    /**
     * 发送人ID（精确）
     */
    private String sendUserId;

    /**
     * 发送人ID（模糊）
     */
    private String sendUserIdFuzzy;

    /**
     * 发送人昵称（精确）
     */
    private String sendUserNickName;

    /**
     * 发送人昵称（模糊）
     */
    private String sendUserNickNameFuzzy;

    /**
     * 发送时间（毫秒时间戳）
     */
    private Long sendTime;

    /**
     * 接收联系人ID（精确）
     */
    private String contactId;

    /**
     * 接收联系人ID（模糊）
     */
    private String contactIdFuzzy;

    /**
     * 联系人类型 0:单聊 1:群聊
     */
    private Integer contactType;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件名（精确）
     */
    private String fileName;

    /**
     * 文件名（模糊）
     */
    private String fileNameFuzzy;

    /**
     * 文件类型
     */
    private Integer fileType;

    /**
     * 状态 0:正在发送 1:已发送
     */
    private Integer status;

    /**
     * 会话ID批量条件：XML 里 <foreach> 生成 contact_id in (...)
     */
    private List<String> contactIdList;

    /**
     * 最后接收时间（非表字段，会话列表查询用）
     */
    private Long lastReceiveTime;
}
