package bbc539ff.saltu.user.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

  private UserDetailsService userDetailsService;

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

  public CustomAuthenticationProvider(
      UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userDetailsService = userDetailsService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // 获取认证的用户名 & 密码
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    logger.info(username + ", " + password);
    //        //通过用户名从数据库中查询该用户
    //        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    //
    //
    //        //判断密码(这里是md5加密方式)是否正确
    //        String dbPassword = userDetails.getPassword();
    //
    //        if (!bCryptPasswordEncoder.matches(username, dbPassword)) {
    //            throw new RuntimeException("密码错误");
    //        }

    Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

    return auth;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
