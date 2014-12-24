package com.baosight.scc.ec.rest;
import com.baosight.common.utils.JSONUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class userAPI {
	/**
	 * 服务器地址
	 */
	public static String serverAddress = "http://10.70.82.33:8080";
	
	/**
	 * 添加用户
	 */
	public static void addUser(){
		ClientRequest restRequest = new ClientRequest(serverAddress+"/restservice/bindMobile");
		restRequest.header("content-type", "application/json");
		String account = "T01986";//工号
		String cpu = "cpu123";//cpu 名称
		String memory = "125K";// 内存大小
		String mobileId = "m18616647412"; //设备id
		String mobileType = "T123JS"; //设备类型
		String mobileBrand = "sungxing";//设备品牌
		String mobileModel = "JX123LK";//设备型号
		String mobileMac = "98-09-08-34";//MAC地址
		String mobileOs = "ISO-00356";//操作系统
		String resolution = "678*1024";//屏幕分辨率
		String body =	"{\"empCode\":\"" + account + "\",\"cpu\":\""+cpu +"\",\"memory\":\""+ memory +"\",\"mobileId\":\""+ mobileId +"\",\"mobileType\":\""+ mobileType +"\",\"mobileBrand\":\""+ mobileBrand +"\",\"mobileModel\":\""+ mobileModel +"\",\"mobileMac\":\""+ mobileMac +"\",\"mobileOs\":\""+ mobileOs +"\",\"resolution\":\""+ resolution +"\"}";
		//body += "\"hardDisk\":\"[{\"Model\":\"JSK\",\"SerialNumber\":\"123456789\",\"Size\":\"120G\"}]\",\"cdrom\":\"[{}]\"";
		//String body = "";
	    restRequest.body("application/json", body);
		ClientResponse<String> restResponse;
		try {
			restResponse = restRequest.post(String.class);
			String result = "";
			if (restResponse.getStatus() == 200) {
				result = restResponse.getEntity();
				System.out.println("result==" + result);
			}
			String info = restResponse.getEntity();
			System.out.println(info);
		      //打印服务器返回的状态
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	/**
	 * 获取用户信息
	 */
	public static void getUserInfo(){
		ClientRequest restRequest = new ClientRequest(serverAddress+"/restservice/getInfoByUserName");
		restRequest.header("content-type", "application/json");
		
		//SchemeRegistry
		/*Interceptor interceptor=new Interceptor();
		restRequest.registerInterceptor(interceptor);*/
		
		//ClientRequestManager m = new ClientRequestManager();
		//String body = "{\"userIds\":\"test1\",\"userType\":\"\",\"offset\":\"0\",\"limit\":\"-1\"}";
		
		String body = "test1";
		
		restRequest.body("application/json", body);
		ClientResponse<String> restResponse;
		int i;
		try {
			restResponse = restRequest.post(String.class);
			
			String result = "";
			if (restResponse.getStatus() == 200) {
				result = restResponse.getEntity();
				System.out.println("result==" + result);
				
				Map param = (HashMap)JSONUtil.getInstance().json2Object(result, HashMap.class);
				
				/*JSONObject jsonObject = JSONObject.fromObject(result);
				Map param = (HashMap)JSONObject.toBean(jsonObject, HashMap.class);*/
				String tatol = (String)param.get("count");
				List datalist = (ArrayList)param.get("data");
				 i = datalist.size();
				 Map m = (Map)datalist.get(0);
				 String empCode = (String)m.get("userName");
				 String email = (String)m.get("email");
				
			}
			String info = restResponse.getEntity();
			System.out.println(info);
		      //打印服务器返回的状态
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	/**
	 * 获取用户数
	 */
	public static void getCountUserInfo(){
		ClientRequest restRequest = new ClientRequest(serverAddress+"/buap/restservice/getCountByUserType");
		restRequest.header("content-type", "application/json");
		
		//SchemeRegistry
		/*Interceptor interceptor=new Interceptor();
		restRequest.registerInterceptor(interceptor);*/
		
		//ClientRequestManager m = new ClientRequestManager();
		String body = "{\"loginnames\":\"\",\"userType\":\"D\"}";
		
		restRequest.body("application/json", body);

		ClientResponse<String> restResponse;
		int i;
		try {
			restResponse = restRequest.post(String.class);
			String result = "";
			if (restResponse.getStatus() == 200) {
				result = restResponse.getEntity();
				System.out.println("result==" + result);
				
				
				
			}
		      //打印服务器返回的状态
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	
	public static String sendSSLPostRequest(String reqURL
			) {
		long responseLength = 0; // 响应长度
		String responseContent = null; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		X509TrustManager xtm = new X509TrustManager() { // 创建TrustManager
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext ctx = SSLContext.getInstance("TLS");

			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);

			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

			// 通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry()
					.register(new Scheme("https", 443, socketFactory));

			HttpGet httpGet = new HttpGet(reqURL); // 创建HttpPost
			/*List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 构建POST请求的表单参数
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formParams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}*/
			//httpGet.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));

			HttpResponse response = httpClient.execute(httpGet); // 执行get请求
			HttpEntity entity = response.getEntity(); // 获取响应实体

			if (null != entity) {
				responseLength = entity.getContentLength();
				responseContent = EntityUtils.toString(entity, "UTF-8");
				Map accMap = (Map)JSONUtil.getInstance().json2Object(responseContent, Map.class);//json  转成MAP
				EntityUtils.consume(entity); // Consume response content
			}
			System.out.println("请求地址: " + httpGet.getURI());
			System.out.println("响应状态: " + response.getStatusLine());
			System.out.println("响应长度: " + responseLength);
			System.out.println("响应内容: " + responseContent);
			
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown(); // 关闭连接,释放资源
		}
		return responseContent;
	}
	
	/**
	 * 删除用户
	 */
	public static void delUser(){
		ClientRequest restRequest = new ClientRequest(serverAddress+"/buap/restservice/userapi/delUser");
		restRequest.header("content-type", "application/json");
		//account账户工号，resGuid来源
		String body = "{\"account\":\"buapt001\",\"resGuid\":\"buap\"}";
		//String body = "";
	    restRequest.body("application/json", body);
		ClientResponse<String> restResponse;
		try {
			restResponse = restRequest.post(String.class);
			String result = "";
			if (restResponse.getStatus() == 200) {
				result = restResponse.getEntity();
				System.out.println(result);
			}
		    //打印服务器返回的状态
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String bytesToHexString(byte[] src){ 
        StringBuilder stringBuilder = new StringBuilder(); 
        if (src == null || src.length <= 0) { 
            return null; 
        } 
        for (int i = 0; i < src.length; i++) { 
            int v = src[i] & 0xFF; 
            String hv = Integer.toHexString(v); 
            if (hv.length() < 2) { 
                stringBuilder.append(0); 
            } 
            stringBuilder.append(hv); 
        } 
        return stringBuilder.toString(); 
    } 
	
	

	
	public static void main(String[] args) throws IOException{
		
		//getCountUserInfo();
		getUserInfo();

		//sendSSLPostRequest("https://bca.baogang.info/buap/restservice/user/T01986");

	}
	
}
