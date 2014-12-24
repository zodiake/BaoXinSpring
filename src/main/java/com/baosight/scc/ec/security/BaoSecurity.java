package com.baosight.scc.ec.security;

import com.baosight.buapx.security.handler.IAuthPostHandler;
import com.baosight.buapx.security.userdetails.SecurityUserInfo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zodiake on 2014/6/20.
 */
@Component
public class BaoSecurity implements IAuthPostHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse httpServletResponse, SecurityUserInfo securityUserInfo, boolean b) {

    }
}
