package bbc539ff.saltu.post.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
//  @Bean
//  public JedisConnectionFactory jedisConnectionFactory(){
//    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//    return jedisConnectionFactory;
//  }
//
//  @Bean
//  public RedisTemplate redisTemplate(){
//    RedisTemplate redisTemplate = new RedisTemplate();
//    redisTemplate.setConnectionFactory(jedisConnectionFactory());
//    return redisTemplate;
//  }
}
