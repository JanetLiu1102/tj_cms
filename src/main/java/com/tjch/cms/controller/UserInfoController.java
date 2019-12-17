/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserController
 * Author:   Administrator
 * Date:     2019/4/23 14:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tjch.cms.controller;



import com.tjch.cms.base.ResponseBase;
import com.tjch.cms.dto.UserDto;
import com.tjch.cms.model.UserModel;
import com.tjch.cms.pojo.UsersInfo;
import com.tjch.cms.service.UserInfoService;
import com.tjch.cms.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * 用户注册登录
 *
 *
 * @author Administrator
 * @create 2019/4/23
 * @since 1.0.0
 */
@RestController
@RequestMapping(value ="user")
public class UserInfoController {


    @Autowired
    private UserInfoService userInfoService;
/**
 *
 * 用户注册
 *
 * @param  user
 * @return:
 * @since: 1.0.0
 * @Author:Administrator
 * @Date: 2019/4/25 14:11
 */

    @ResponseBody
    @RequestMapping(value = "saveUser",method = RequestMethod.POST)
    public ResponseBase saveUser(@RequestBody UsersInfo user) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        UserModel usermodel = new UserModel();
        ResponseBase resultModel = new ResponseBase();
        usermodel.setUserName(user.getUserName());
        UsersInfo nuser = userInfoService.loginUser(usermodel);
        if(nuser!=null){
            resultModel.setMsg("用户名已存在，注册失败");
            resultModel.setCode(500);
        }else {
            if(user.getPassWord()!=null){
                String nps = Md5Util.getEncryptedPwd(user.getPassWord());
                user.setPassWord(nps);
                resultModel.setCode(200);
                resultModel.setMsg("成功");
               UsersInfo suser = userInfoService.insertUser(user);
                suser.setPassWord(null);
                resultModel.setData(suser);
            }else{
                resultModel.setCode(500);
                resultModel.setMsg("密码不能为空");
            }
        }
        return resultModel;
    }


/*
登录
 */
    @RequestMapping("/login")
    public ResponseBase UserLogin(@RequestBody @Valid UserDto userDto, HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return userInfoService.UserLogin(userDto.getUserName(), userDto.getPassword());
    }

/*

 */
    @RequestMapping("/getUserInfo")
    public ResponseBase getUserInfo(String  username) {
        return userInfoService.getUserInfo(username);
    }

    @RequestMapping(value = "/getUserInfoWeb",method = RequestMethod.GET)
    public ResponseBase getUserInfoWeb(String  username) {
        return userInfoService.getUserInfo(username);
    }
/*
修改密码
 */
    @RequestMapping("/editPassWord")
    public ResponseBase editPassWord(String  password,String newPassword, HttpServletRequest request) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String username = request.getParameter("username");
        return userInfoService.editPassWord(password,newPassword,username);
    }
/*
退出登录
 */
    @RequestMapping("/userExit")
    public ResponseBase userExit( HttpServletRequest request) {
        String username = request.getParameter("username");
        return userInfoService.userExit(username);
    }
/*
编辑用户信息
 */
    @RequestMapping("/editUser")
    public ResponseBase editUser( HttpServletRequest request,@RequestBody  UserModel model) {
        return userInfoService.editUser(model);
    }
/*
开通账户 管理员可操作
 */
    @RequestMapping("/passUser")
    public ResponseBase passUser( HttpServletRequest request,Integer...id) {
        UserModel usermodel = new UserModel();
        String username = request.getParameter("username");
        usermodel.setUserName(username);
        ResponseBase res = new ResponseBase();
        UsersInfo nuser = userInfoService.loginUser(usermodel);
        if(nuser.getTypeId()==1){
            return userInfoService.passUser(id);
        }else {
            res.setCode(500);
            res.setMsg("您没有操作权限,请联系管理员!");
            return res;
        }
    }
    /*
删除用户
     */
    @RequestMapping("/delUser")
    public ResponseBase delUser( HttpServletRequest request,Integer...id) {
        UserModel usermodel = new UserModel();
        String username = request.getParameter("username");
        usermodel.setUserName(username);
        ResponseBase res = new ResponseBase();
        UsersInfo nuser = userInfoService.loginUser(usermodel);
        if(nuser.getTypeId()==1){
            return userInfoService.delUser(id);
        }else {
            res.setCode(500);
            res.setMsg("您没有操作权限,请联系管理员!");
            return res;
        }
    }

    @RequestMapping("/userList")
    public ResponseBase userList( HttpServletRequest request,Integer enable,Integer typeId,String name) {
        UserModel usermodel = new UserModel();
        usermodel.setUserName(name);
        ResponseBase res = userInfoService.findAll(enable,typeId,name);
        return res;
    }

}