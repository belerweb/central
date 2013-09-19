package com.belerweb.central.service;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class TemplateService {

  @Autowired
  private FreeMarkerConfigurer freeMarkerConfigurer;

  public String render(String templateFile, Object dataModel) throws IOException, TemplateException {
    Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateFile);
    StringWriter writer = new StringWriter();
    template.process(dataModel, writer);
    return writer.toString();
  }

}
