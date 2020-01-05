package bbc539ff.saltu.user;

import bbc539ff.saltu.common.utils.JwtUtil;
import bbc539ff.saltu.common.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories
@EnableDiscoveryClient
public class UserApplication {
  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }

  @Bean
  BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  @Value("${snowflake.dataCenterId}")
  Long dataCenterId;

  @Value("${snowflake.machineId}")
  Long machineId;

  @Bean
  SnowFlake snowFlake() {
    return new SnowFlake(dataCenterId, machineId);
  }

  @Bean
  JwtUtil jwtUtil() {
    return new JwtUtil();
  }

  @Bean
  @LoadBalanced
  RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
