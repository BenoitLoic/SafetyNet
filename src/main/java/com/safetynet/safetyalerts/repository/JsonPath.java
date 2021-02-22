package com.safetynet.safetyalerts.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class allow to externalize our json, in order to easily modify its path in the properties.
 */
@Component
public class JsonPath {

  @Value("${path.json}")
  private String dataJson;

  public String getDataJson() {
    return dataJson;
  }
}
