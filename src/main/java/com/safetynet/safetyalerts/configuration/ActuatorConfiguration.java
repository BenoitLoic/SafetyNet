package com.safetynet.safetyalerts.configuration;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Configuration actuator.
 * Contains @Bean for /actuator/httptrace to work on spring boot 2.2.x
 */
@Configuration
public class ActuatorConfiguration {

  @Bean
  public HttpTraceRepository htttpTraceRepository() {
    return new InMemoryHttpTraceRepository();
  }
}
