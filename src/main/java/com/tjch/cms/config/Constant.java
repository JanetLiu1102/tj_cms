package com.tjch.cms.config;

/**
 * 项目中的常量定义类
 */
public class Constant {
    /**
     * 企业corpid, 需要修改成开发者所在企业
     */
    public static final String CORP_ID = "ding172b5c4b03a38a8135c2f4657eb6378f";
    /**
     * 应用的AppKey，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String APPKEY = "dingo8vlbsczlaeqm0vf";
    /**
     * 应用的AppSecret，登录开发者后台，点击应用管理，进入应用详情可见
     */
    public static final String APPSECRET = "Mb4E1rXSXJrCqAhZ2AF-_9uOi5fUo1HQCpGQYzP0oyJR_bZgAxqetFc6PV0kvHjo";

    /**
     * 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取,您可以随机生成
     */
    public static final String ENCODING_AES_KEY = "NKfjZJrNlZUIhcJPVURipXi9V6cduQcc5T5hBEJu5py";

    /**
     * 加解密需要用到的token，企业可以随机填写。如 "12345"
     */
    public static final String TOKEN = "123456";

    /**
     * 应用的agentdId，登录开发者后台可查看
     */
    public static final Long AGENTID = 317434151L;

    /**
     * 审批模板唯一标识，可以在审批管理后台找到
     */
    public static final String PROCESS_CODE = "PROC-B0146028-7587-42A5-8702-A3B17C29FAF5";

    /**
     * 回调host
     */
    public static final String CALLBACK_URL_HOST = "http://192.168.0.112:8080/grainbig-0.0.1-SNAPSHOT/county/getCounty";
}
