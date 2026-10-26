package com.easychat.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统设置 VO（返回给前端）
 *
 * 为什么只暴露 3 个字段？
 *   SysSettingDto 里还有群上限、机器人 uid/昵称/欢迎语等"内部/管理端"配置，
 *   普通客户端只需要知道上传大小限制。让前端只看到该看的 —— 最小暴露原则。
 *
 * 默认值和 SysSettingDto 一致：即使 Redis 里没有配置，前端也能拿到 2/5/5。
 * （注意：CopyTools/BeanUtils 会把源对象的 null 也拷过来，所以源 DTO 必须有默认值兜底。）
 */
@Data
public class SysSettingVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单张图片最大 MB
     */
    private Integer maxImageSize = 2;

    /**
     * 单个视频最大 MB
     */
    private Integer maxVideoSize = 5;

    /**
     * 单个文件最大 MB
     */
    private Integer maxFileSize = 5;
}
