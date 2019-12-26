package bbc539ff.saltu.controller;

import bbc539ff.saltu.exception.Result;
import bbc539ff.saltu.exception.ResultCode;
import bbc539ff.saltu.pojo.Member;
import bbc539ff.saltu.service.MemberService;
import bbc539ff.saltu.utils.JwtUtil;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class LoginController {
  private final Logger logger = LoggerFactory.getLogger(LoginController.class);

  @Autowired MemberService memberService;
  @Autowired JwtUtil jwtUtil;

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public Result login(@RequestBody Member member) {
    logger.info(member.toString());
    // Verify memberName and memberPassword
    Member memberFromDB = memberService.login(member);
    if (memberFromDB != null) {
      // Create jwt
      String token =
          jwtUtil.createJwt(memberFromDB.getMemberId(), memberFromDB.getMemberName(), "member");
      Map<String, Object> map = new HashMap<>();
      map.put("token", token);
      map.put("member", memberFromDB);
      return Result.success(map);
    } else {
      return Result.failure(ResultCode.DATA_IS_WRONG);
    }
  }
}
