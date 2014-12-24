package com.baosight.scc.ec.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import com.baosight.common.utils.JSONUtil;
import org.springframework.beans.factory.annotation.Value;

/**
 * 取小代码rest客户端方法
 * @author wangyb
 *
 */
public class CodeAPI {

    /**
     * 服务器地址
     */
    public static String serverAddress;

    @Value("${code-url}")
    public void setPrivateName(String serverAddress) {
        CodeAPI.serverAddress = serverAddress;
    }

    /**
     * 查询状态为显示的小代码
     * @param typeCode 小代码编码
     * @return
     */
    public List getBusinessCodeList(String typeCode) {
        String url = "getBusinessCodeList";
        url = url + "?typeCode=" + typeCode;
        String result = getRestResultString(url);

        System.out.println("result==" + result);

        List datalist = (ArrayList) JSONUtil.getInstance().json2Object(result, ArrayList.class);
        return datalist;
    }

    /**
     * 查询状态为显示的小代码
     * 可以分层级查询
     * @param typeCode 小代码编码
     * @param parentValueCodePath  父代码层级值(一级代码值/二级代码值/……) valueLevel=1时可为空
     * @param valueLevel 层级(从1开始)
     * @return
     */
    public List getBusinessCodeTree(String typeCode,String parentValueCodePath,int valueLevel){
        String url = "getBusinessCodeTree";
        url = url + "?typeCode=" + typeCode;
        url = url + "&parentValueCodePath=" + parentValueCodePath;
        url = url + "&valueLevel=" + valueLevel;
        String result = getRestResultString(url);

        System.out.println("result==" + result);

        List datalist = (ArrayList)JSONUtil.getInstance().json2Object(result, ArrayList.class);
        return datalist;
    }

    /**
     * 获取小代码值名称
     * @param typeCode
     * @param codeValue
     * @return
     */
    public String getBusinessNameByValue(String typeCode, String codeValue){
        String url = "getBusinessNameByValue";
        url = url + "?typeCode=" + typeCode;
        url = url + "&codeValue=" + codeValue;
        String result = getRestResultString(url);

        System.out.println("result==" + result);

        return result;
    }

    /**
     * 获取单个小代码
     * @param typeCode 类别值
     * @param codeValue 代码值
     * @return
     */
    public Map getBusinessCodeByValue(String typeCode, String codeValue){
        String url = "getBusinessCodeByValue";
        url = url + "?typeCode=" + typeCode;
        url = url + "&codeValue=" + codeValue;
        String result = getRestResultString(url);

        System.out.println("result==" + result);

        Map para = (HashMap) JSONUtil.getInstance().json2Object(result, HashMap.class);
        return para;
    }

    /**
     * 通过父代码获取子代码列表
     * @param typeCode
     * @param parentValueId
     * @return
     */
    public List getCodeValueByParentId(String typeCode, String parentValueId) {
        String url = "getCodeValueByParentId";
        url = url + "?typeCode=" + typeCode;
        url = url + "&parentValueId=" + parentValueId;
        String result = getRestResultString(url);

        System.out.println("result==" + result);

        List datalist = (ArrayList) JSONUtil.getInstance().json2Object(result, ArrayList.class);
        return datalist;
    }

    /**
     * 获取单个小代码
     * @param typeCode 类别值
     * @param codeValue 代码名称
     * @return
     */
    public Map getBusinessCodeByName(String typeCode, String codeName){
        try {
            codeName = URLEncoder.encode(codeName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "getBusinessCodeByName";
        url = url + "?typeCode=" + typeCode;
        url = url + "&codeName=" + codeName;
        String result = getRestResultString(url);

        System.out.println("result==" + result);

        Map para = (HashMap) JSONUtil.getInstance().json2Object(result, HashMap.class);
        return para;
    }

    public static void main(String args[]) throws Exception
    {
        CodeAPI CodeAPI = new CodeAPI();
        List aa = CodeAPI.getBusinessCodeList("foushi");
        //System.out.println(aa);
        CodeAPI.getBusinessNameByValue("foushi", "00");
        List bb = CodeAPI.getBusinessCodeTree("sort","10/106",3);
        CodeAPI.getBusinessCodeByValue("foushi", "00");
        CodeAPI.getCodeValueByParentId("fabricTechnologyType", "t1");
    }

    public static String getRestResultString(String url) {
        String result = "";
        HttpClient httpClient = new HttpClient();
        try {
            PostMethod postMethod = new PostMethod(serverAddress + url);
            postMethod.getParams().setContentCharset("UTF-8");
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// 设置超时
            int status = httpClient.executeMethod(postMethod);
            if (status == 200 || status == 204) {
                result = postMethod.getResponseBodyAsString();
            } else {
                System.err.println("建立请求失败，返回状态码==》" + status);
            }
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static String getRestResultString(String url, String param) {
        String result = "";
        HttpClient httpClient = new HttpClient();
        try {
            PostMethod postMethod = new PostMethod(serverAddress + url);
            postMethod.setRequestBody(param);
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// 设置超时
            int status = httpClient.executeMethod(postMethod);
            if (status == 200 || status == 204) {
                result = postMethod.getResponseBodyAsString();
            } else {
                System.err.println("建立请求失败，返回状态码==》" + status);
            }
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static String getRestResultString(String url, InputStream fileInputStream) {
        String result = "";
        HttpClient httpClient = new HttpClient();
        try {
            PutMethod putMethod = new PutMethod(serverAddress + url);
            putMethod.setRequestHeader("content-type", MediaType.APPLICATION_OCTET_STREAM);
            putMethod.setRequestBody(fileInputStream);
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);//设置超时
            int code = httpClient.executeMethod(putMethod);
            if (code == 200 || code == 204) {
                result = putMethod.getResponseBodyAsString();
            } else {
                System.err.println("建立请求失败，返回状态码==》" + code);
            }
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static InputStream getRestResultInputStream(String url) {
        InputStream inputStream = null;
        HttpClient httpClient = new HttpClient();
        try {
            GetMethod getMethod = new GetMethod(serverAddress + url);
            int code = httpClient.executeMethod(getMethod);
            if (code == 200 || code == 204) {
                inputStream = getMethod.getResponseBodyAsStream();
            } else {
                System.err.println("建立请求失败，返回状态码==》" + code);
            }
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return inputStream;
    }
}

