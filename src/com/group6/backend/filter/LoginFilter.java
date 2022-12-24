package com.group6.backend.filter;

import com.group6.backend.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"*.action", "*.html"})
public class LoginFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    //Servlet转成HttpServlet
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    //获取路径
    var path = request.getServletPath();
    //判断过滤器拦截的信息请求
    switch (path) {
      case "/login.action":
      case "/register.action":
      case "/jsp/login.html":
        filterChain.doFilter(request, response);
        break;
      default: {//其他所有需要权限的页面
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
          filterChain.doFilter(request, response);
        } else {
          response.sendRedirect("/jsp/login.html");
        }
      }
    }
  }

  @Override
  public void destroy() {
    Filter.super.destroy();
  }
}
