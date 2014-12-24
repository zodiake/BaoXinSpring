package com.baosight.scc.ec.security;

import com.baosight.buapx.security.handler.AbstractSpringSecurity3Handler;
import com.baosight.buapx.security.handler.IAuthPostHandler;
import com.baosight.scc.ec.service.jpa.CommonUserServiceImpl;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.baosight.scc.ec.utils.RedisConstant.ConstantKey.*;

/**
 * Created by zodiake on 2014/6/30.
 */
public class CustomPostHandler extends AbstractSpringSecurity3Handler implements IAuthPostHandler {
    private static String url;
    private static String userTypeUrl;

    private CommonUserServiceImpl commonUserService = new CommonUserServiceImpl();

    @Value("${security-postHandler}")
    public void setUrl(String url) {
        CustomPostHandler.url = url;
    }

    @Value("${userType.url}")
    public void setUserTypeUrl(String userTypeUrl) {
        CustomPostHandler.userTypeUrl = userTypeUrl;
    }

    @Override
    public List<String> loadRoles(String s) {
        List<String> roles = new ArrayList<String>();

        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(getUserInfo(s));
            String name = (String) object.get("empName");
            String role = (String) object.get("busRelation");   //主营范围，企业认证资料里填写的内容:ml-面料；fl-辅料
            String roleType = (String) object.get("roleType");  //账号角色类型，注册时选择的，供应商为gy
            String userName = (String) object.get("userName");
            boolean flag = (Boolean) object.get("auditFlag");
            String userType = commonUserService.postStr(userTypeUrl, userName);
            if (roleType != null) {
                if (roleType.indexOf("gy") > -1 && "E".equals(userType)) {
                    userType = "E";
                } else if ("M".equals(userType)) {
                    userType = "M";
                } else {
                    userType = "A";
                }
            }
            if (!flag) {
                roles.add("ROLE_USER");
                return roles;
            }
            if (role == null) {
                roles.add("ROLE_USER");
            } else {
                if (role.contains(",")) {
                    roles = convertToListRole(role, userType);
                } else {
                    roles.add(convertStringToRole(role, userType));
                }
            }
            if (userType.equals("M")) {
                roles.add("ROLE_ADMIN");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return roles;
    }

    private List<String> convertToListRole(String array, String userType) {
        Set<String> roles = new HashSet<String>();
        List<String> result = new ArrayList<String>();

        String[] roleArray = array.split(",");
        for (int i = 0; i < roleArray.length; i++) {
            String role = convertStringToRole(roleArray[i], userType);
            roles.add(role);
        }
        result.addAll(roles);
        return result;
    }

    private String convertStringToRole(String sb, String userType) {
        if (sb.equals("ml") && userType.equals("E")) {
            return "ROLE_FABRIC";
        } else if (sb.equals("fl") && userType.equals("E")) {
            return "ROLE_MATERIAL";
        } else {
            return "ROLE_USER";
        }
    }


    public void updateSession(HttpServletRequest request, String s) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(getUserInfo(s));
            JsonNode jsonName = node.get("empName");
            JsonNode jsonId = node.get("userName");
            String id = jsonId.getValueAsText();
            request.getSession().setAttribute("id", id);
            unionCart(request, id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unionCart(HttpServletRequest request, String id) {
        String annoy = (String) request.getSession().getAttribute(ANNOY);
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        StringRedisTemplate template = (StringRedisTemplate) context.getBean("redisTemplate");
        template.opsForZSet().unionAndStore(CART + annoy, CART + id, CART + id);
        template.opsForSet().unionAndStore( REDIS_SAMPLE+ annoy, REDIS_SAMPLE + id, REDIS_SAMPLE + id);
        template.delete(CART + annoy);
        template.delete(REDIS_SAMPLE+annoy);
    }

    public String getUserInfo(String param) {
        String result = "";
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        System.err.println("建立请求url==》" + url);
        postMethod.setRequestBody(param);
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(50000);// 设置超时
        try {
            int status = httpClient.executeMethod(postMethod);
            if (status == 200 || status == 204) {
                result = postMethod.getResponseBodyAsString();
            } else {
                System.err.println("建立请求失败，返回状态码==》" + status);
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
