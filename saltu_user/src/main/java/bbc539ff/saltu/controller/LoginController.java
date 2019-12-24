package bbc539ff.saltu.controller;

import bbc539ff.saltu.exception.Result;
import bbc539ff.saltu.exception.ResultCode;
import bbc539ff.saltu.pojo.Member;
import bbc539ff.saltu.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {
  private final Logger logger = LoggerFactory.getLogger(LoginController.class);

  @Autowired
  MemberService memberService;

  @RequestMapping(path = "login", method = RequestMethod.POST)
  public Result login(@RequestBody Map<String, String> loginForm) {
    logger.info(loginForm.toString());
    Member member = memberService.login(loginForm);
    if(member != null) {
        return Result.success(member);
    } else {
        return Result.failure(ResultCode.DATA_IS_WRONG);
    }
  }
}
