package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Shop;
import com.baosight.scc.ec.model.SourceUserInfo;
import com.baosight.scc.ec.model.UserInfo;
import com.baosight.scc.ec.search.properties.CompositeQueryParam;
import com.baosight.scc.ec.search.properties.SearchParam;
import com.baosight.scc.ec.search.service.EsService;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by zodiake on 2014/7/22.
 */
@Controller
public class ProviderInfoController extends AbstractSearchController {
    Logger logger = LoggerFactory.getLogger(ProviderInfoController.class);
    @Autowired
    private EsService esService;
    @Autowired
    private EcUserService userService;

    @RequestMapping(produces = "text/html", value = "/synchronize/shop", method = RequestMethod.POST)
    @ResponseBody
    public String insertSearch(@RequestBody String userInfo) {
        UserInfo user = convertStringToUserInfo(userInfo);
        if (user != null) {
            if (!exist(user))
                esService.insertList("user", convertObjectToList(user));
            else
                esService.updateById("user", user.getId(), convertObjectToMap(user));
            return "ok";
        } else
            return "fail";
    }

    private boolean exist(UserInfo user) {
        CompositeQueryParam query = new CompositeQueryParam();
        SearchParam param = new SearchParam("id", user.getId(), false);
        query.addQueryMustParam(param);
        Long count = esService.count("user", query);
        return count > 0;
    }

    private UserInfo convertStringToUserInfo(String userInfo) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(userInfo);
            UserInfo info = new UserInfo();
            info.setId((String) object.get("id"));
            info.setMianProductService((String) object.get("mianProductService"));
            info.setCompanyName((String) object.get("companyName"));
            info.setCompanyRemark((String) object.get("companyRemark"));
            info.setBusiness((String) object.get("business"));
            info.setOperatingCapital(Long.parseLong((String) object.get("operatingCapital")));
            info.setLogo((String) object.get("logo"));
            String type = (String) object.get("userType");
            info.setUserType(convertStringToRole(type));
            info.setYyzz((String) object.get("yyzz"));
            return info;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> convertStringToRole(String sb) {
        List<String> roles = new LinkedList<String>();
        if (sb.contains(",")) {
            String[] roleArray = sb.split(",");
            for (int i = 0; i < roleArray.length; i++) {
                if (roleArray[i].equals("ml")) {
                    roles.add("面料");
                } else if (roleArray[i].equals("fl")) {
                    roles.add("辅料");
                }
            }
        } else {
            if (sb.equals("ml"))
                roles.add("面料");
            else if (sb.equals("fl"))
                roles.add("辅料");
        }
        return roles;
    }

    @RequestMapping(produces = "text/html", value = "/synchronize/shop", method = RequestMethod.PUT)
    @ResponseBody
    public String updateSearch(@RequestBody String userInfo) {
        UserInfo user = convertStringToUserInfo(userInfo);
        esService.deleteById("user", user.getId());
        esService.insertList("user", convertObjectToList(user));
        return "ok";
    }

    @RequestMapping(value = "/synchronize/info", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String synchronize(@RequestBody String userInfo) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject object = (JSONObject) parser.parse(userInfo);
            String id = (String) object.get("id");
            String token = (String) object.get("token");
            SourceUserInfo source = new SourceUserInfo();
            source.setId(id);
            source.setToken(token);

            if (validateToken(source)) {
                EcUser user = new EcUser();
                user.setId(source.getId());
                Shop shop = new Shop();
                shop.setId(GuidUtil.newGuid());
                shop.setUser(user);
                user.getShops().add(shop);
                userService.save(user);
                return "ok";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    private boolean validateToken(SourceUserInfo userInfo) {
        String id = userInfo.getId() + " ";
        String hex = DigestUtils.md5Hex(id);
        logger.debug("id:" + userInfo.getId());
        logger.debug("hex:" + hex);
        logger.debug("token:" + userInfo.getToken());
        if (hex.equals(userInfo.getToken()))
            return true;
        else
            return false;
    }

    private List<Map<String, Object>> convertObjectToList(UserInfo userInfo) {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        lists.add(convertObjectToMap(userInfo));
        return lists;
    }

    private Map<String, Object> convertObjectToMap(UserInfo userInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("_id", userInfo.getId());
        map.put("focus", userInfo.getMianProductService());
        map.put("desc", userInfo.getCompanyRemark());
        map.put("name", userInfo.getCompanyName());
        map.put("scope", userInfo.getBusiness());
        map.put("money", userInfo.getOperatingCapital());
        map.put("type", userInfo.getUserType());
        map.put("cover", userInfo.getLogo());
        map.put("yyzz", userInfo.getYyzz());
        map.put("score", 0.1);
        logger.info(map.toString());
        return map;
    }
}
