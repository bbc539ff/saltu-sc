package bbc539ff.saltu.filter;

import bbc539ff.saltu.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;
  private JwtUtil jwtUtil;

  public JWTLoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    // Get username and password.
    ObjectMapper mapper = new ObjectMapper();
    String username = "";
    String password = "";
    try {
      Map<String, String> formData = mapper.readValue(request.getInputStream(), Map.class);
      username = formData.get("memberName");
      password = formData.get("memberPassword");
    } catch (Exception e) {
      e.printStackTrace();
    }
    logger.info(username + ", " + password);

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(username, password);
    return authenticationToken;
  }

  /**
   * Called after authentication, generate a token and return.
   *
   * @param request
   * @param response
   * @param chain
   * @param authResult
   */
  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult) {

    String token = jwtUtil.createJwt(authResult.getName(), "MEMBER");
    response.addHeader("token", token);
  }
}
