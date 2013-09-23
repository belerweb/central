package com.belerweb.central.service;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.belerweb.central.dao.MongoDao;
import com.belerweb.central.model.App;
import com.belerweb.central.model.AppConfig;

@Service
public class AppService {

  @Autowired
  private MongoDao mongoDao;

  public App addApp(String userId, String key, String name) {
    App app = new App();
    app.setId(UUID.randomUUID().toString());
    app.setKey(key);
    app.setName(name);
    app.setUserId(userId);
    app.setAppKey(RandomStringUtils.randomAlphanumeric(16));
    app.setAppSecret(RandomStringUtils.randomAlphanumeric(16));
    app = mongoDao.createObject("App", app);
    return app;
  }

  public App getApp(String key) {
    return mongoDao.findBy("App", App.class, "key", key);
  }

  public List<App> getUserApp(String userId) {
    return mongoDao.findAllBy("App", App.class, "userId", userId);
  }

  public void addAppConfig(String key, String config) {
    AppConfig appConfig = new AppConfig();
    appConfig.setId(UUID.randomUUID().toString());
    mongoDao.createQuery("App").eq("key", key).modify().push(config, mongoDao.unmap(appConfig))
        .update();
  }

  public void updateAppConfig(String configId, String config, String name, String value) {
    mongoDao.createQuery("App").eq(config + ".id", configId).modify()
        .set(config + ".$." + name, value).update();
  }

}
