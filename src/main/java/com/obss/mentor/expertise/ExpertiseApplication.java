package com.obss.mentor.expertise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Main class of expertise microservice.
 * 
 * @author Goktug Selcuk
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveElasticsearchRepositories
public class ExpertiseApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExpertiseApplication.class, args);
  }

  /**
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
