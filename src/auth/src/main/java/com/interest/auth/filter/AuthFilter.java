package com.interest.auth.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类描述：
 *
 * @author
 */

//顺序越小，先执行
//@Order(1)
//@WebFilter(filterName = "authFilter", urlPatterns = "/*")
public class AuthFilter implements Filter
{
    protected Log logger = LogFactory.getLog(getClass());

    /**
     * 封装，不需要过滤的list列表
     */
    protected static List<Pattern> patterns = new ArrayList<Pattern>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        Pattern pattern = Pattern.compile("/authenticate");

        patterns.add(pattern);
        pattern = Pattern.compile("((.+)(html|js))|(/static/.+)");
        patterns.add(pattern);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest hreq = (HttpServletRequest) servletRequest;
        HttpServletResponse hrsp = (HttpServletResponse) servletResponse;
        logger.info("getRequestURI="+hreq.getRequestURI());
        logger.info("hreq.getPathInfo()="+hreq.getPathInfo());
        logger.info("hreq.getContextPath()="+hreq.getContextPath());

        if(isInclude(hreq.getRequestURI())){
            logger.info("uri is exclude");
            filterChain.doFilter(servletRequest,servletResponse);
            return ;
        }
        HttpSession session = hreq.getSession();
        Object username  = session.getAttribute("username");
        if(username == null){
            logger.info("no login ,exit");
            hrsp.setCharacterEncoding("UTF-8");
            hrsp.setContentType("application/json; charset=utf-8");
            hrsp.setStatus(403);
            return;
        }
        logger.info("xiejieyi, filter, username=" + username);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy()
    {

    }

    private boolean isInclude(String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
}
