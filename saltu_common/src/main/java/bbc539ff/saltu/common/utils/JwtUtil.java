package bbc539ff.saltu.common.utils;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

@ConfigurationProperties("jwt.config")
public class JwtUtil {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
  private String secretKey;
  private Long ttl;
  private String prefix;

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public Long getTtl() {
    return ttl;
  }

  public void setTtl(Long ttl) {
    this.ttl = ttl;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  /**
   * create jwt token
   *
   * @param subject memberName
   * @param role user
   * @return jwt token
   */
  public String createJwt(String id, String subject, String role) {
    long nowMills = System.currentTimeMillis();
    Date now = new Date(nowMills);
    String token =
        Jwts.builder()
            .setId(id)
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(new Date(nowMills + ttl))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .claim("role", role)
            .compact();

    logger.info(token);
    return prefix + " " + token;
  }

  /**
   * parse jwt token
   *
   * @param token jwt token
   */
  public Claims parseJwt(String token) {
    Claims claims =
        Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token.replace(prefix + " ", ""))
            .getBody();
    return claims;
  }
}
