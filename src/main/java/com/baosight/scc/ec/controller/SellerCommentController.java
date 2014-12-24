package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.utils.UrlUtil;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by sam on 2014/5/29.
 */
@Controller
public class SellerCommentController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private EcUserService ecUserService;
    @Autowired
    private SellerCommentService sellerCommentService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderLineService orderLineService;

    private final static String EDIT_SELLERCOMMENT = "seller_comment_edit";

    private final static  String REDIRECT = "redirect:/sellerCenter/commentOk";
    private final static  String COMMENT_OK = "seller_comment_ok"; //评价成功
    private final static  String COMMENT_REPEAT = "comment/commentRepeat"; //评价重复

    final Logger logger = LoggerFactory.getLogger(SellerCommentController.class);


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    /*
    创建表单，让用户输入
     */
    @RequestMapping(value="/sellerCenter/{id}/sellerComment", method = RequestMethod.GET)
    public String createForm(Model uiModel,@PathVariable("id") String id){
        SellerComment sellerComment = new SellerComment();
        OrderItem orderItem = orderItemService.findById(id);
        for(OrderLine ol:orderItem.getLines()){
            if (ol.getItem() instanceof Fabric)
                ol.getItem().setUrl("fabric");
            else
                ol.getItem().setUrl("material");
        }
        sellerComment.setOrderItem(orderItem);
        sellerComment.setContent("感谢您的购买，您的好评是我们的动力。请多多评价我们的产品吧！");
        uiModel.addAttribute("sellerComment",sellerComment);
        return EDIT_SELLERCOMMENT;
    }

    /**
     *处理用户新建的表单数据
     */
    @RequestMapping(value="/sellerCenter/{id}/sellerComment",method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("sellerComment") SellerComment sellerComment,
                       RedirectAttributes redirectAttributes,Locale locale,
                       @PathVariable("id") String id,
                       HttpServletRequest request,BindingResult result,Model uiModel){
        if(result.hasErrors()){
            uiModel.addAttribute("sellerComment",sellerComment);
            return EDIT_SELLERCOMMENT;
        }
        String message = "非正常操作";
        Item item = itemService.findById(id);
        sellerComment.setUser(userContext.getCurrentUser());
        sellerComment.setItem(item);
        sellerComment.setCreatedTime(Calendar.getInstance());
        List<SellerComment> sellerComment1 = sellerCommentService.findByOrderItem(sellerComment.getOrderItem());
        if (null != sellerComment1 && sellerComment1.size()>0){
            message = "您已评价";
            uiModel.addAttribute("message",message);
            return COMMENT_REPEAT;
        }
        SellerComment source = this.sellerCommentService.save(sellerComment);

        //   redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("save_success", new Object[]{}, locale)));
        //    return REDIRECT + UrlUtil.encodeUrlPathSegment(source.getId(), request) + "?edit";
        message = "评价成功";
        uiModel.addAttribute("message",message);
        return REDIRECT;

    }

    @RequestMapping(value="/sellerCenter/commentOk",method = RequestMethod.GET)
    public String commentOk(Model model){
        model.addAttribute("message","seller");
        return COMMENT_OK;
    }

}
