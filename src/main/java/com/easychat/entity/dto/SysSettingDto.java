package com.easychat.entity.dto;

import com.easychat.entity.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统设置 DTO —— 整个对象以 JSON 形式存在 Redis 的一个 key 里：easychat:syssetting:
 *
 * ⚠️ 重点注意：这个项目**没有 sys_setting 表**，系统设置只存 Redis！
 *    所以 Redis 丢了 → 全部回到这里的默认值（这是"Redis 被迫当真相源"的设计缺陷，P9 会讲）。
 *
 * 字段分两类：
 *   1. 业务限制类：群数量上限、群成员上限、图片/视频/文件大小上限（P7 上传校验用）；
 *   2. 机器人配置类：机器人 uid/昵称/欢迎语（注册时加机器人好友、群欢迎语用）。
 *
 * 默认值就是"Redis 里没有配置时"的兜底值，代码里 new SysSettingDto() 即可用。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysSettingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单用户最多可创建的群数量
     */
    private Integer maxGroupCount = 5;

    /**
     * 单个群最大成员数
     */
    private Integer maxGroupMemberCount = 500;

    /**
     * 单张图片最大 MB
     */
    private Integer maxImageSize = 2;

    /**
     * 单个视频最大 MB
     */
    private Integer maxVideoSize = 5;

    /**
     * 单个文件最大 MB
     */
    private Integer maxFileSize = 5;

    /**
     * 机器人用户 id（注册时自动加机器人好友）
     */
    private String robotUid = Constants.ROBOT_UID;

    /**
     * 机器人昵称
     */
    private String robotNickName = "EasyChat";

    /**
     * 机器人欢迎语
     */
    private String robotWelcome = "欢迎使用EasyChat";
}
