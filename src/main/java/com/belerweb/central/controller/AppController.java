package com.belerweb.central.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.belerweb.central.model.App;
import com.belerweb.central.service.AppService;

@Controller
public class AppController extends ControllerHelper {

  @Autowired
  private AppService appService;

  @RequestMapping("/app/create")
  public Object create(@RequestParam String key, @RequestParam String name, Model model) {
    name = name.trim();
    key = key.trim();

    if (!name.matches("^.{1,32}$")) {
      return error("请填写应用名称，应用名称不能超过32个字符。");
    }

    if (!key.matches("^[a-zA-Z0-9-]{6,16}$")) {
      return error("应用代码由英文字母和数字组成，长度在6到16之间。");
    }

    App app = appService.getApp(key);
    if (app != null) {
      return error("应用已经存在，请修改应用代码。");
    }

    app = appService.addApp(getUser().getId(), key, name);
    return json(app);
  }

  @RequestMapping("/app")
  public Object app(Model model) {
    model.addAttribute("apps", appService.getUserApp(getUser().getId()));
    return "/v1/apps";
  }

  @RequestMapping("/app/{key}")
  public Object app(@PathVariable String key, Model model) {
    App app = appService.getApp(key);
    if (app == null || !getUser().getId().equals(app.getUserId())) {
      return illegal();
    }

    model.addAttribute("app", app);
    return "/v1/app";
  }

}
