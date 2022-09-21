package com.itheima.reggie.filter;


import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 检查是否已完成登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器，匹配通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        //定义不需要处理的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };

        boolean check = check(urls, requestURI);
        if(check){
            filterChain.doFilter(request,response);
            return;
        }
        HttpSession session = request.getSession();
        if(session.getAttribute("employee")!=null){
            Long empId = (Long) session.getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }
//        Long empId = (Long) session.getAttribute("employee");
//        BaseContext.setCurrentId(empId);
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("拦截到请求：{}",request.getRequestURI());
//        filterChain.doFilter(request,response);
    }

    /**
     * 路径匹配：本次匹配是否需要放行
     * @param requestURI
     * @param urls
     * @return boolean
     */
    public boolean check(String[] urls,  String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
