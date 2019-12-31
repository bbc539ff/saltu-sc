package bbc539ff.saltu.user.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.exception.ResultCode;
import bbc539ff.saltu.user.pojo.Member;
import bbc539ff.saltu.user.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/user")
public class MemberController {
  @Autowired MemberService memberService;
  @Autowired HttpServletRequest request;

  private final Logger logger = LoggerFactory.getLogger(MemberController.class);

  @RequestMapping("")
  public List<Member> findAll() {
    return memberService.findAll();
  }

  @RequestMapping(path = "", method = RequestMethod.POST)
  public Result addMember(@RequestBody Member member) {
    memberService.saveOne(member);
    return Result.success();
  }

  @RequestMapping(path = "/{memberId}", method = RequestMethod.DELETE)
  public Result disableMember(@PathVariable String memberId) {
    logger.info("Disable memberId: " + memberId);
    memberService.disableById(memberId);
    return Result.success();
  }

  @RequestMapping(path = "/{memberId}", method = RequestMethod.PUT)
  public Result updateMember(@PathVariable String memberId, @RequestBody Member member) {
    member.setMemberId(memberId);
    logger.info(member.toString());
    String token = (String) request.getAttribute("user_claims");
    if (token == null || "".equals(token)) return Result.failure(ResultCode.USER_NOT_LOGGED_IN);
    memberService.updateById(member);
    return Result.success(member);
  }
}
