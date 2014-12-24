package com.baosight.scc.ec.web.listener;

import com.baosight.scc.ec.utils.GuidUtil;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Administrator on 2014/10/15.
 */
public class SessionCreatedListener implements HttpSessionListener{
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("annoy", GuidUtil.newGuid());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
