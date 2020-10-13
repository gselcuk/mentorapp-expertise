package com.obss.mentor.expertise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main class of expertise microservice.
 * 
 * @author Goktug Selcuk
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ExpertiseApplication {

  public static void main(String[] args) {
    SpringApplication.run(ExpertiseApplication.class, args);
  }

}
