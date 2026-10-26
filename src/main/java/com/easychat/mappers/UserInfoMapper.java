package com.easychat.mappers;

import com.easychat.entity.query.UserInfoQuery;
import org.apache.ibatis.annotations.Param;

/**
 * 用户信息 Mapper
 *
 * 说明：
 * 1. 泛型 T/P 只是"类型标记"，MyBatis 运行时按接口全限定名去找同名 XML，泛型不参与校验；
 *    服务层注入写法：@Resource private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
 * 2. 通用 CRUD 方法来自父接口 BaseMapper，具体 SQL 写在 UserInfoMapper.xml 里；
 * 3. 下面 6 个是这张表专有的按主键/唯一键操作方法。
 */
public interface UserInfoMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 按主键 user_id 更新
     */
    Integer updateByUserId(@Param("bean") T t, @Param("userId") String userId);

    /**
     * 按主键 user_id 删除
     */
    Integer deleteByUserId(@Param("userId") String userId);

    /**
     * 按主键 user_id 查询
     */
    T selectByUserId(@Param("userId") String userId);

    /**
     * 按唯一键 email 更新
     */
    Integer updateByEmail(@Param("bean") T t, @Param("email") String email);

    /**
     * 按唯一键 email 删除
     */
    Integer deleteByEmail(@Param("email") String email);

    /**
     * 按唯一键 email 查询
     */
    T selectByEmail(@Param("email") String email);
}
