package com.group6.backend.servlet;

import com.group6.backend.dao.impl.UserDaoImpl;
import com.group6.backend.pojo.R;
import com.group6.backend.pojo.User;
import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/*
 * 登录控制器
 * 接收前端发送的请求
 * 先继承HttpServlet*/
@WebServlet(urlPatterns = {"/jsp/login", "/getCpacha"})
public class LoginServlet extends HttpServlet {
  //    接收get请求
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        验证码功能
    //通过java中awt中提供的类绘制验证码图片
//        1.创建一张图片
    int height = 90;//验证码高度
    int width = 300;//验证码宽度
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//实例化一个图片对象
//        2.绘制图片对象，从图片对象中获取绘制图片的笔
    Graphics2D pen = image.createGraphics();
    //a.绘制背景
    pen.setColor(getRandomColor());
    pen.fillRect(0, 0, width, height);//绘制实心矩形

    //b.绘制验证码字符串
    int letterNum = 4;//验证码图片上的字符的个数
    int space = 20;//验证码图片上两个字母之间的空隙
    int letterWidth = (width - (letterNum + 1) * space) / letterNum;//计算每个字母占据的宽度

//        for循环每循环一次，绘制一个字母（小写字母的ascii码：97-122）
    Random random = new Random();
    String code = "";
    for (int i = 0; i < letterNum; i++) {
//            随机生成一个小写字母
      int ascii = random.nextInt(26) + 97;//97-122
      byte[] bytes = {(byte) ascii};
      String letter = new String(bytes);
      code = code + letter;//为了保存验证码字符到session
//            绘制字母
      pen.setColor(getRandomColor());
      pen.setFont(new Font("Gulim", Font.BOLD, 70));
      pen.drawString(letter, space + (letterWidth + space) * i, height - space);//把该字母写在画布上

    }
    HttpSession session = request.getSession();
    session.setAttribute("code", code);
    //添加图片干扰，防止机器自动识别
//            图片绘制完成后，将图片通过response输出流响应到客户端
    ImageIO.write(image, "png", response.getOutputStream());
  }

  private Color getRandomColor() {
    Random random = new Random();
    int r = random.nextInt(256);
    int g = random.nextInt(256);
    int b = random.nextInt(256);
    Color color = new Color(r, g, b);
    return color;
  }

  private UserDaoImpl userDao = new UserDaoImpl();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String vcode = request.getParameter("vcode");
    //判断验证码是否正确
    HttpSession session = request.getSession();
    String code = (String) session.getAttribute("code");
    //判断验证码是否一致
    if (code.equals(vcode)) {
      User user = userDao.login(username, password);
      if (user != null) {
        //将当前用户信息保存在session中
        session.setAttribute("user", user);
        R r = new R();
        //将user对象保存在user中
        r.put("user", user);
        r.put("msg", "登录成功");
        out.print(new Gson().toJson(r));
      } else {
        out.print(new Gson().toJson(R.error("账号或密码错误")));
      }
    } else {
      out.print(new Gson().toJson(R.error("验证码错误，请重新输入！")));
    }
  }
}
