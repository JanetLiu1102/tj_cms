/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TjProjectController
 * Author:   Administrator
 * Date:     2019/11/28 16:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tjch.cms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceGetRequest;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.taobao.api.ApiException;
import com.tjch.cms.base.ResponseBase;
import com.tjch.cms.config.URLConstant;
import com.tjch.cms.model.TjProjectModel;
import com.tjch.cms.pojo.TjProject;
import com.tjch.cms.service.TjProjectService;
import com.tjch.cms.utils.AccessTokenUtil;
import com.tjch.cms.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2019/11/28
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "project")
public class TjProjectController {
    private static final Logger logger = LoggerFactory.getLogger(TjProjectController.class);
    @Autowired
    private TjProjectService tjProjectService;

    /**
     * 功能描述:
     * 〈查询所有〉
     *
     * @param
     * @return:
     * @since: 1.0.0
     * @Author:Administrator
     * @Date: 2019/12/11 17:07
     */
    @ResponseBody
    @RequestMapping(value = "findAll",method = RequestMethod.POST)
    public ResponseBase findAll(String createStartTime,String createEndTime,String proName, String entrustCompany,String street,String proType,String applicationStartTime,String applicationEndTime, String instanceId, Integer pageIndex, Integer pageSize){
        TjProjectModel model = new TjProjectModel();
        model.setApplicationEndTime(applicationEndTime);
        model.setApplicationStartTime(applicationStartTime);
        model.setCreateEndTime(createEndTime);
        model.setCreateStartTime(createStartTime);
        model.setEntrustCompany(entrustCompany);
        model.setInstanceId(instanceId);
        model.setProType(proType);
        model.setProName(proName);
        model.setStreet(street);
        model.setPageIndex(pageIndex);
        model.setPageSize(pageSize);
        ResponseBase res = tjProjectService.findAllPro(model);
        res.setCode(200);
        res.setMsg("操作成功");
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "findById",method = RequestMethod.GET)
    public ResponseBase findById(Integer id){
        ResponseBase res = tjProjectService.findById(id);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "savePro",method = RequestMethod.GET)
    public ResponseBase savePro(@RequestParam String instanceId)throws RuntimeException{
        ResponseBase res =new ResponseBase();
        //获取accessToken,注意正是代码要有异常流处理
        String accessToken = AccessTokenUtil.getToken();
        TjProject pro = new TjProject();
        //根据审批单id获取审批内容
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_PROCESSINSTANCE_GET);
        OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
        request.setProcessInstanceId(instanceId);
        try {
            OapiProcessinstanceGetResponse response = client.execute(request,accessToken);
            if(response.getErrcode()==0){
                JSONObject jsonObject = JSONObject.parseObject(JsonUtils.objectToJson(response));

                JSONObject processJson =jsonObject.getJSONObject("processInstance");
                System.out.println(processJson);
                pro.setInstanceId(instanceId);
                JSONArray photoJsonArray1 = processJson.getJSONArray("formComponentValues");
                // 设置日期格式
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createTime =df.format(new Date());
                pro.setCreateTime(createTime);
                for (int j = 0; j < photoJsonArray1.size(); j++) {
                    JSONObject jo1 = photoJsonArray1.getJSONObject(j);
                    if(jo1.getString("name").equals("项目编号")){
                        pro.setProId(jo1.getString("value"));
                    }
                        if(jo1.getString("name").equals("申请日期")){
                            if(!jo1.getString("value").equals("null")){
                                pro.setApplicationTime(jo1.getString("value"));
                            }
                        }
                    if(jo1.getString("name").equals("测绘单位")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setMappingCompany(jo1.getString("value"));
                        }
                    }
                    if(jo1.getString("name").equals("委托单位")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setEntrustCompany(jo1.getString("value"));
                        }
                    }
                    if(jo1.getString("name").equals("项目名称")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setProName(jo1.getString("value"));
                        }
                    }
                    if(jo1.getString("name").equals("所需人员")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setPersonType(jo1.getString("value"));
                        }
                    }
                    if(jo1.getString("name").equals("项目类型")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setProType(jo1.getString("value"));
                        }
                    }
                    if(jo1.getString("name").equals("进场时间")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setApproachTime(jo1.getString("value"));
                        }
                    }
                    if(jo1.getString("name").equals("所在街道")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setStreet(jo1.getString("value"));
                        }
                    }
                    if(jo1.getString("name").equals("项目说明")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setProContent(jo1.getString("value"));
                        }
                    }
                    if(jo1.getString("name").equals("图片")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setImgName(jo1.getString("value"));
                        }
                    }
                    if(jo1.getString("name").equals("图片路径")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setImgPath(jo1.getString("value"));
                        }
                    }
                    if(jo1.getString("name").equals("附件")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setFileName(jo1.getString("value"));
                        }
                    }
                    if(jo1.getString("name").equals("附件路径")){
                        if(!jo1.getString("value").equals("null")){
                            pro.setFilePath(jo1.getString("value"));
                        }
                    }
                }
                 res =tjProjectService.insertPro(pro);
            }

        } catch (ApiException e) {
            e.printStackTrace();
            logger.error("审批流异常：",e);
            return null;
        }
        return res;
    }
    @ResponseBody
    @RequestMapping(value = "updateProject",method = RequestMethod.POST)
    public ResponseBase updateProject(@RequestBody TjProject project){
        ResponseBase res = tjProjectService.updatePro(project);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "deleteProject",method = RequestMethod.POST)
    public ResponseBase deleteProject(Integer...ids){
        ResponseBase res = tjProjectService.deletePro(ids);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "groupProCount",method = RequestMethod.POST)
    public ResponseBase groupProCount(String createStartTime,String createEndTime,String proName, String entrustCompany,String street,String proType,String applicationStartTime,String applicationEndTime, String instanceId){
        TjProjectModel model = new TjProjectModel();
        model.setApplicationEndTime(applicationEndTime);
        model.setApplicationStartTime(applicationStartTime);
        model.setCreateEndTime(createEndTime);
        model.setCreateStartTime(createStartTime);
        model.setEntrustCompany(entrustCompany);
        model.setInstanceId(instanceId);
        model.setProName(proName);
        model.setProType(proType);
        model.setStreet(street);
        ResponseBase res = tjProjectService.findGroupProCount(model);
        res.setCode(200);
        res.setMsg("操作成功");
        return res;
    }
}