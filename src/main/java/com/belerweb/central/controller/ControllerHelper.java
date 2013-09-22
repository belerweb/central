package com.belerweb.central.controller;

import java.lang.reflect.Method;

import org.springframework.security.core.context.SecurityContextHolder;

import com.belerweb.central.model.User;
import com.belerweb.central.view.ViewHelper;


public abstract class ControllerHelper extends ViewHelper {

  protected User getUser() {
    try {
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      Method getDetail = principal.getClass().getMethod("getDetail");
      return (User) getDetail.invoke(principal);
    } catch (Exception e) {
      return null;
    }
  }

}
