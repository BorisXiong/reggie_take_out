package com.boxing.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.boxing.reggie.common.BaseContext;
import com.boxing.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: xiongbo
 * @Description: TODO
 * @DateTime: 2022/11/23 0:29
 * 过滤器
 */

@WebFilter(filterName = "loginCheckFilter",urlPatterns = {"/*"})
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public  static  final AntPathMatcher PATH_MATCHER=new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        向下转型
        HttpServletRequest request=(HttpServletRequest)  servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        log.info("拦截到请求“{}",request.getRequestURI());


        //1.获取本次请求的UrL
        String requestUrl=request.getRequestURI();
        log.info(requestUrl);
        //2定义不需要的处理的请求路径
        String[] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };

        //2.判断本次请求是否需要处理
        boolean check = check(urls, requestUrl);

        if (check) {
            log.info("本次请求不需要处理");
            filterChain.doFilter(request,response);
            return;
        }


//        4.判断登录状态，如果已经登录则放行
        if(request.getSession().getAttribute("employee")!=null)
        {
//            放行
            log.info("用户已登录，用户为{}",request.getSession().getAttribute("employee"));
            Long employeeid = (Long) request.getSession().getAttribute("employee");

            BaseContext.setCurrentId(employeeid);

            long id = Thread.currentThread().getId();
            log.info("线程id为{}",id);

            filterChain.doFilter(request,response);
            return;


        }

        log.info("用户未登录");
        //5.如果未登录则返回登录结果，通过输出留的方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

        return;

    }

    /**
     *
     * @param urls
     * @param RequestUrl
     * @return
     */
    public boolean check(String[] urls,String RequestUrl){
            for (String url : urls) {
                boolean match = PATH_MATCHER.match(url, RequestUrl);
                if (match) {
                    return true;
                }
            }
            return false;
    }
}
