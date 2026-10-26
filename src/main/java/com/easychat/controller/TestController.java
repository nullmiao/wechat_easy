package com.easychat.controller;

import com.easychat.entity.enums.ResponseCodeEnum;
import com.easychat.entity.po.AppUpdate;
import com.easychat.entity.po.ChatMessage;
import com.easychat.entity.po.ChatSession;
import com.easychat.entity.po.ChatSessionUser;
import com.easychat.entity.po.GroupInfo;
import com.easychat.entity.po.UserContact;
import com.easychat.entity.po.UserContactApply;
import com.easychat.entity.po.UserInfo;
import com.easychat.entity.po.UserInfoBeauty;
import com.easychat.entity.query.AppUpdateQuery;
import com.easychat.entity.query.ChatMessageQuery;
import com.easychat.entity.query.ChatSessionQuery;
import com.easychat.entity.query.ChatSessionUserQuery;
import com.easychat.entity.query.GroupInfoQuery;
import com.easychat.entity.query.UserContactApplyQuery;
import com.easychat.entity.query.UserContactQuery;
import com.easychat.entity.query.UserInfoBeautyQuery;
import com.easychat.entity.query.UserInfoQuery;
import com.easychat.entity.vo.ResponseVO;
import com.easychat.exception.BusinessException;
import com.easychat.mappers.AppUpdateMapper;
import com.easychat.mappers.ChatMessageMapper;
import com.easychat.mappers.ChatSessionMapper;
import com.easychat.mappers.ChatSessionUserMapper;
import com.easychat.mappers.GroupInfoMapper;
import com.easychat.mappers.UserContactApplyMapper;
import com.easychat.mappers.UserContactMapper;
import com.easychat.mappers.UserInfoBeautyMapper;
import com.easychat.mappers.UserInfoMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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

    // ================== 以下为 P2 数据层临时验证接口，验证完可删 ==================

    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

    @Resource
    private UserInfoBeautyMapper<UserInfoBeauty, UserInfoBeautyQuery> userInfoBeautyMapper;

    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;

    @Resource
    private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;

    @Resource
    private GroupInfoMapper<GroupInfo, GroupInfoQuery> groupInfoMapper;

    @Resource
    private ChatSessionMapper<ChatSession, ChatSessionQuery> chatSessionMapper;

    @Resource
    private ChatSessionUserMapper<ChatSessionUser, ChatSessionUserQuery> chatSessionUserMapper;

    @Resource
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;

    @Resource
    private AppUpdateMapper<AppUpdate, AppUpdateQuery> appUpdateMapper;

    /**
     * GET /api/test/dbCountAll
     * 一次性验证 9 张表的 Mapper 注入 + XML 加载 + selectCount 执行
     */
    @RequestMapping("dbCountAll")
    public ResponseVO<Map<String, Integer>> dbCountAll() {
        Map<String, Integer> result = new LinkedHashMap<>();
        result.put("user_info", userInfoMapper.selectCount(new UserInfoQuery()));
        result.put("user_info_beauty", userInfoBeautyMapper.selectCount(new UserInfoBeautyQuery()));
        result.put("user_contact", userContactMapper.selectCount(new UserContactQuery()));
        result.put("user_contact_apply", userContactApplyMapper.selectCount(new UserContactApplyQuery()));
        result.put("group_info", groupInfoMapper.selectCount(new GroupInfoQuery()));
        result.put("chat_session", chatSessionMapper.selectCount(new ChatSessionQuery()));
        result.put("chat_session_user", chatSessionUserMapper.selectCount(new ChatSessionUserQuery()));
        result.put("chat_message", chatMessageMapper.selectCount(new ChatMessageQuery()));
        result.put("app_update", appUpdateMapper.selectCount(new AppUpdateQuery()));
        return getSuccessResponseVO(result);
    }

    /**
     * GET /api/test/dbCount
     * 验证：Mapper 能注入、XML 能被加载、selectCount 能执行
     */
    @RequestMapping("dbCount")
    public ResponseVO<Integer> dbCount() {
        UserInfoQuery query = new UserInfoQuery();
        return getSuccessResponseVO(userInfoMapper.selectCount(query));
    }

    /**
     * GET /api/test/dbInsert
     * 验证：insert 能写库、selectByUserId 能把列映射回 PO、deleteByUserId 能删
     * 会临时插入一条 U00000000099 / db-test@example.com，验证后立刻删除
     */
    @RequestMapping("dbInsert")
    public ResponseVO<String> dbInsert() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("U00000000099");
        userInfo.setEmail("db-test@example.com");
        userInfo.setNickName("db-test");
        userInfo.setJoinType(0);
        userInfo.setSex(0);
        userInfo.setPassword("e10adc3949ba59abbe56e057f20f883e");
        userInfo.setStatus(1);
        userInfo.setCreateTime(new Date());
        userInfo.setLastLoginTime(new Date());
        userInfo.setLastOffTime(System.currentTimeMillis());
        Integer insertCount = userInfoMapper.insert(userInfo);

        UserInfo loaded = userInfoMapper.selectByUserId("U00000000099");
        Integer deleteCount = userInfoMapper.deleteByUserId("U00000000099");
        return getSuccessResponseVO("insert=" + insertCount + ", delete=" + deleteCount + ", selected=" + loaded);
    }

    /**
     * GET /api/test/dbAutoInc
     * 验证：自增主键表（chat_message）的 <selectKey> 回填是否生效
     * 期望 generatedId 不为 null；验证后立刻删除
     */
    @RequestMapping("dbAutoInc")
    public ResponseVO<String> dbAutoInc() {
        ChatMessage message = new ChatMessage();
        message.setSessionId("test-session-id");
        message.setMessageType(2);
        message.setMessageContent("hello");
        message.setSendUserId("U00000000001");
        message.setSendUserNickName("tester");
        message.setSendTime(System.currentTimeMillis());
        message.setContactId("U00000000002");
        message.setContactType(0);
        message.setStatus(1);

        Integer insertCount = chatMessageMapper.insert(message);
        Long generatedId = message.getMessageId();
        ChatMessage loaded = generatedId == null ? null : chatMessageMapper.selectByMessageId(generatedId);
        if (generatedId != null) {
            chatMessageMapper.deleteByMessageId(generatedId);
        }
        return getSuccessResponseVO("insert=" + insertCount + ", generatedId=" + generatedId + ", loaded=" + loaded);
    }
}
