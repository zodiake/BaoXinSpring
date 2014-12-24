package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.InsideLetter;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.service.InsideLetterService;
import com.baosight.scc.ec.web.EcGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by sam on 2014/6/27.
 */
@Controller
@RequestMapping("/letterCenter")
public class InsideLetterController extends AbstractController {

    private final static String RECEIVE_LIST = "letter/receiveList";
    private final static String SEND_LIST = "letter/sendList";
    private final static String LETTER_DETAIL = "letter/detail";
    private final static String LETTER_EDIT = "letter/edit";
//    private final static String REDIRECT = "redirect:/letterCenter/receiverLetters";
    private final static String REDIRECT = "redirect:/letterCenter/letters";
    private final static String LIST = "letter_list";
    private final static String TALK_DETAIL = "letter_talk_detail";
    private final static String REPEAT = "letter/fail";
    final Logger logger = LoggerFactory.getLogger(InsideLetterController.class);
    @Autowired
    private UserContext userContext;
    @Autowired
    private InsideLetterService insideLetterService;
    @Autowired
    private EcUserService ecUserService;
    @Autowired
    HttpSession session;

    /*
    创建表单
     */
    @RequestMapping(value = "/letter",method = RequestMethod.GET)
    public String createForm(Model model){
        InsideLetter insideLetter = new InsideLetter();
        model.addAttribute("letter",insideLetter);
        return LETTER_EDIT;
    }

    /*
    处理表单
     */
    @RequestMapping(value = "/letter",method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("letter") InsideLetter letter,
                       RedirectAttributes redirectAttributes, Locale locale,
                       HttpServletRequest request, BindingResult result){
        String str = "";
        if (result.hasErrors()){
            str= LETTER_EDIT;
            return str;
        }
        logger.info("===============================receiver="+letter.getReceiveUser().getName());
        EcUser user = userContext.getCurrentUser();
        letter.setSendUser(user);
        letter.setCreatedTime(Calendar.getInstance());
        logger.info("========================rid="+letter.getReceiveUser().getId()+"===========content="+letter.getContent());
        if (letter.getSendUser().getId().equals(letter.getReceiveUser().getId())){
            logger.info("===============自己不能够给自己发送站内信");
            str=REPEAT;
        }else{
            int count = insideLetterService.updateInsideLetterByReceiverOrSender(letter.getReceiveUser(),letter.getSendUser());
        //    insideLetterService.updateInsideLetterStatusByReceiverAndSender(1,letter.getReceiveUser(),user);
            logger.info("======================修改记录数："+count);
            letter.setFlag(1);
            if("demandOrder".equals(letter.getRefers())){
                letter.setContent(letter.getContent()+"<br/>&nbsp;&nbsp;<a href='"+letter.getUrl()+"'>求购单："+letter.getTitle()+"</a>");
            }
            insideLetterService.save(letter);
            str=REDIRECT;
        }
        return str;
    }

    /*
    收到的站内信
     */
    @RequestMapping(value = "/receiverLetters",method = RequestMethod.GET)
    public String findReceiveLetters(Model model,
                                     @RequestParam(value = "letterType",required = false,defaultValue = "1") Integer letterType,
                                     @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                     @RequestParam(value = "letterStatus",required = false,defaultValue = "2") Integer letterStatus,
                                     @RequestParam(value = "size",required = false,defaultValue = "15") Integer size){
        PageRequest pageRequest = null;
        //指定排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcUser user = userContext.getCurrentUser();
        Page<InsideLetter> insideLetterPage =insideLetterService.findLettersByReceiver(user,letterType,letterStatus,pageRequest) ;
        EcGrid<InsideLetter> grid = new EcGrid<InsideLetter>();
        grid=createGrid(insideLetterPage);
        model.addAttribute("grid", grid);
        return RECEIVE_LIST;
    }

    /*
    发出的站内信
     */
    @RequestMapping(value = "/sendLetters",method = RequestMethod.GET)
    public String findSenderLetters(Model model,
                                    @RequestParam(value = "letterType",required = false,defaultValue = "1") Integer letterType,
                                    @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                    @RequestParam(value = "letterStatus",required = false,defaultValue = "2") Integer letterStatus,
                                    @RequestParam(value = "size",required = false,defaultValue = "15") Integer size){
        PageRequest pageRequest = null;
        //指定排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcUser user = userContext.getCurrentUser();
        Page<InsideLetter> insideLetterPage =insideLetterService.findLettersBySender(user, letterType, letterStatus, pageRequest) ;
        EcGrid<InsideLetter> grid = new EcGrid<InsideLetter>();
        grid=createGrid(insideLetterPage);
        model.addAttribute("grid",grid);
        return SEND_LIST;
    }

    /*
    站内信详情
     */
    @RequestMapping(value = "/letter/{id}",method = RequestMethod.GET)
    public String findLetterDetail(Model model,@PathVariable("id") String id){
        InsideLetter insideLetter = insideLetterService.findLetterById(id);
        model.addAttribute("letter",insideLetter);
        return LETTER_DETAIL;
    }

    /*
    修改站内信状态，如：未读，已读，已删除,支持多选
     */
    @RequestMapping(value = "/updateLetterStatus",method = RequestMethod.GET)
    public String updateLetterStatus(@RequestParam(value = "status") String status,
                                     @RequestParam(value = "arrId") String arrId){
        String[] arr = arrId.split(",");
        Integer letterStatus = Integer.parseInt(status);
        insideLetterService.updateLetterStatus(arr, letterStatus);
        return "letter/test";
    }
//////////////////////////////////以上站内信代码，风格与爱企站内信风格一样，暂时先留着，以后可能会有用，站内信可能会改版////////////////////////////////////////////////////////
    /*
    站内信列表
     */
    @RequestMapping(value = "/letters",method = RequestMethod.GET)
    public String listLetters(Model model,
                              @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                              @RequestParam(value = "size",required = false,defaultValue = "15") Integer size){
        PageRequest pageRequest = null;
        //指定排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);

        EcUser user = userContext.getCurrentUser();
        Page<InsideLetter> insideLetterPage = insideLetterService.findLettersAboutUser(user, pageRequest);
        int total = insideLetterService.countByReceiverUserAndLetterStatus(user,0);
        EcGrid<InsideLetter> grid = new EcGrid<InsideLetter>();
        grid = createGrid(insideLetterPage);
        model.addAttribute("grid", grid);
        model.addAttribute("user",user);
        model.addAttribute("letterTotal",total);
        return LIST;
    }

    /*
    站内信详情
     */
    @RequestMapping(value = "/letterDetails",method = RequestMethod.GET)
    public String detailLetters(Model model,
                                @RequestParam(value = "receiveId") String receiveId,
                                @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                @RequestParam(value = "size",required = false,defaultValue = "15") Integer size){
        PageRequest pageRequest = null;
        //指定排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcUser user = userContext.getCurrentUser();
    //    EcUser receiveUser = ecUserService.findById(receiveId);
        EcUser receiveUser = new EcUser();
        receiveUser.setId(receiveId);
        Page<InsideLetter> insideLetterPage = insideLetterService.findLetterDetailsAboutUserTalks(user, receiveUser, pageRequest);
        InsideLetter letter = new InsideLetter();
        EcUser sendUser = new EcUser();
        sendUser.setId(receiveId);
        insideLetterService.updateInsideLetterStatusByReceiverAndSender(1,user,sendUser);
        int total = insideLetterService.countByReceiverUserAndLetterStatus(user,0);
        session.setAttribute("letterLen",total);
        EcGrid<InsideLetter> grid = new EcGrid<InsideLetter>();
        grid=createGrid(insideLetterPage);
        model.addAttribute("grid",grid);
        model.addAttribute("user",user);
        model.addAttribute("letter",letter);
        model.addAttribute("receiveId",receiveId);
        return TALK_DETAIL;
    }

}
