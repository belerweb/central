package com.belerweb.central.model;

import java.util.ArrayList;
import java.util.List;

public class App {

  private String id;
  private String key;
  private String name;
  private String userId;
  private List<String> whiteIPs = new ArrayList<>();
  private List<String> blackIPs = new ArrayList<>();
  private String appKey;
  private String appSecret;

  private List<AppConfig> development = new ArrayList<>();
  private List<AppConfig> test = new ArrayList<>();
  private List<AppConfig> production = new ArrayList<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public List<String> getWhiteIPs() {
    return whiteIPs;
  }

  public void setWhiteIPs(List<String> whiteIPs) {
    this.whiteIPs = whiteIPs;
  }

  public List<String> getBlackIPs() {
    return blackIPs;
  }

  public void setBlackIPs(List<String> blackIPs) {
    this.blackIPs = blackIPs;
  }

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  public List<AppConfig> getDevelopment() {
    return development;
  }

  public void setDevelopment(List<AppConfig> development) {
    this.development = development;
  }

  public List<AppConfig> getTest() {
    return test;
  }

  public void setTest(List<AppConfig> test) {
    this.test = test;
  }

  public List<AppConfig> getProduction() {
    return production;
  }

  public void setProduction(List<AppConfig> production) {
    this.production = production;
  }

}
