package com.easychat.entity.query;

import com.easychat.entity.enums.PageSize;
import lombok.Data;

/**
 * 分页参数
 *
 * 命名说明（很重要，原项目的历史命名）：
 *   start = 偏移量 offset，等于 (pageNo - 1) * pageSize
 *   end   = 每页条数 rowCount，等于 pageSize（不是"结束下标"！）
 * 所以 XML 里写的是：limit #{query.simplePage.start},#{query.simplePage.end}
 * 对应 MySQL 的 LIMIT offset, rowCount
 *
 * 使用方式（Service 层固定三步）：
 *   int count = mapper.selectCount(query);                      // 1. 先查总数
 *   query.setSimplePage(new SimplePage(pageNo, count, pageSize)); // 2. 构造分页
 *   List<Xxx> list = mapper.selectList(query);                  // 3. 再查列表
 *
 * 注意：@Data 会为所有字段生成 getter/setter，但 setCountTotal 必须由我们手写覆盖
 *      （Lombok 检测到同名方法就不再生成），因为设置总数后要立刻重算分页。
 */
@Data
public class SimplePage {

    /**
     * 当前页码，从 1 开始
     */
    private int pageNo;

    /**
     * 总记录数（由 count 查询得到）
     */
    private int countTotal;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总页数（由 action() 计算）
     */
    private int pageTotal;

    /**
     * SQL 的 LIMIT offset
     */
    private int start;

    /**
     * SQL 的 LIMIT rowCount（命名叫 end，但语义是"每页条数"）
     */
    private int end;

    public SimplePage() {
    }

    /**
     * 常用构造器：传页码、总条数、每页条数，内部自动算出 start/end/pageTotal
     * 注意 pageNo 用 Integer 接收，允许调用方传 null（null 视为 0，后面会被修正为 1）
     */
    public SimplePage(Integer pageNo, int countTotal, int pageSize) {
        if (pageNo == null) {
            pageNo = 0;
        }
        this.pageNo = pageNo;
        this.countTotal = countTotal;
        this.pageSize = pageSize;
        action();
    }

    /**
     * 直接指定 start/end 的构造器（一般用不到，保留以兼容）
     */
    public SimplePage(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 分页核心计算：
     * 1) pageSize 非法时用默认 20；
     * 2) pageTotal = ceil(countTotal / pageSize)，countTotal 为 0 时总页数按 1 算；
     * 3) pageNo 修正到 [1, pageTotal]；
     * 4) start = 偏移量，end = 每页条数。
     */
    public void action() {
        if (pageSize <= 0) {
            pageSize = PageSize.SIZE20.getSize();
        }
        if (countTotal > 0) {
            pageTotal = countTotal % pageSize == 0 ? countTotal / pageSize : countTotal / pageSize + 1;
        } else {
            pageTotal = 1;
        }
        if (pageNo <= 1) {
            pageNo = 1;
        }
        if (pageNo > pageTotal) {
            pageNo = pageTotal;
        }
        this.start = (pageNo - 1) * pageSize;
        this.end = pageSize;
    }

    /**
     * 手写覆盖 Lombok 生成的 setter：设置总条数后立刻重算分页
     * （典型场景：先 selectCount 拿到 count，再 setCountTotal 刷新 start/end/pageTotal）
     */
    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
        action();
    }
}
