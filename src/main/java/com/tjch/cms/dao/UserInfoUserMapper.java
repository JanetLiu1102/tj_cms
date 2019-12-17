/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserInfoUserMapper
 * Author:   Administrator
 * Date:     2019/11/18 14:53
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tjch.cms.dao;



import com.tjch.cms.model.UserModel;
import com.tjch.cms.pojo.UsersInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2019/11/18
 * @since 1.0.0
 */
@Mapper
public interface UserInfoUserMapper {
    UsersInfo selectLogin(UserModel model);

    UsersInfo getUserInfo(String username);

    UsersInfo getUserInfoById(Integer id);

    List<UsersInfo> selectAll(UserModel model);

    Integer editPassWord(@Param(value = "id") Integer id, @Param(value = "password") String password);

    Integer editUser(UserModel model);

    Integer passUser(@Param(value = "id") Integer... id);

    int deleteByPrimaryKey(@Param(value = "id") Integer... id);

}