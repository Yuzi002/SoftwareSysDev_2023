package com.Yuzi.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//编码方式的过滤器，编码为UTF-8
@WebFilter("/*")//匹配所有的请求
public class EncodingFilter implements Filter {

  //初始化方法
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  //执行方法
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    //Servlet转成HttpServlet
    HttpServletRequest request= (HttpServletRequest) servletRequest;
    HttpServletResponse response= (HttpServletResponse) servletResponse;
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    filterChain.doFilter(request,response);
  }

  //销毁方法
  @Override
  public void destroy() {
    Filter.super.destroy();
  }
}
