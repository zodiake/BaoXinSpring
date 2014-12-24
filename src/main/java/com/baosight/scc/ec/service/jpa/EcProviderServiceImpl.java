package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.Designer;
import com.baosight.scc.ec.model.EcProvider;
import com.baosight.scc.ec.service.EcProviderService;
import com.baosight.scc.ec.service.EcUserService;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2014/7/22.
 */
@Service
public class EcProviderServiceImpl implements EcProviderService {

    private Logger logger = LoggerFactory.getLogger(EcProviderServiceImpl.class);

    @Value("${provider.url}")
    private String providerUrl;

    @Value("${multipleUser.url}")
    private String multipleUser;

    @Value("${countUserInfo.url}")
    private String countUserInfo;

    @Override
    public EcProvider findOne(String id) {
        //    id="100048";
        return postMethod(providerUrl, id);
    }

    @Override
    public String findMultipleUser(String userId, String userType) {
        return postMethod(multipleUser, userId, userType);
    }


    public EcProvider postMethod(String url, String id) {
        logger.info("发送的url=" + url);
        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        PostMethod putMethod = new PostMethod(url);
        putMethod.setRequestBody(id);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);//设置超时
        //HttpResponse httpResponse = httpClient.execute(httpGet);
        int status;
        String r = "";
        EcProvider ecProvider = null;
        try {
            status = httpClient.executeMethod(putMethod);
            logger.info("建立请求，返回状态码==》" + status);
            if (status == 200) {
                r = putMethod.getResponseBodyAsString();
                logger.info("接收的json：" + r);
                ecProvider = getObjectFromJson(r);
                logger.info("=====ecprovider=" + ecProvider);
                if (ecProvider == null) {
                    ecProvider = new EcProvider();
                    ecProvider.setUserName(id);
                    ecProvider.setId(id);
                } else {
                    ecProvider.setId(id);
                    ecProvider.setUserName(id);
                    if (null == ecProvider.getEmail()) {
                        ecProvider = new EcProvider();
                        ecProvider.setUserName(id);
                        ecProvider.setId(id);
                    }
                }
            } else {
                ecProvider = new EcProvider();
                ecProvider.setUserName(id);
                ecProvider.setId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ecProvider = new EcProvider();
            ecProvider.setUserName(id);
        }
        return ecProvider;
    }

    public EcProvider getObjectFromJson(String str) {
        EcProvider ecProvider = null;
        JSONObject jsonObject = JSONObject.fromObject(str);
        //    jsonObject.remove("createTime");
        ecProvider = (EcProvider) JSONObject.toBean(jsonObject, EcProvider.class);
    //    String yyzz = ecProvider.getYyzz();

        /*if (!"".equals(yyzz) && null != yyzz) {
            List<String> list = new ArrayList<String>();
            if (yyzz.endsWith(",")) {

                ecProvider.setYyzz(yyzz.substring(0, yyzz.length() - 1));
                String[] arr = ecProvider.getYyzz().split(",");
                for (int i = 0, len = arr.length; i < len; i++) {
                    list.add(arr[i]);
                }
            }else {

                list.add(yyzz);
            }

            ecProvider.setYyList(list);
        }*/


        return ecProvider;
    }

    /**
     * 根据用户id查询多个用户名称
     *
     * @param url
     * @param userId
     * @param userType
     * @return
     */
    private String postMethod(String url, String userId, String userType) {
        JSONObject object = new JSONObject();
        String param = null;
        if (userId != null) {
            object.put("userIds", userId);
            object.put("userType", userType);
            object.put("topFlag", "1");
            param = object.toString();
        } else {
            param = "{}";
        }
        StringRequestEntity requestEntity = null;
        try {
            requestEntity = new StringRequestEntity(param, "application/json", "UTF-8");
            PostMethod postMethod = new PostMethod(url);
            postMethod.setRequestEntity(requestEntity);
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(postMethod);
            int status = httpClient.executeMethod(postMethod);
            if (status == 200 || status == 204) {
                return postMethod.getResponseBodyAsString();
            } else {
                System.err.println("建立请求失败，返回状态码==》" + status);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getTopBrand(){
        JSONObject object=new JSONObject();
        object.put("userType","E");
        object.put("roleType","pp");
        object.put("offset","0");
        object.put("limit","9");
        object.put("topFlag","1");
        String response=postMethod(object,multipleUser);
        Object obj= JSONValue.parse(response);
        org.json.simple.JSONObject jobj=(org.json.simple.JSONObject)obj;
        org.json.simple.JSONArray array=(JSONArray)jobj.get("data");
        List<String> lists=new LinkedList<String>();
        for(int i=0;i<array.size();i++) {
            org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) array.get(i);
            lists.add((String)jsonObject.get("guid"));
        }
        return lists;
    }

    public List<Designer> getTopDesigner(){
        JSONObject object=new JSONObject();
        object.put("userType","D");
        object.put("offset","0");
        object.put("limit","5");
        object.put("topFlag","1");
        String response=postMethod(object,multipleUser);
        if(StringUtils.isEmpty(response)){
            return null;
        }else {
            logger.debug("response==========="+response);
            System.out.println("response==========="+response);
            Object obj= JSONValue.parse(response);
            org.json.simple.JSONObject jobj=(org.json.simple.JSONObject)obj;
            JSONArray array=(JSONArray)jobj.get("data");
            List<Designer> lists=new LinkedList<Designer>();
            for(int i=0;i<array.size();i++) {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) array.get(i);
                Designer designer=new Designer();
                designer.setId((String)jsonObject.get("guid"));
                designer.setName((String)jsonObject.get("empName"));
                lists.add(designer);
            }
            return lists;
        }

    }

    public String getBrandCount(){
        JSONObject object=new JSONObject();
        object.put("loginnames","");
        object.put("userType","E");
        object.put("roleType","pp");
        String response=postMethod(object,countUserInfo);
        return response;
    }

    public String postMethod(JSONObject object,String restUrl) {
        try {
            HttpClient client = new HttpClient();
            PostMethod postMethod = new PostMethod(restUrl);
            RequestEntity entity = new StringRequestEntity(object.toString(), "application/json", "UTF-8");
            postMethod.setRequestEntity(entity);
            int status=client.executeMethod(postMethod);
            if (status == 200 || status == 204) {
                return postMethod.getResponseBodyAsString();
            } else {
                System.err.println("建立请求失败，返回状态码==》" + status);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
