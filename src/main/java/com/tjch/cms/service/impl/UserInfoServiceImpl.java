/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UserServiceImpl
 * Author:   Administrator
 * Date:     2019/4/23 14:20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tjch.cms.service.impl;



import com.tjch.cms.base.BaseApiService;
import com.tjch.cms.base.ResponseBase;
import com.tjch.cms.dao.UserInfoUserMapper;
import com.tjch.cms.dao.UsersInfoMapper;
import com.tjch.cms.dto.TokenInfo;
import com.tjch.cms.model.UserModel;
import com.tjch.cms.pojo.UsersInfo;
import com.tjch.cms.service.UserInfoService;
import com.tjch.cms.utils.Md5Util;
import com.tjch.cms.utils.ProductToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2019/4/23
 * @since 1.0.0
 */
@Service(value="userInfoService")
@Transactional
public class UserInfoServiceImpl extends BaseApiService implements UserInfoService {

    @Autowired
    private UsersInfoMapper usersInfoMapper;

    @Autowired
    private ProductToken productToken;

    @Autowired
    private UserInfoUserMapper userInfoUserMapper;

    @Override
    public ResponseBase findAll(Integer enable, Integer typeId, String username) {
        UserModel model = new UserModel();
        model.setType(typeId);
        model.setUserName(username);
        model.setEnable(enable);
        List<UsersInfo> list= userInfoUserMapper.selectAll(model);
        List<UsersInfo> nlist = new ArrayList<>();
        for(UsersInfo user:list){
            user.setPassWord(null);
            nlist.add(user);
        }
        ResponseBase res = new ResponseBase();
        res.setCode(200);
        res.setMsg("查询成功");
        res.setData(nlist);
        return res;
    }

    @Override
    public UsersInfo insertUser(UsersInfo user) {
        usersInfoMapper.insertSelective(user);
        return user;
    }

    @Override
    public UsersInfo loginUser(UserModel model) {
        return userInfoUserMapper.selectLogin(model);
    }



    @Override
    public ResponseBase UserLogin(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //1.校验登陆是否成功
        UsersInfo user = userInfoUserMapper.getUserInfo(username);
        //2.如果不成功返回提示
        if (user == null ) {
            return setResultError("用户名密码错误");
        }else if(!Md5Util.validPassword(password,user.getPassWord())){
            return setResultError("用户名密码错误");
        }else if(user.getEnable()==0){
            return setResultError("该账户未开通,请联系管理员！");
        }else {
            //3.如果成功，生产一个token
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            Map<String, String> mapInfo = productToken.productToken( user.getUserName(),token);
            //3.返回token信息（有效期10分钟）
            TokenInfo info = new TokenInfo();
            info.setUsername(user.getUserName());
            info.setToken(token);
            info.setRealname(user.getRealName());
//            UserLog userLog = new UserLog();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
//            userLog.setContent(user.getRealName()+"在"+df.format(new Date())+"登录");
//            userLog.setTime(df.format(new Date()));
//            userLog.setUsername(user.getName());
//            userLogMapper.insertSelective(userLog);
            return setResultSuccess((Object)info);
        }
    }

    @Override
    public ResponseBase getUserInfo(String username) {

        UsersInfo user = userInfoUserMapper.getUserInfo(username);
        if(user != null) {
            UserModel info = new UserModel();
            info.setUserName(user.getUserName());
            info.setId(user.getId());
            if(user.getRealName()!=null){
                info.setRealName(user.getRealName());
            }

            if(user.getEnable()!=null){
                info.setEnable(user.getEnable());
            }

            return setResultSuccess((Object)info);
        } else {
            return setResultSuccess("无此用户");
        }
    }

    @Override
    public ResponseBase editPassWord(String password, String newPassword, String username) throws NoSuchAlgorithmException,UnsupportedEncodingException{
        ResponseBase res = new ResponseBase();
        UsersInfo user = userInfoUserMapper.getUserInfo(username);
        if(!Md5Util.validPassword(password,user.getPassWord())){
            return setResultError("密码错误");
        }else {
//            新密码
            String nps = Md5Util.getEncryptedPwd(newPassword);
            Integer item=userInfoUserMapper.editPassWord(user.getId(),nps);
            if(item!=0){
                res.setCode(200);
                res.setMsg("编辑成功");
            }else {
                res.setCode(500);
                res.setMsg("编辑失败");
            }

        }
        return res;
    }

    @Override
    public ResponseBase userExit(String username) {
        Boolean code = productToken.clearToken(username);
        ResponseBase res = new ResponseBase();
        if(code){
            res.setMsg("成功");
            res.setCode(200);
        }else {
            res.setMsg("失败");
            res.setCode(500);
        }
        return res;
    }
    @Override
    public ResponseBase editUser(UserModel userModel) {
        ResponseBase res = new ResponseBase();
        Integer item =userInfoUserMapper.editUser(userModel);
        if(item!=0){
            res.setCode(200);
            res.setMsg("编辑成功");
        }else {
            res.setCode(500);
            res.setMsg("编辑失败");
        }
        return res;
    }
    @Override
    public ResponseBase passUser(Integer...id) {
        ResponseBase res = new ResponseBase();
       Integer item= userInfoUserMapper.passUser(id);
        if(item!=0){
            res.setCode(200);
            res.setMsg("开通成功");
        }else {
            res.setCode(500);
            res.setMsg("开通失败");
        }
        return res;
    }
    @Override
    public ResponseBase delUser(Integer...id) {
        ResponseBase res = new ResponseBase();
        Integer item = userInfoUserMapper.deleteByPrimaryKey(id);
        if(item!=0){
            res.setCode(200);
            res.setMsg("删除成功");
        }else {
            res.setCode(500);
            res.setMsg("删除失败");
        }
        return res;
    }
}