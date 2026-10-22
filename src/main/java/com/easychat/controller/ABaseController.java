package com.easychat.controller;

import com.easychat.entity.enums.ResponseCodeEnum;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.exception.BusinessException;

/**
 * 所有 Controller 的基类：统一响应结构（status / code / info / data）
 * 注意：它本身不是 Spring Bean（没有 @RestController / @RequestMapping），只负责被继承。
 */
public class ABaseController {

    // ===================== 修正点 1 =====================
    // 原来：String COOKIE_KEY_TOKEN = "token";（实例字段、包级可见、非 final）
    // 现在：protected static final
    //   为什么 static：常量属于类，不属于每个 Controller 实例，没必要每个对象存一份；
    //   为什么 final：防止运行期被误改（常量就该不可变）；
    //   为什么 protected：子类（所有 Controller）能直接用，外部类拿不到。
    // 保留 STATUC 拼写（原项目就是 STATUC_SUCCESS/STATUC_ERROR），别加 D，后面代码会引用。
    protected static final String COOKIE_KEY_TOKEN = "token";
    protected static final String STATUC_SUCCESS = "success";
    protected static final String STATUC_ERROR = "error";

    // ===================== 修正点 2 =====================
    // 原来：protected <T> ResponseVO getSuccessResponseVO(T t)  —— 返回裸类型
    // 现在：protected <T> ResponseVO<T> getSuccessResponseVO(T t)
    //   为什么：保留泛型信息，调用方拿到的 data 有编译期类型；避免 unchecked 警告。
    //
    // ===================== 修正点 3 =====================
    // 原来：res.setCode(200); res.setInfo("请求成功");  —— 魔法值
    // 现在：ResponseCodeEnum.CODE_200.getCode() / getMsg()
    //   为什么：200 和文案只应在枚举里定义一次；写死两处就是"同一事实两个真相源"，
    //   以后改枚举文案时，这里的响应不会跟着变，就会出现"成功响应文案不一致"。
    protected <T> ResponseVO<T> getSuccessResponseVO(T t) {
        ResponseVO<T> res = new ResponseVO<>();
        res.setStatus(STATUC_SUCCESS);
        res.setCode(ResponseCodeEnum.CODE_200.getCode());
        res.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        res.setData(t);
        return res;
    }

    // ===================== 修正点 4 =====================
    // 原来：res.setCode(600);  —— 业务码被写死
    // 现在：e.getCode() == null ? 600 : e.getCode()
    //   为什么：BusinessException 可能带 901（登录超时）、902（不是好友）、903（不在群聊）等业务码，
    //   写死 600 会让这些码永远传不到前端，前端就无法做"跳登录页 / 提示加好友"这类分支处理。
    //
    // ===================== 修正点 5 =====================
    // 原来：方法收了参数 t，却没有 res.setData(t)，错误响应里的 data 永远是 null
    // 现在：补上 res.setData(t)，保证错误响应和成功响应结构完全一致
    protected <T> ResponseVO<T> getBusinessErrorResponseVO(BusinessException e, T t) {
        ResponseVO<T> res = new ResponseVO<>();
        res.setStatus(STATUC_ERROR);
        res.setCode(e.getCode() == null ? ResponseCodeEnum.CODE_600.getCode() : e.getCode());
        res.setInfo(e.getMessage());
        res.setData(t);
        return res;
    }

    // ===================== 修正点 6 =====================
    // 原来：没设置 info（也没有 data），客户端收到的错误响应 info=null
    // 现在：补上 500 的提示文案和 data，错误响应同样符合 {status, code, info, data} 契约
    protected <T> ResponseVO<T> getServerErrorResponseVO(T t) {
        ResponseVO<T> res = new ResponseVO<>();
        res.setStatus(STATUC_ERROR);
        res.setCode(ResponseCodeEnum.CODE_500.getCode());
        res.setInfo(ResponseCodeEnum.CODE_500.getMsg());
        res.setData(t);
        return res;
    }

    // ===================== 待补（P3）=====================
    // token 相关方法先不写，因为它们依赖 RedisUtils 和 Constants 里的 token key；
    // 等 P3（Redis + 认证）把 RedisUtils 写好后，再补：
    //   protected TokenUserInfoDto getTokenUserInfo(HttpServletRequest request)
    //   protected void resetTokenUserInfo(HttpServletRequest request, TokenUserInfoDto tokenUserInfoDto)
    // 现在不写是为了让 P1 阶段保持"随时可编译、可启动"。
}
