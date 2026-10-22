package com.easychat.entity.config;

import com.easychat.utils.StringTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 项目自定义配置读取类
 * 对应 application.yml 中的自定义配置项：
 *   ws.port        → WebSocket 端口
 *   project.folder → 项目根目录（日志、上传文件、App 安装包都放这里）
 *   admin.emails   → 超级管理员邮箱列表
 */
@Component("appConfig")
@Slf4j
public class AppConfig {

    /**
     * WebSocket 端口（Netty 用，P6 生效）
     */
    @Value("${ws.port:}")
    private Integer wsPort;

    /**
     * 文件目录
     */
    @Value("${project.folder:}")
    private String projectFolder;

    @Value("${admin.emails:}")
    private String adminEmails;

    /**
     * 目录统一以 / 结尾，方便后面直接拼 "/file/202312" 这样的子路径
     * 注意：yml 里配的 "c:/easychat/" 已经带斜杠，这里做兜底
     */
    public String getProjectFolder() {
        if (!StringTools.isEmpty(projectFolder) && !projectFolder.endsWith("/")) {
            projectFolder = projectFolder + "/";
        }
        return projectFolder;
    }

    public String getAdminEmails() {
        return adminEmails;
    }

    public Integer getWsPort() {
        return wsPort;
    }
}
