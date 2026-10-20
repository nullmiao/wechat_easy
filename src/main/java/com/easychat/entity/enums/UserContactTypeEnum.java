package com.easychat.entity.enums;

/**
 * 联系人类型
 * 前缀规则：USER 的 id 以 U 开头，GROUP 的 id 以 G 开头
 */
public enum UserContactTypeEnum {
    USER(0, "U", "好友"),
    GROUP(1, "G", "群");

    private Integer type;
    private String prefix;
    private String desc;

    UserContactTypeEnum(Integer type, String prefix, String desc) {
        this.type = type;
        this.prefix = prefix;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDesc() {
        return desc;
    }

    public static UserContactTypeEnum getByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        try {
            return UserContactTypeEnum.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static UserContactTypeEnum getByPrefix(String prefix) {
        if (prefix == null || prefix.trim().isEmpty()) {
            return null;
        }
        prefix = prefix.substring(0, 1);
        for (UserContactTypeEnum typeEnum : UserContactTypeEnum.values()) {
            if (typeEnum.getPrefix().equals(prefix)) {
                return typeEnum;
            }
        }
        return null;
    }
}
