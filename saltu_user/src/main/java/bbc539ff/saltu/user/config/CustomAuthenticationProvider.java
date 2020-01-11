package bbc539ff.saltu.user.config;

import bbc539ff.saltu.user.pojo.Member;
import bbc539ff.saltu.user.service.MemberService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.HashMap;
import java.util.Map;

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
    Member memberDB = memberService.login(member);
    logger.info(memberDB.toString());
    if(memberDB == null) throw new RuntimeException("验证失败");
    Map<String, String> map = new HashMap<>();
    map.put("memberId", memberDB.getMemberId());
    map.put("memberName", memberDB.getMemberName());
    String mapJSON = JSON.toJSONString(map);
    Authentication auth = new UsernamePasswordAuthenticationToken(mapJSON, password);

    return auth;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
