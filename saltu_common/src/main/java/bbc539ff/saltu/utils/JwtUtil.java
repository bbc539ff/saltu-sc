package bbc539ff.saltu.utils;

import io.jsonwebtoken.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

@ConfigurationProperties("jwt.config")
public class JwtUtil {

  private String key;
  private Long ttl;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Long getTtl() {
    return ttl;
  }

  public void setTtl(Long ttl) {
    this.ttl = ttl;
  }

  /**
   * create jwt token
   * @param id memberId
   * @param subject memberName
   * @param role user
   * @return jwt token
   */
  public String createJwt(String id, String subject, String role) {
    long nowMills = System.currentTimeMillis();
    Date now = new Date(nowMills);
    JwtBuilder jwtBuilder =
        Jwts.builder()
            .setId(id)
            .setSubject(subject)
            .setIssuedAt(now)
            .signWith(SignatureAlgorithm.HS256, key)
            .claim("role", role);
    if (ttl > 0) {
      jwtBuilder.setExpiration(new Date(nowMills + ttl));
    }
    return jwtBuilder.compact();
  }

  /**
   * parse jwt token
   * @param token jwt token
   */
  public Claims parseJwt(String token) {
    Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    return claims;
  }
}
