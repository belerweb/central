package com.belerweb.central.service;

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.belerweb.central.dao.MongoDao;
import com.belerweb.central.model.User;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private MongoDao mongoDao;

  @Autowired
  private FreeMarkerConfigurer freeMarkerConfigurer;

  public User getUser(String property, Object value) {
    return mongoDao.createQuery("User").eq(property, value).findObject(User.class);
  }

  public User getUser(String id) {
    return mongoDao.findById("User", User.class, id);
  }

  public User signup(String mobile, String fullname, String password) {
    User user = new User();
    user.setId(UUID.randomUUID().toString());
    user.setMobile(mobile);
    user.setFullname(fullname);
    user.setPassword(new ShaPasswordEncoder(256).encodePassword(password, null));
    user.setActivationCode(RandomStringUtils.randomNumeric(6));
    mongoDao.createObject("User", user);
    return user;
  }

  public void active(String userId) {
    mongoDao.createQuery("User").eq("_id", userId).modify().set("enabled", Boolean.TRUE)
        .set("activationCode", null).update();
  }

  public void updateUserSite(String userId, String property, Object value) {
    mongoDao.createQuery("Site").eq("_id", userId).modify().set(property, value).update();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    String property = null;
    if (username.matches("^\\d+$")) {
      property = "mobile";
    } else if (username.contains("@")) {
      property = "email";
    } else {
      property = "username";
    };

    User user = mongoDao.createQuery("User").eq(property, username).findObject(User.class);
    if (user == null) {
      throw new UsernameNotFoundException("用户不存在");
    }
    return new User.UserWrapper(username, user);
  }
}
