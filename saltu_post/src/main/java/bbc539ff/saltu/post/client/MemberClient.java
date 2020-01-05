package bbc539ff.saltu.post.client;

import bbc539ff.saltu.common.exception.Result;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "saltu-user", fallback = MemberClientHystrix.class)
public interface MemberClient {
  @GetMapping("/member/{memberId}")
  public Result findByMemberId(@PathVariable String memberId, @RequestHeader("token") String token);
}
