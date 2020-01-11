package bbc539ff.saltu.zuul;

import bbc539ff.saltu.common.utils.JwtUtil;
import bbc539ff.saltu.zuul.config.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ZuulApplication {
  public static void main(String[] args) {
    SpringApplication.run(ZuulApplication.class, args);
  }

//  @Bean
//  public AccessFilter accessFilter() {
//    return new AccessFilter();
//  }

  @Bean
  public JwtUtil jwtUtil(){
    return new JwtUtil();
  }

  @Bean
  BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
