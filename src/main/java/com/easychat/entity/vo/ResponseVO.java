package com.easychat.entity.vo;

/**
 * 统一响应结构
 * status: success / error
 * code:   业务码（200 成功、600 参数错误、901 登录超时……）
 * info:   提示信息
 * data:   业务数据
 */
public class ResponseVO<T> {
    private String status;
    private Integer code;
    private String info;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
