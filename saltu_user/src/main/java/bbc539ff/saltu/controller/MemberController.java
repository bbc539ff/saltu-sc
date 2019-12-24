package bbc539ff.saltu.controller;

import bbc539ff.saltu.exception.Result;
import bbc539ff.saltu.pojo.Member;
import bbc539ff.saltu.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class MemberController {

  @Autowired
  MemberService memberService;

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
  public Result deleteMember(@PathVariable String memberId) {
    logger.info("memberId: " + memberId);
    memberService.deleteById(memberId);
    return Result.success();
  }

  @RequestMapping(path = "/{memberId}", method = RequestMethod.PUT)
  public Result updateMember(@PathVariable String memberId, @RequestBody Member member) {
    member.setMemberId(memberId);
    logger.info(member.toString());
    memberService.updateById(member);
    return Result.success();
  }
}
