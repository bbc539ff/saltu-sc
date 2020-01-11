package bbc539ff.saltu.post.config;

import bbc539ff.saltu.common.utils.JwtUtil;
import bbc539ff.saltu.post.exception.SimpleAccessDeniedHandler;
import bbc539ff.saltu.post.exception.SimpleAuthenticationEntryPoint;
import bbc539ff.saltu.post.filter.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired private UserDetailsService userDetailsService;

  @Autowired JwtUtil jwtUtil;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers()
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        //        .rememberMe()
        //        .and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil))
        // Disabled CSRF
        .csrf()
        .disable()
        // Handle exception.
        .exceptionHandling()
        .authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
        .accessDeniedHandler(new SimpleAccessDeniedHandler());
  }

  @Bean
  @Override
  public UserDetailsService userDetailsServiceBean() throws Exception {
    return super.userDetailsServiceBean();
  }


  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }
}
