package com.easychat.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录/用户信息 VO（返回给前端）
 *
 * 和 PO（UserInfo）的区别：
 *   1. 没有 password 等敏感字段（PO 是靠 @JsonIgnore 兜着，VO 是根本不放）；
 *   2. 多出 token / admin / contactStatus 这些"非数据库字段"：
 *      - token：登录成功后才有；
 *      - admin：登录时按 admin.emails 算出来；
 *      - contactStatus：查看别人资料时，表示"我和他的关系"（好友/被删/被拉黑…，P4 用）。
 *
 * 为什么不让接口直接返回 PO：
 *   PO 和表结构强绑定，表加一列就可能把内部字段吐给前端；VO 是"对外契约"，只放前端需要的。
 * （原项目有些地方图省事直接返回 PO，靠 @JsonIgnore 挡住密码——不推荐，但存在。）
 */
@Data
public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 0:女 1:男
     */
    private Integer sex;

    /**
     * 加好友方式：0 直接加入 1 同意后加入
     */
    private Integer joinType;

    /**
     * 个性签名
     */
    private String personalSignature;

    /**
     * 地区编号
     */
    private String areaCode;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 登录令牌（登录接口返回，前端后续请求放在 header token 里）
     */
    private String token;

    /**
     * 是否管理员（前端据此显示后台入口）
     */
    private Boolean admin;

    /**
     * 联系人状态（0 非好友、1 好友、2 已删除、3 被删除、4 拉黑、5 被拉黑、6 首次被拉黑）
     * 用于"查看他人资料"时展示关系；登录接口一般不填
     */
    private Integer contactStatus;
}
