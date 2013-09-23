package com.belerweb.central.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.belerweb.central.model.App;
import com.belerweb.central.model.AppConfig;
import com.belerweb.central.model.User;
import com.belerweb.central.service.AppService;
import com.belerweb.central.service.SmsService;
import com.belerweb.central.service.TemplateService;
import com.belerweb.central.service.UserService;

@Controller
public class IndexController extends ControllerHelper {

  @Autowired
  private UserService userService;
  @Autowired
  private SmsService smsService;
  @Autowired
  private TemplateService templateService;
  @Autowired
  private AppService appService;

  @RequestMapping("/")
  public Object root(HttpServletRequest request, Model model) {
    String serverName = request.getServerName();
    if (!serverName.startsWith("api.")) {
      return "redirect:/login";
    }

    if (!"post".equalsIgnoreCase(request.getMethod())) {
      return illegal();
    }

    String key = request.getParameter("app");
    App app = appService.getApp(key);
    if (app == null) {
      return illegal();
    }

    String appKey = request.getParameter("appSecret");
    String appSecret = request.getParameter("appSecret");
    if (!app.getAppKey().equals(appKey) || !app.getAppSecret().equals(appSecret)) {
      return illegal();
    }

    String profile = request.getParameter("profile");
    List<AppConfig> configs = app.getDevelopment();
    if ("test".equals(profile)) {
      configs = app.getTest();
    }
    if ("production".equals(profile)) {
      configs = app.getProduction();
    }
    Map<String, String> result = new HashMap<>();
    for (AppConfig config : configs) {
      result.put(config.getKey(), config.getValue());
    }
    return json(result);
  }

  @RequestMapping("/index.html")
  public Object index(HttpServletRequest request, Model model) {
    return "redirect:/login";
  }

  @SuppressWarnings("deprecation")
  @RequestMapping("/login.html")
  public Object login(Model model, HttpSession session) {
    Object exception = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    if (exception instanceof DisabledException) {
      model.addAttribute("activation", true);
      model.addAttribute("mobile", ((DisabledException) exception).getAuthentication()
          .getPrincipal());
    }

    return "/v1/login";
  }

  /**
   * 注册
   */
  @RequestMapping(method = RequestMethod.POST, value = "/signup")
  public Object signup(@RequestParam(required = false) String mobile,
      @RequestParam(required = false) String fullname,
      @RequestParam(required = false) String password,
      @RequestParam(required = false) String repassword, HttpServletRequest request) {
    if (mobile == null || !smsService.isValidMobile(mobile)) {
      return error("请输入正确的手机号码。");
    }
    if (userService.getUser("mobile", mobile) != null) {
      return error("该手机号已被注册。");
    }
    if (StringUtils.isEmpty(fullname)) {
      return error("请输入您的尊姓大名。");
    }
    if (fullname.length() > 16) {
      return error("姓名不能超过16字。");
    }
    if (StringUtils.isEmpty(password)) {
      return error("请输入您的密码。");
    }
    if (password.length() < 6 || password.length() > 16) {
      return error("密码长度在6～16之间。");
    }
    if (!password.equals(repassword)) {
      return error("两次输入的密码不一致。");
    }

    User user = userService.signup(mobile, fullname, password);
    String smsContent = null;
    try {
      Map<String, Object> dataModel = new HashMap<>();
      dataModel.put("server", request.getServerName());
      dataModel.put("user", user);
      smsContent = templateService.render("/signup/code.ftl", dataModel);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (smsContent == null || !smsService.send(mobile, smsContent)) {
      return error("短信发送失败。");
    }

    return ok("注册成功。");
  }

  /**
   * 注册
   */
  @RequestMapping(method = RequestMethod.POST, value = "/signup/activate")
  public Object activate(@RequestParam String mobile, @RequestParam String activationCode,
      HttpServletRequest request) {
    User user = userService.getUser("mobile", mobile);
    if (user == null) {
      return error("帐号未注册。");
    }
    if (!activationCode.equals(user.getActivationCode())) {
      return error("激活码不正确。");
    }

    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(new User.UserWrapper(mobile, user), null, user
            .getWrapperRoles()));
    userService.active(user.getId());
    return ok();
  }

  /**
   * 登录后跳转
   */
  @RequestMapping("/redirect")
  public Object redirect() {
    return "redirect:/home";
  }

  /**
   * 登录后首页
   */
  @RequestMapping("/home")
  public Object home(Model model) {
    return "/v1/home";
  }

}
