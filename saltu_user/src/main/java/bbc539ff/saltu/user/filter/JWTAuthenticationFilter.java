package bbc539ff.saltu.user.filter;

import bbc539ff.saltu.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
  private JwtUtil jwtUtil;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    super(authenticationManager);
    this.jwtUtil = jwtUtil;
  }


  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    String token = request.getHeader("token");
    logger.info(token);
    // 判断是否有token
    if (token == null || !token.startsWith(jwtUtil.getPrefix() + " ")) {
      chain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    // 放行
    chain.doFilter(request, response);
  }

  /** 解析token中的信息,并判断是否过期 */
  private UsernamePasswordAuthenticationToken getAuthentication(String token) {
    Claims claims = null;
    try{
      claims = jwtUtil.parseJwt(token);
    }catch (ExpiredJwtException e){
      e.printStackTrace();
      return null;
    }
    // 得到用户名
    String username = claims.getSubject();

    // 得到过期时间
    Date expiration = claims.getExpiration();

    // 判断是否过期
    Date now = new Date();

    if (now.getTime() > expiration.getTime()) {

      throw new RuntimeException("该账号已过期,请重新登陆");
    }

    if (username != null) {
      return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
    }
    return null;
  }
}
