package bbc539ff.saltu.user.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.exception.ResultCode;
import bbc539ff.saltu.common.utils.JwtUtil;
import bbc539ff.saltu.user.pojo.Member;
import bbc539ff.saltu.user.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@CrossOrigin
public class LoginController {
  private final Logger logger = LoggerFactory.getLogger(LoginController.class);

  @Autowired MemberService memberService;
  @Autowired JwtUtil jwtUtil;

  @RequestMapping(path = "/login", method = RequestMethod.POST)
  public Result login(@RequestBody @Valid Member member) {
    logger.info(member.toString());
    return Result.failure(ResultCode.DATA_IS_WRONG, "LoginController");
  }

  @PostMapping("/register")
  public Result register(@RequestBody @Valid Member member, Errors errors) {
    if (!errors.hasErrors()) {
      member = memberService.saveOne(member);
      return Result.success(member);
    } else {
      List<String> errorMessages = new ArrayList<>();
      Iterator<FieldError> iterator = errors.getFieldErrors().iterator();
      while (iterator.hasNext()) {
        errorMessages.add(iterator.next().getDefaultMessage());
      }
      return Result.failure(ResultCode.RESULE_DATA_NONE, errorMessages);
    }
  }
}
