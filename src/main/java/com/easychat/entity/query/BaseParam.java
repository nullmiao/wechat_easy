package com.easychat.entity.query;

import lombok.Data;

/**
 * 所有查询参数（XxxQuery）的父类，提供分页和排序能力
 *
 * 说明：
 * 1. 每个 Query 都继承它，所以 XML 里可以统一用 query.simplePage / query.orderBy；
 * 2. orderBy 在 XML 里是用 ${} 字符串拼接的，只能由服务端写死（如 "send_time desc"），
 *    绝对不能直接接收前端传来的排序字段，否则是 SQL 注入；
 * 3. 字段全部用包装类型，null 表示"不参与查询"。
 */
@Data
public class BaseParam {

    /**
     * 分页参数（由 countTotal + pageNo + pageSize 计算出来）
     */
    private SimplePage simplePage;

    /**
     * 页码，从 1 开始
     */
    private Integer pageNo;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 排序子句，如 "send_time desc"
     */
    private String orderBy;
}
