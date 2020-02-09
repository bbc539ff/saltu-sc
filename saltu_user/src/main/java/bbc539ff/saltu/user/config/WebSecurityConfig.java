package bbc539ff.saltu.user.config;

import bbc539ff.saltu.common.utils.JwtUtil;
import bbc539ff.saltu.user.exception.SimpleAccessDeniedHandler;
import bbc539ff.saltu.user.exception.SimpleAuthenticationEntryPoint;
import bbc539ff.saltu.user.filter.JWTAuthenticationFilter;
import bbc539ff.saltu.user.filter.JWTLoginFilter;
import bbc539ff.saltu.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired private UserDetailsService userDetailsService;

  @Autowired JwtUtil jwtUtil;

  @Autowired MemberService memberService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/register")
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
        .cors(Customizer.withDefaults())
        // Handle exception.
        .exceptionHandling()
        .authenticationEntryPoint(new SimpleAuthenticationEntryPoint())
        .accessDeniedHandler(new SimpleAccessDeniedHandler());
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(new CustomAuthenticationProvider(memberService));
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

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.addExposedHeader("token");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
