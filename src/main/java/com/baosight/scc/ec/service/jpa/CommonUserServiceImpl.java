package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.CommonUser;
import com.baosight.scc.ec.service.CommonUserService;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by sam on 2014/8/6.
 */
@Service
public class CommonUserServiceImpl implements CommonUserService {
    private Logger logger = LoggerFactory.getLogger(CommonUserServiceImpl.class);

    @Value("${commonUser.url}")
    private String commonUserUrl;
    @Value("${userType.url}")
    private String userTypeUrl;

    @Override
    public CommonUser findOne(String id) {
        return postMethod(commonUserUrl,id);
    }

    @Override
    public String findUserTypeById(String id) {
        return postStr(userTypeUrl,id);
    }
//
//    public static void main(String args[]){
//    //    new CommonUserServiceImpl().postStr("http://10.70.82.33:8080/buap/restservice/getUserTypeByUserName","100048");
//        new CommonUserServiceImpl().postMethod("http://10.70.82.33:8080/buap/restservice/getOrdinaryInfoByUserName","100089");
//    }

    public CommonUser postMethod(String url,String id){
        logger.info("发送的url="+url);
        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        PostMethod putMethod = new PostMethod(url);
        putMethod.setRequestBody(id);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);//设置超时
        //HttpResponse httpResponse = httpClient.execute(httpGet);
        int status;
        String r="";
        CommonUser commonUser = null;
        try {
            status = httpClient.executeMethod(putMethod);
            logger.info("建立请求，返回状态码==》"+status);
            if(status == 200){
                r =putMethod.getResponseBodyAsString();
                logger.info("接收的json："+r);
                commonUser=getObjectFromJson(r);
                if (commonUser == null){
                    commonUser = new CommonUser();
                    commonUser.setUserId(id);
                }else{
                    if (null == commonUser.getUserId()){
                        commonUser = new CommonUser();
                        commonUser.setUserId(id);
                    }
                }
            }else{
                commonUser = new CommonUser();
                commonUser.setUserId(id);
            }
        }catch (Exception e){
            e.printStackTrace();
            commonUser = new CommonUser();
            commonUser.setUserId(id);
        }
        return commonUser;
    }

    public CommonUser getObjectFromJson(String str){
        CommonUser commonUser = null;
        JSONObject jsonObject = JSONObject.fromObject(str);
        commonUser = (CommonUser)jsonObject.toBean(jsonObject,CommonUser.class);
        return commonUser;
    }


    public String postStr(String url,String id){
        logger.info("发送的url="+url);
        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        PostMethod putMethod = new PostMethod(url);
        putMethod.setRequestBody(id);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);//设置超时
        //HttpResponse httpResponse = httpClient.execute(httpGet);
        int status;
        String r="";
        try {
            status = httpClient.executeMethod(putMethod);
            logger.info("建立请求，返回状态码==》"+status);
            if(status == 200){
                r =putMethod.getResponseBodyAsString();
                logger.info("接收的str=："+r);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return r;
    }

}
