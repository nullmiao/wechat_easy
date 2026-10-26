package com.easychat.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户信息 DTO —— 登录成功后写入 Redis，key 形如：
 *   easychat:ws:token:<token>        → 本对象（AOP/WS 握手时按 token 取）
 *   easychat:ws:token:userid<userId> → token 字符串（按 userId 反查 token）
 *
 * 为什么叫 DTO 而不是 VO：
 *   - DTO（Data Transfer Object）用于"服务端内部/跨进程传输"，这里是要塞进 Redis 的会话快照；
 *   - VO 是"返回给前端看的视图"，比如 UserInfoVO。
 *   两者都不该直接等于 PO（数据库表映射）。
 *
 * 设计要点：
 *   1. implements Serializable + serialVersionUID：原项目按 Java 序列化规范写，
 *      现在 Redis 用的是 JSON 序列化，理论上不需要，但保留以兼容历史/WS 传输；
 *   2. @JsonIgnoreProperties(ignoreUnknown = true)：Redis 里可能残留旧版本 JSON（多/少字段），
 *      反序列化时忽略未知字段，避免上线变更字段后旧 token 全部反序列化失败；
 *   3. 没有密码等敏感字段：token 里只放鉴权必需信息；
 *   4. admin 是"登录那一刻的快照"：之后改 admin.emails 配置，已签发的 token 不会变
 *      （这是原项目的一个设计缺陷，P9 复盘会讨论）。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenUserInfoDto implements Serializable {

    private static final long serialVersionUID = -6910208948981307451L;

    /**
     * 登录令牌（也是 Redis key 的一部分）
     */
    private String token;

    /**
     * 用户ID（U 开头）
     */
    private String userId;

    /**
     * 昵称（冗余字段：改昵称时要把 Redis 里的 token 一起更新，P4 会用到 resetTokenUserInfo）
     */
    private String nickName;

    /**
     * 是否管理员（登录时按 admin.emails 计算后快照进来）
     */
    private Boolean admin;
}
