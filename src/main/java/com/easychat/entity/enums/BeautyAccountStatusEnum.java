package com.easychat.entity.enums;

/**
 * 靓号状态（user_info_beauty.status）
 *
 * 靓号：后台提前录入的"邮箱 → 指定 userId"记录，
 * 用户注册时如果邮箱命中未使用的靓号，就直接用指定 userId，而不是随机生成。
 *
 *   0 NO_USE 未使用 —— 注册时命中则可分配
 *   1 USEED  已使用 —— 注册后置为该状态（原项目拼写就是 USEED，保留）
 *
 * 使用场景（P3 注册流程）：
 *  1. 按 email 查 user_info_beauty
 *  2. status == NO_USE 才使用靓号
 *  3. 注册成功后 updateById 把状态改成 USEED
 */
public enum BeautyAccountStatusEnum {

    NO_USE(0, "未使用"),
    USEED(1, "已使用");

    private Integer status;
    private String desc;

    BeautyAccountStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static BeautyAccountStatusEnum getByStatus(Integer status) {
        for (BeautyAccountStatusEnum item : BeautyAccountStatusEnum.values()) {
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
