package com.easychat.utils;

import com.easychat.entity.constants.Constants;
import com.easychat.entity.enums.UserContactTypeEnum;
import com.easychat.exception.BusinessException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 字符串/通用工具类
 */
public class StringTools {

    /**
     * 多参数更新/删除时的兜底校验：反射遍历对象所有 getter，如果全为 null/空串，就拒绝执行。
     * 防止写出 "update ... where 1=1" 这种把整张表更新掉的 SQL。
     */
    public static void checkParam(Object param) {
        try {
            Field[] fields = param.getClass().getDeclaredFields();
            boolean notEmpty = false;
            for (Field field : fields) {
                String methodName = "get" + StringTools.upperCaseFirstLetter(field.getName());
                Method method = param.getClass().getMethod(methodName);
                Object object = method.invoke(param);
                if (object != null && object instanceof java.lang.String && !StringTools.isEmpty(object.toString())
                        || object != null && !(object instanceof java.lang.String)) {
                    notEmpty = true;
                    break;
                }
            }
            if (!notEmpty) {
                throw new BusinessException("多参数更新，删除，必须有非空条件");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("校验参数是否为空失败");
        }
    }

    /**
     * 首字母大写；但如果第二个字母已经大写（如 userName -> UserName 之外的特殊字段），保持原样
     */
    public static String upperCaseFirstLetter(String field) {
        if (isEmpty(field)) {
            return field;
        }
        //如果第二个字母是大写，第一个字母不大写
        if (field.length() > 1 && Character.isUpperCase(field.charAt(1))) {
            return field;
        }
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    public static boolean isNumber(String str) {
        String checkNumber = "^[0-9]+$";
        if (null == str) {
            return false;
        }
        if (!str.matches(checkNumber)) {
            return false;
        }
        return true;
    }

    /**
     * 判空：null、"null" 字符串、空串、空白串、\u0000 都算空
     */
    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str) || "null".equals(str) || "\u0000".equals(str)) {
            return true;
        } else if ("".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 随机纯数字（用于生成 userId 后缀）
     */
    public static final String getRandomNumber(Integer count) {
        return RandomStringUtils.random(count, false, true);
    }

    /**
     * 随机数字+字母（用于 token 随机串）
     */
    public static final String getRandomString(Integer count) {
        return RandomStringUtils.random(count, true, true);
    }

    /**
     * MD5 加密（密码、token、sessionId 都用它）
     */
    public static String encodeByMD5(String originString) {
        return StringTools.isEmpty(originString) ? null : DigestUtils.md5Hex(originString);
    }

    /**
     * 取文件后缀（含点），如 "a.jpg" -> ".jpg"
     * 注意：文件名不带点时这里会抛 StringIndexOutOfBoundsException（原项目的坑，后面讲文件上传时会遇到）
     */
    public static String getFileSuffix(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return suffix;
    }

    /**
     * 路径穿越防护：包含 ../ 或 ..\ 就拒绝（原项目全仓库没有调用它，是个安全缺口）
     */
    public static boolean pathIsOk(String path) {
        if (StringTools.isEmpty(path)) {
            return true;
        }
        if (path.contains("../") || path.contains("..\\")) {
            return false;
        }
        return true;
    }

    /**
     * 生成群号：G + 11 位随机数字
     */
    public static final String getGroupId() {
        return UserContactTypeEnum.GROUP.getPrefix() + getRandomNumber(Constants.LENGTH_11);
    }

    /**
     * 生成用户 id：U + 11 位随机数字
     */
    public static final String getUserId() {
        return UserContactTypeEnum.USER.getPrefix() + getRandomNumber(Constants.LENGTH_11);
    }

    /**
     * 单聊会话 id：双方 userId 排序后拼接再 MD5（保证 A-B 与 B-A 得到同一个会话）
     */
    public static final String getChatSessionId4User(String[] userIds) {
        Arrays.sort(userIds);
        return encodeByMD5(StringUtils.join(userIds, ""));
    }

    /**
     * 群聊会话 id：直接对 groupId 做 MD5
     */
    public static final String getChatSessionId4Group(String groupId) {
        return encodeByMD5(groupId);
    }

    /**
     * 清理消息内容里的 HTML 标签（防 XSS），换行转 <br>
     */
    public static String cleanHtmlTag(String content) {
        if (isEmpty(content)) {
            return content;
        }
        content = content.replace("<", "&lt;");
        content = content.replace("\r\n", "<br>");
        content = content.replace("\n", "<br>");
        return content;
    }

    public static String resetMessageContent(String content) {
        content = cleanHtmlTag(content);
        return content;
    }
}
