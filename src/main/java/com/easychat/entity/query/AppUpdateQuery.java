package com.easychat.entity.query;

import lombok.Data;

/**
 * App 版本发布查询参数（对应表 app_update）
 */
@Data
public class AppUpdateQuery extends BaseParam {

    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 版本号（精确）
     */
    private String version;

    /**
     * 版本号（模糊）
     */
    private String versionFuzzy;

    /**
     * 更新描述（精确）
     */
    private String updateDesc;

    /**
     * 更新描述（模糊）
     */
    private String updateDescFuzzy;

    /**
     * 创建时间（精确）
     */
    private String createTime;

    /**
     * 创建时间范围起始（>=）
     */
    private String createTimeStart;

    /**
     * 创建时间范围结束（<=）
     */
    private String createTimeEnd;

    /**
     * 0:未发布 1:灰度发布 2:全网发布
     */
    private Integer status;

    /**
     * 灰度uid（精确）
     */
    private String grayscaleUid;

    /**
     * 灰度uid（模糊）
     */
    private String grayscaleUidFuzzy;

    /**
     * 文件类型0:本地文件 1:外链
     */
    private Integer fileType;

    /**
     * 外链地址（精确）
     */
    private String outerLink;

    /**
     * 外链地址（模糊）
     */
    private String outerLinkFuzzy;
}
