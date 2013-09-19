package com.belerweb.central.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class User implements Serializable {

  private static final long serialVersionUID = 524608810136559698L;

  private String id;
  private String mobile;// 手机号
  private String password;// 密码
  private String fullname;// 姓名
  private String activationCode;// 激活码
  private Boolean enabled = Boolean.FALSE;// 是否激活
  private Date created;// 注册时间
  private Date modified;// 最后更新时间
  private List<String> roles = new ArrayList<>();// 角色

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getActivationCode() {
    return activationCode;
  }

  public void setActivationCode(String activationCode) {
    this.activationCode = activationCode;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

  public List<SimpleGrantedAuthority> getWrapperRoles() {
    List<SimpleGrantedAuthority> roles = new ArrayList<>();
    for (String role : this.roles) {
      roles.add(new SimpleGrantedAuthority(role));
    }
    return roles;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public static class UserWrapper extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = -5930836006606431420L;
    private User user;

    public UserWrapper(String username, User user) {
      super(username, user.getPassword(), user.getEnabled(), true, true, true, user
          .getWrapperRoles());
      this.user = user;
    }

    public User getDetail() {
      return user;
    }
  }

}
