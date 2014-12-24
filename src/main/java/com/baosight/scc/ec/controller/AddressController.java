package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.Address;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.AddressService;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.web.AddressJSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by zodiake on 2014/5/22.
 */
@Controller
@RequestMapping("/buyerCenter")
public class AddressController extends AbstractController {
    @Autowired
    private AddressService service;
    @Autowired
    private EcUserService userService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private AddressService addressService;
    @PersistenceContext
    private EntityManager em;

    public static final String CREATE = "address_create";
    public static final String SELECT_ADDRESS = "address/selectAddress";
    public static final String ADDRESS_LIST = "address/addressList";
    public static final String ADDRESS_MANAGE = "address_manage";
    public static final String EDIT = "address_edit";
    //地址提交成功
    private final static String SUBMIT_OK = "redirect:/buyerCenter/address";

    /**
     * 添加新地址
     *
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/address", method = RequestMethod.GET)
    public String create(Model uiModel) {
        EcUser currentUser = userContext.getCurrentUser();
        EcUser ecUser = em.find(EcUser.class, currentUser.getId());
        Address address = new Address();
        List<Address> addresses = service.findByUser(currentUser);
        uiModel.addAttribute("addresses", addresses);
        uiModel.addAttribute("address", address);
        uiModel.addAttribute("defaultAdd", ecUser.getDefaultAddress());
        return ADDRESS_MANAGE;
    }

    /**
     * 收货地址管理保存或更新address
     *
     * @param request
     * @param uiModel
     * @param address
     * @param result
     * @param locale
     * @return
     */
    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public String save(HttpServletRequest request, Model uiModel, @Valid @ModelAttribute("address") Address address, BindingResult result, Locale locale) {
        if (result.hasErrors()) {
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("address.required", new Object[]{}, locale)));
            uiModel.addAttribute("address", address);
            return CREATE;
        } else {
            String id = request.getParameter("id");
            List<String> defaultAddresses = address.getDefaultAddress();
            String defaultAddress = "0";
            if (defaultAddresses != null) {
                defaultAddress = "1";
            }
            EcUser user = userContext.getCurrentUser();
            EcUser ecUser = em.find(EcUser.class, user.getId());
            address.setUser(ecUser);
            Address defaultAdd = new Address();
            if (StringUtils.isEmpty(id)) {
                defaultAdd = service.save(address);
            } else {
                address.setId(id);
                defaultAdd = service.update(address);
                if (ecUser.getDefaultAddress() != null) {
                    if (id.equals(ecUser.getDefaultAddress().getId()) && "0".equals(defaultAddress)) {
                        ecUser.setDefaultAddress(null);
                        userService.save(ecUser);
                    }
                }
            }
            if ("1".equals(defaultAddress)) {
                ecUser.setDefaultAddress(defaultAdd);
                EcUser u = userService.save(ecUser);
            }
            return SUBMIT_OK;
        }
    }

    /**
     * 地址管理页面设置默认地址
     *
     * @param addressId
     * @return
     */
    @RequestMapping(value = "/defaultAddress/{addressId}", method = RequestMethod.GET)
    public String defaultAddress(@PathVariable("addressId") String addressId, Model uiModel) {
        EcUser user = userContext.getCurrentUser();
        Address address = service.findById(addressId);
        user.setDefaultAddress(address);
        userService.save(user);
        List<Address> addresses = addressService.findByUser(user);
        uiModel.addAttribute("defaultAdd", address);
        uiModel.addAttribute("addresses", addresses);
        return ADDRESS_LIST;
    }


    /**
     * 查看地址
     *
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") String id, Model uiModel) {
        EcUser currentUser = userContext.getCurrentUser();
        EcUser ecUser = em.find(EcUser.class, currentUser.getId());
        Address address = service.findById(id);
        List<Address> addresses = service.findByUser(currentUser);
        List<String> defaultAddress = new ArrayList<String>();
        if (ecUser.getDefaultAddress() != null) {
            if (address.equals(ecUser.getDefaultAddress())) {
                defaultAddress.add("1");
                address.setDefaultAddress(defaultAddress);
            } else {
                defaultAddress.add("0");
                address.setDefaultAddress(defaultAddress);
            }
        } else {
            defaultAddress.add("0");
            address.setDefaultAddress(defaultAddress);
        }

        uiModel.addAttribute("addresses", addresses);
        uiModel.addAttribute("address", address);
        uiModel.addAttribute("defaultAdd", ecUser.getDefaultAddress());
        return EDIT;
    }

    /**
     * 地址管理页面删除地址 added by Charles
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/address/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") String id, Model uiModel) {
        EcUser user = userContext.getCurrentUser();
        service.delete(id, user);
        EcUser ecUser = em.find(EcUser.class, user.getId());
        Address defaultAd = ecUser.getDefaultAddress();
        List<Address> addresses = service.findByUser(user);
        uiModel.addAttribute("defaultAdd", defaultAd);
        uiModel.addAttribute("addresses", addresses);
        return ADDRESS_LIST;
    }

    /**
     * 订单提交页面保存或更新address
     *
     * @param request
     * @param uiModel
     * @param address
     * @param result
     * @param locale
     * @return
     */
    @RequestMapping(value = "/saveAddress", method = RequestMethod.POST)
    public String saveAdd(HttpServletRequest request, Model uiModel, @Valid @ModelAttribute("address") Address address, BindingResult result, Locale locale) {
        if (result.hasErrors()) {
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("address.required", new Object[]{}, locale)));
            uiModel.addAttribute("address", address);
            return CREATE;
        } else {
            String id = request.getParameter("addressId");
            String defaultAddress = request.getParameter("defaultAddress");
            EcUser user = userContext.getCurrentUser();
            EcUser ecUser = em.find(EcUser.class, user.getId());
            address.setUser(ecUser);
            Address defaultAdd = new Address();
            if (StringUtils.isEmpty(id)) {
                defaultAdd = service.save(address);
            } else {
                address.setId(id);
                defaultAdd = service.update(address);
                if (ecUser.getDefaultAddress() != null) {
                    if (id.equals(ecUser.getDefaultAddress().getId()) && "0".equals(defaultAddress)) {
                        ecUser.setDefaultAddress(null);
                        userService.save(ecUser);
                    }
                }
            }
            if ("1".equals(defaultAddress)) {
                ecUser.setDefaultAddress(defaultAdd);
                EcUser u = userService.save(ecUser);
            }
            Address defaultAd = ecUser.getDefaultAddress();
            List<Address> addresses = addressService.findByUser(user);
            uiModel.addAttribute("defaultAddress", defaultAd);
            uiModel.addAttribute("newAdd", defaultAdd);
            uiModel.addAttribute("addresses", addresses);
            return SELECT_ADDRESS;
        }
    }

    /**
     * 订单提交页面删除地址 added by Charles
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/address/deleteAdd/{id}", method = RequestMethod.GET)
    public String deleteAdd(@PathVariable("id") String id, Model uiModel) {
        EcUser user = userContext.getCurrentUser();
        EcUser source = service.delete(id, user);
        Address defaultAd = source.getDefaultAddress();
        List<Address> addresses = service.findByUser(user);
        uiModel.addAttribute("defaultAddress", defaultAd);
        uiModel.addAttribute("addresses", addresses);
        return SELECT_ADDRESS;
    }

    /**
     * 订单提交页面查看地址单个地址
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/viewAddress/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map viewAdd(@PathVariable("id") String id) {
        Map result = new HashMap();
        EcUser user = userContext.getCurrentUser();
        EcUser ecUser = em.find(EcUser.class, user.getId());
        String defaultAddress = "0";
        if (ecUser.getDefaultAddress() != null) {
            if (ecUser.getDefaultAddress().getId().equals(id)) {
                defaultAddress = "1";
            }

        }
        Address address = service.findById(id);
        result.put("result", "success");
        result.put("id", id);
        result.put("receiverName", address.getReceiverName());
        result.put("state", address.getState());
        result.put("city", address.getCity());
        result.put("street", address.getStreet());
        result.put("zipCode", address.getZipCode());
        result.put("mobile", address.getMobile());
        result.put("zipPhone", address.getZipPhone());
        result.put("phone", address.getPhone());
        result.put("childPhone", address.getChildPhone());
        result.put("defaultAddress", defaultAddress);
        return result;
    }

    /**
     * 订单提交页面设置默认地址
     *
     * @param addressId
     * @return
     */
    @RequestMapping(value = "/setDefaultAddress/{addressId}", method = RequestMethod.GET)
    public String setDefaultAddress(@PathVariable("addressId") String addressId, Model uiModel) {
        EcUser user = userContext.getCurrentUser();
        Address address = service.findById(addressId);
        user.setDefaultAddress(address);
        userService.save(user);
        List<Address> addresses = addressService.findByUser(user);
        uiModel.addAttribute("defaultAddress", address);
        uiModel.addAttribute("addresses", addresses);
        return SELECT_ADDRESS;
    }
}
