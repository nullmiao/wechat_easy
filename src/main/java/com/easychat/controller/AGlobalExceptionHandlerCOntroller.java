package com.easychat.controller;

import com.easychat.entity.enums.ResponseCodeEnum;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 * 作用：把所有 Controller / Service 抛出的异常收敛到这里，统一转成 {status, code, info, data} 结构。
 * 没有它的话，异常会走 Spring 默认错误响应（HTTP 500 + 默认 JSON），前端拿不到业务码，堆栈还可能泄露给客户端。
 *
 * 说明：@RestControllerAdvice = @ControllerAdvice + @ResponseBody，
 *      返回值会被 Jackson 直接序列化成 JSON 写回响应。
 */
@RestControllerAdvice
public class AGlobalExceptionHandlerController extends ABaseController {

    private static final Logger logger = LoggerFactory.getLogger(AGlobalExceptionHandlerController.class);

    /**
     * 兜底处理所有异常。
     * 返回值统一用 ResponseVO，保证"成功"和"失败"两种响应结构完全一致。
     *
     * 注意分支顺序：这里用 instanceof 手工判断，越具体的异常要越靠前，
     * 最后才是"其他未知异常"的兜底分支。
     */
    @ExceptionHandler(value = Exception.class)

    public ResponseVO<Object> handleException(Exception e, HttpServletRequest request) {
        // 日志里带上请求地址和完整异常
        // （未预期异常靠堆栈定位；业务异常重写了 fillInStackTrace，日志里只有一行 message）
        logger.error("请求错误，请求地址{},错误信息:", request.getRequestURL(), e);

        // 1) 请求地址不存在（404）
        // 注意：该分支要生效需要配置 spring.mvc.throw-exception-if-no-handler-found=true，
        // 原项目没配，所以现实中断言"地址不存在"会走 Spring 默认错误页；这里保留是为了对齐原项目结构。
        if (e instanceof NoHandlerFoundException) {
            return errorResponse(ResponseCodeEnum.CODE_404);
        }

        // 2) 业务异常：业务码以异常自带的为准（901 登录超时 / 902 不是好友 / 903 不在群聊...），没有才回落 600
        // 直接复用 ABaseController 的方法（这也是刚才修正点 4 的意义）
        if (e instanceof BusinessException) {
            return getBusinessErrorResponseVO((BusinessException) e, null);
        }

        // 3) 参数绑定失败 / 参数类型不匹配（例如把 "abc" 传给 Integer 类型的 id）
        // MethodArgumentNotValidException 在 Spring 5 里是 BindException 的子类，会走到这里
        if (e instanceof BindException || e instanceof MethodArgumentTypeMismatchException) {
            return errorResponse(ResponseCodeEnum.CODE_600);
        }

        // 4) 唯一键冲突（例如同一邮箱重复注册、重复加好友）
        if (e instanceof DuplicateKeyException) {
            return errorResponse(ResponseCodeEnum.CODE_601);
        }

        // 5) Bean Validation 校验失败（@NotEmpty / @Size 等，方法参数上的校验）
        // 说明：原项目这里还重复写了一次 BindException（第二个 instanceof 永远进不去），属于死代码，这里合并掉了。
        if (e instanceof ConstraintViolationException) {
            return errorResponse(ResponseCodeEnum.CODE_600);
        }

        // 6) 其他未预期异常（空指针、SQL 错误、Redis 超时……）
        // 对外只返回 500 + 通用文案，具体堆栈只写日志，避免把内部实现细节暴露给客户端
        return getServerErrorResponseVO(null);
    }

    /**
     * 构造"只有错误码、没有额外数据"的统一错误响应。
     * 抽成私有方法是为了避免 404/600/601 三个分支重复 set 三次字段。
     */
    private ResponseVO<Object> errorResponse(ResponseCodeEnum codeEnum) {
        ResponseVO<Object> vo = new ResponseVO<>();
        vo.setStatus(STATUC_ERROR);
        vo.setCode(codeEnum.getCode());
        vo.setInfo(codeEnum.getMsg());
        return vo;
    }
}
