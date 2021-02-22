package com.benoit.safetyAlert.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class JsonPath {

  @Value("${path.json}")
  private String dataJson;

  public String getDataJson() {
    return dataJson;
  }
}
