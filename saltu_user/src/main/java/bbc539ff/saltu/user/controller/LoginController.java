package bbc539ff.saltu.user.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.exception.ResultCode;
import bbc539ff.saltu.user.pojo.Member;
import bbc539ff.saltu.user.service.MemberService;
import bbc539ff.saltu.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {
  private final Logger logger = LoggerFactory.getLogger(LoginController.class);

  @Autowired MemberService memberService;
  @Autowired JwtUtil jwtUtil;

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public Result login(@RequestBody Member member) {
    logger.info(member.toString());
    return Result.failure(ResultCode.DATA_IS_WRONG);
  }

  @PostMapping("/register")
  public Result register(@RequestBody Member member){
    member = memberService.saveOne(member);
    return Result.success(member);
  }
}
