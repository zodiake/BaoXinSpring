package com.baosight.scc.ec.rmi;

import com.baosight.scc.ec.model.EcProvider;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sam on 2014/7/22
 */
public class Test {

    static String url = "http://10.70.82.33:8080/buap/restservice/getCompanyInfoByUserName";

    public static void getAllCompanyInfoByUserName() {
        org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
        PostMethod putMethod = new PostMethod(url);
        putMethod.setRequestBody("100048");
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);//设置超时
//HttpResponse httpResponse = httpClient.execute(httpGet);
        int status;
        String r = "";
        try {
            status = httpClient.executeMethod(putMethod);
            r =putMethod.getResponseBodyAsString();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            getProvider(r);
        }


        System.out.println(r);


    }

    public static void main(String args[]){
        System.out.println("=====================================================");
            getAllCompanyInfoByUserName();

        String str="aaa,";
        if(str.endsWith(","))
            str = str.substring(0,str.length()-1);
        System.out.print(str);

    }


    public static void getProvider(String str){
        System.out.println("====接收到的json="+str);
        EcProvider ecProvider = null;
        JSONObject jsonObject = JSONObject.fromObject(str);
    //    jsonObject.remove("createTime");
        System.out.println("=========处理后的json="+jsonObject);
    //    ecProvider=(EcProvider)JSONObject.toBean(jsonObject,EcProvider.class);
    }



}
