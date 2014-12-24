package com.baosight.scc.ec.security;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.service.InsideLetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * Created by zodiake on 2014/5/12.
 */
@Component
public class EcUserContext implements UserContext {
    @Autowired
    private EcUserService service;
    @Autowired
    private InsideLetterService insideLetterService;
    @Autowired
    private HttpSession session;
    Logger logger= LoggerFactory.getLogger(EcUserContext.class);

    public EcUser getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication.getPrincipal() == null)
            logger.error("用户为空，请查找原因");
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String id=(String)session.getAttribute("id");
        if(id==null)
            id="100047";
        //modifyed by sam 2014-8-4 添加未读站内信数据
        EcUser ecUser = new EcUser(id,user);
        int total = insideLetterService.countByReceiverUserAndLetterStatus(ecUser,0);
        session.setAttribute("letterLen",total);
        ecUser.setLetterLen(total);
        //ended by sam
        return ecUser;
    }
}
