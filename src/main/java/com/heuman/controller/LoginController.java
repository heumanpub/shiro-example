package com.heuman.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by heuman on 2018/3/21.
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        // 进入登录页
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return "login";
        }
        String input = request.getParameter("vCode");
        String vCode = (String) request.getSession().getAttribute("vCode");
        boolean vPassed = input != null && vCode != null && input.equalsIgnoreCase(vCode);
        if (!vPassed) {
            model.addAttribute("errorMsg", "验证码错误");
            return "login";
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String errorMsg = null;
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            errorMsg = "用户名和密码不能为空";
            model.addAttribute("errorMsg", errorMsg);
            return "login";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());
        try {
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException e) {
            errorMsg = "用户名或密码错误";
        } catch (DisabledAccountException e) {
            errorMsg = "该账号已被锁定";
        } catch (AuthenticationException e) {
            errorMsg = "登录失败";
        } catch (Exception e) {
            errorMsg = "系统异常";
        }
        if (StringUtils.isNotBlank(errorMsg)) {
            model.addAttribute("errorMsg", errorMsg);
            return "login";
        }
        return "redirect:/index";
    }

    @RequestMapping("/unauthorized")
    public String deny() {
        return "unauthorized";
    }

    @RequestMapping("/captcha")
    @ResponseBody
    public void generateCaptchaImg(HttpSession session, HttpServletResponse response) {
        int width = 120;//生成的图片的宽度
        int height = 30;//生成的图片的高度
        int vCodeLength = 4;
        String vCode = null;
        try {
            vCode = generate(response, width, height, vCodeLength);
        } catch (ServletException | IOException ignored) {
        }
        session.setAttribute("vCode", vCode);
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "login";
    }

    private String generate(HttpServletResponse response, int width, int height, int vCodeLength)
            throws ServletException, IOException {
        String baseNumLetter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        Font font = new Font("Times New Roman", Font.HANGING_BASELINE, 28);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        String vCode = "";
        int size = baseNumLetter.length();
        for (int i = 0; i < vCodeLength; i++) {
            int start = random.nextInt(size);
            String strRand = baseNumLetter.substring(start, start + 1);

            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            g.drawString(strRand, 15 * i + 6, 24);
            // 将产生的四个随机数组合在一起。
            vCode += strRand;
        }
        g.dispose();
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ImageIO.write(buffImg, "jpg", response.getOutputStream());
        return vCode;
    }

    // 获取随机颜色
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
