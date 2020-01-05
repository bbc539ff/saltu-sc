package bbc539ff.saltu.post.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.post.client.MemberClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class MemberController {
  @Autowired MemberClient memberClient;

  @GetMapping("/member/{memberId}")
  public Result findByMemberId(@PathVariable String memberId, @RequestHeader("token") String token) {
    log.info(token);
    return memberClient.findByMemberId(memberId, token);
  }
}
