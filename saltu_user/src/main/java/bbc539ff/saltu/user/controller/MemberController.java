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
@RequestMapping("/member")
public class MemberController {
  @Autowired MemberService memberService;
  @Autowired HttpServletRequest request;

  private final Logger logger = LoggerFactory.getLogger(MemberController.class);

  /**
   * Get all member(Admin).
   * @return
   */
  @RequestMapping("")
  public Result findAll() {
    List<Member> memberList = memberService.findAll();
    return Result.success(memberList);
  }

  /**
   * Get member detail.
   * @param memberId
   * @return
   */
  @GetMapping("/{memberId}")
  public Result findByMemberId(@PathVariable String memberId) {
    Member member = memberService.findById(memberId);
    return Result.success(member);
  }

  @GetMapping("/c/{memberId}")
  public Result findByMemberIdFromRedis(@PathVariable String memberId) {
    Map<String, Object> map = memberService.findByIdFromRedis(memberId);
    return Result.success(map);
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
