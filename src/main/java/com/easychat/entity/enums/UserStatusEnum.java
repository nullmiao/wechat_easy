package com.easychat.entity.enums;

/**
 * 用户状态
 *
 * 使用场景：
 *  1. 注册时 userInfo.status = ENABLE(1)
 *  2. 登录时校验：DISABLE 的账号不允许登录（"账号已禁用"）
 *  3. 后台管理端可以禁用/启用账号（P8）
 *  4. 数据库 user_info.status 列存 0/1
 */
public enum UserStatusEnum {

    DISABLE(0, "禁用"),
    ENABLE(1, "启用");

    private Integer status;
    private String desc;

    UserStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    /**
     * 根据数值取枚举（后台传 status 时用），找不到返回 null
     */
    public static UserStatusEnum getByStatus(Integer status) {
        for (UserStatusEnum item : UserStatusEnum.values()) {
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
