package com.easychat.entity.enums;

/**
 * 分页大小枚举
 * SimplePage 在调用方没传 pageSize（<=0）时，默认取 SIZE20
 */
public enum PageSize {
    SIZE15(15),
    SIZE20(20),
    SIZE30(30),
    SIZE40(40),
    SIZE50(50);

    private final int size;

    PageSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
