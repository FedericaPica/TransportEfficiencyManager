package com.java.tem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan
public class TemApplication {

  public static void main(String[] args) {
    SpringApplication.run(TemApplication.class, args);
  }

}
