package com.easychat.entity.enums;

import com.easychat.utils.StringTools;

/**
 * 联系人状态（user_contact.status）
 *
 * 这个枚举是本项目"一张表表达双向关系"的核心，7 个状态要结合"谁看这行数据"来理解：
 *
 *   0 NOT_FRIEND          非好友（一般不会落库，是默认值/兜底）
 *   1 FRIEND              好友（正常关系）
 *   2 DEL                 我删除了对方（我这边看到"已删除"）
 *   3 DEL_BE              我被对方删除（我这边还不知道，发消息时会收到 902）
 *   4 BLACKLIST           我拉黑了对方
 *   5 BLACKLIST_BE        我被对方拉黑（我这边还不知道）
 *   6 BLACKLIST_BE_FIRST  首次发现"被对方拉黑"（从 5 演进来的中间态，提示一次）
 *
 * 为什么要有 2/3、4/5 成对出现：
 *   user_contact 每行是"某个用户视角的一段关系"，A 删了 B 只改 A 视角的行，
 *   B 视角的行还是 FRIEND——直到 B 给 A 发消息，服务端检查 A 的视角发现 DEL_BE，
 *   才会把 B 的视角也改成 DEL_BE（这就是 3 和 5 的来历）。
 * 详细的双向状态机在 P4 讲"联系人 7 态"时展开。
 */
public enum UserContactStatusEnum {

    /**
     * 非好友
     */
    NOT_FRIEND(0, "非好友"),

    /**
     * 好友
     */
    FRIEND(1, "好友"),

    /**
     * 已删除好友（我删的）
     */
    DEL(2, "已删除好友"),

    /**
     * 被好友删除（对方删的，我这边还没处理）
     */
    DEL_BE(3, "被好友删除"),

    /**
     * 已拉黑好友（我拉黑的）
     */
    BLACKLIST(4, "已拉黑好友"),

    /**
     * 被好友拉黑（对方拉黑的）
     */
    BLACKLIST_BE(5, "被好友拉黑"),

    /**
     * 首次被好友拉黑（用于提示一次）
     */
    BLACKLIST_BE_FIRST(6, "首次被好友拉黑");

    private Integer status;

    private String desc;

    UserContactStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    /**
     * 按枚举名取（如 "FRIEND"），找不到返回 null
     */
    public static UserContactStatusEnum getByStatus(String status) {
        try {
            if (StringTools.isEmpty(status)) {
                return null;
            }
            return UserContactStatusEnum.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * 按数值取（如 1），找不到返回 null
     */
    public static UserContactStatusEnum getByStatus(Integer status) {
        for (UserContactStatusEnum item : UserContactStatusEnum.values()) {
            if (item.getStatus().equals(status)) {
                return item;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
