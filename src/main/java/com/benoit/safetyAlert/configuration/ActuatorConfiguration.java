package com.benoit.safetyAlert.configuration;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Configuration actuatorµ.
 * Contains @Bean for /actuator/httptrace to work on spring boot 2.2.x
 */
@Configuration
public class ActuatorConfiguration {

  @Bean
  public HttpTraceRepository htttpTraceRepository() {
    return new InMemoryHttpTraceRepository();
  }
}
