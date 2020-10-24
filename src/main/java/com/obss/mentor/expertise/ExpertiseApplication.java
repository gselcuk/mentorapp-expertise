package com.obss.mentor.expertise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Main class of expertise microservice.Discovery client and reactive elasctic repository enabled in
 * this class.
 * 
 * @author Goktug Selcuk
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveElasticsearchRepositories
public class ExpertiseApplication {

  /**
   * Main metod.Entry point for Spring Boot application.
   * 
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(ExpertiseApplication.class, args);
  }

  /**
   * Rest template configuration.
   * 
   * @return
   */
  @Bean
  public RestTemplate restTemplate() {
    var factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(3000);
    factory.setReadTimeout(3000);
    return new RestTemplate(factory);
  }
}
