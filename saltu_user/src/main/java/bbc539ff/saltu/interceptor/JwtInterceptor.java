package bbc539ff.saltu.interceptor;

import bbc539ff.saltu.controller.MemberController;
import bbc539ff.saltu.exception.Result;
import bbc539ff.saltu.exception.ResultCode;
import bbc539ff.saltu.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
  @Autowired JwtUtil jwtUtil;

  private final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // Get jwt from header
    String header = request.getHeader("Authorization");
    if (header != null && !"".equals(header)) {
      try {
        Claims claims = jwtUtil.parseJwt(header);
        logger.info(claims.getId());
        logger.info(claims.getExpiration().toString());
        request.setAttribute("user_claims", header);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return true;
  }
}
