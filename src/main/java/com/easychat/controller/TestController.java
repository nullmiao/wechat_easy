package com.easychat.controller;

import com.easychat.entity.enums.ResponseCodeEnum;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.exception.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 临时测试 Controller（P1 验收用，P2 之后可以删）
 * 目的：验证「统一响应结构」和「全局异常处理器」两条路径都通。
 */
@RestController
@RequestMapping("/test")
public class TestController extends ABaseController {

    /**
     * 成功路径：Controller 自己构造统一响应
     * GET /api/test/hello
     */
    @RequestMapping("hello")
    public ResponseVO<String> hello() {
        return getSuccessResponseVO("hello easychat");
    }

    /**
     * 业务异常路径：抛 BusinessException（不带 code）
     * GET /api/test/error
     * 期望：全局异常处理器接住 → status=error、code=600、info=测试业务异常
     */
    @RequestMapping("error")
    public ResponseVO<Object> error() {
        throw new BusinessException("测试业务异常");
    }

    /**
     * 业务异常路径：抛带业务码的 BusinessException
     * GET /api/test/error901
     * 期望：status=error、code=901、info=登录超时
     * 用来验证 ABaseController 修正点 4（业务码透传，而不是一律 600）
     */
    @RequestMapping("error901")
    public ResponseVO<Object> error901() {
        throw new BusinessException(ResponseCodeEnum.CODE_901);
    }

    /**
     * 未预期异常路径：非业务异常（空指针）
     * GET /api/test/boom
     * 期望：status=error、code=500（具体堆栈只进日志，不返回给前端）
     */
    @RequestMapping("boom")
    public ResponseVO<Object> boom() {
        String nullStr = null;
        return getSuccessResponseVO(nullStr.length());
    }
}
