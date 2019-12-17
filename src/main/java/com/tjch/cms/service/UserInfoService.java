/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserService
 * Author:   Administrator
 * Date:     2019/4/23 14:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tjch.cms.service;



import com.tjch.cms.base.ResponseBase;
import com.tjch.cms.model.UserModel;
import com.tjch.cms.pojo.UsersInfo;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 *
 *
 * @author Administrator
 * @create 2019/4/23
 * @since 1.0.0
 */
public interface UserInfoService {
    /**
     *用户列表
     */
    ResponseBase findAll(Integer enable, Integer typeId, String username);
    /**
     *添加用户
     */
     UsersInfo insertUser(UsersInfo user);
    /**
     *用户登录
     */
    UsersInfo loginUser(UserModel model);

     /**
      * 用户登录
      * @param username
      * @param password
      * @return
      */
      ResponseBase UserLogin(String username, String password)throws NoSuchAlgorithmException, UnsupportedEncodingException ;

    /**
     * 获取用户基本信息
     * @param  username
     * @return
     */
    ResponseBase getUserInfo(String username);
    /**
     *修改密码
     */
    ResponseBase editPassWord(String password, String newPassword, String username) throws NoSuchAlgorithmException,UnsupportedEncodingException;
    /**
     *退出登录
     */
    ResponseBase userExit(String username);
    /**
     *用户信息编辑
     */
    ResponseBase editUser(UserModel userModel);
    /**
     *开通账户
     */
    ResponseBase passUser(Integer... id);
    /**
     *删除用户
     */
    ResponseBase delUser(Integer... id);
}