package com.belerweb.central.service;

import org.springframework.stereotype.Service;

import com.belerweb.sms._9nuo.Sms;

@Service
public class SmsService {

  private Sms sms = Sms.init();

  public boolean isValidMobile(String mobile) {
    return mobile
        .matches("^(139|138|137|136|135|134|147|150|151|152|157|158|159|182|183|184|187|188|130|131|132|155|156|185|186|145|133|153|180|181|189)\\d{8}$");
  }

  public boolean send(String to, String content) {
    return sms.send(to, content).isSuccess();
  }

}
