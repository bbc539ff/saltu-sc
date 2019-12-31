package bbc539ff.saltu.user.config;

import bbc539ff.saltu.user.exception.SimpleAccessDeniedHandler;
import bbc539ff.saltu.user.exception.SimpleAuthenticationEntryPoint;
import bbc539ff.saltu.user.filter.JWTAuthenticationFilter;
import bbc539ff.saltu.user.filter.JWTLoginFilter;
import bbc539ff.saltu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        .antMatchers("/login", "/")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        //        .rememberMe()
        //        .and()
        .addFilter(new JWTLoginFilter(authenticationManager(), jwtUtil))
        .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil))
        // Disabled CSRF
        .csrf()
        .disable()
        // Handle exception.
        .exceptionHandling()
        .authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
        .accessDeniedHandler(new SimpleAccessDeniedHandler());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(
        new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder));
  }

  @Bean
  @Override
  public UserDetailsService userDetailsServiceBean() throws Exception {
    return super.userDetailsServiceBean();
  }
}
