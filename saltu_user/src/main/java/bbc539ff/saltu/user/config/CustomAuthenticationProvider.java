package bbc539ff.saltu.user.config;

import bbc539ff.saltu.user.pojo.Member;
import bbc539ff.saltu.user.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationProvider implements AuthenticationProvider {

  private MemberService memberService;

  CustomAuthenticationProvider(MemberService memberService){
    this.memberService = memberService;
  }

  private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // 获取认证的用户名 & 密码
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    Member member = new Member(username, password);
    logger.info(member.toString());
    member = memberService.login(member);
    logger.info(member.toString());
    if(member == null) throw new RuntimeException("验证失败");
    Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

    return auth;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
