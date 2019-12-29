package bbc539ff.saltu.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  public JWTLoginFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
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
    logger.info(username+", "+password);

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(username, password);
    return authenticationToken;
  }

  /**
   * Called after authentication, generate a token and return.
   * @param request
   * @param response
   * @param chain
   * @param authResult
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain, Authentication authResult) {

    String token = Jwts.builder()
            .setSubject(authResult.getName())
            .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 2 * 1000))
            .signWith(SignatureAlgorithm.HS512, "saltu")
            .compact();

    response.addHeader("token", "Bearer " + token);

  }
}
