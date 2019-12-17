/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TjProjectModel
 * Author:   Administrator
 * Date:     2019/11/28 14:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tjch.cms.model;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2019/11/28
 * @since 1.0.0
 */
public class TjProjectModel extends BaseResultModel{

//    create_time 创建时间
    private String createStartTime ;
    private String createEndTime;
//    pro_name 项目名称
    private String proName;
//    entrust_company 委托单位
    private  String entrustCompany;
//    street所在街道
    private  String street;
//    pro_type项目类型
    private String proType;
//    application_time申请时间
    private String applicationStartTime;

    private String applicationEndTime;

//    审批单Id
    private String instanceId;

    public String getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(String createStartTime) {
        this.createStartTime = createStartTime;
    }

    public String getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(String createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getEntrustCompany() {
        return entrustCompany;
    }

    public void setEntrustCompany(String entrustCompany) {
        this.entrustCompany = entrustCompany;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getApplicationStartTime() {
        return applicationStartTime;
    }

    public void setApplicationStartTime(String applicationStartTime) {
        this.applicationStartTime = applicationStartTime;
    }

    public String getApplicationEndTime() {
        return applicationEndTime;
    }

    public void setApplicationEndTime(String applicationEndTime) {
        this.applicationEndTime = applicationEndTime;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}