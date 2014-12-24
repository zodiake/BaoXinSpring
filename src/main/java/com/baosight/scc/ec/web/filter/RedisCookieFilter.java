package com.baosight.scc.ec.web.filter;

import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2014/10/14.
 */
public class RedisCookieFilter implements Filter {
    private static final String ANNOY = "annoy";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Cookie[] cookies = req.getCookies();
        if (cookies != null && existCookie(cookies))
            chain.doFilter(request, response);
        else {
            Cookie cookie = new Cookie(ANNOY, GuidUtil.newGuid());
            cookie.setMaxAge(60 * 60 * 10);
            res.addCookie(cookie);
            chain.doFilter(request, response);
        }
    }

    private boolean existCookie(Cookie[] cookies) {
        for (Cookie c : cookies) {
            if (c.getName().equals("annoy")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
