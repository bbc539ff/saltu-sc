package bbc539ff.saltu.user.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.user.pojo.Follow;
import bbc539ff.saltu.user.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/follow")
public class FollowController {
  @Autowired
  FollowService followService;

  @GetMapping("{memberId}")
  public List<Follow> findAllByMemberId(@PathVariable String memberId){
    return followService.findAllFollowerByMemberId(memberId);
  }

  @PostMapping("")
  public Result followOne(@RequestBody Map<String, String> paramMap){
    String memberId = paramMap.get("memberId");
    String followingId = paramMap.get("followingId");
    Follow follow = new Follow(memberId ,followingId);
    follow = followService.followOne(follow);
    return Result.success(follow);
  }

  @DeleteMapping("")
  public Result unFollowOne(@RequestBody Map<String, String> paramMap){
    String memberId = paramMap.get("memberId");
    String followingId = paramMap.get("followingId");
    Follow follow = new Follow(memberId ,followingId);
    followService.unFollowOne(follow);
    return Result.success();
  }
}
