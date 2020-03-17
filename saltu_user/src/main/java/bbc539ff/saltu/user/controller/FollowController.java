package bbc539ff.saltu.user.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.exception.ResultCode;
import bbc539ff.saltu.user.pojo.Follow;
import bbc539ff.saltu.user.service.FollowService;
import bbc539ff.saltu.user.service.FollowerRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/follow")
public class FollowController {
  @Autowired FollowService followService;
  @Autowired FollowerRedisService followerRedisService;

  @GetMapping("{memberId}")
  public List<Follow> findAllByMemberId(@PathVariable String memberId) {
    return followService.findAllFollowerByMemberId(memberId);
  }

  @PostMapping("")
  public Result followOne(@Valid @RequestBody Follow follow, Errors errors) {
    log.info("follow: " + follow);

    if (!errors.hasErrors()) {
      follow = followService.followOne(follow);
      if (follow != null) return Result.success(follow);
      else return Result.failure(ResultCode.PARAM_IS_INVALID);
    } else {
      List<String> errorMessages = new ArrayList<>();
      Iterator<FieldError> iterator = errors.getFieldErrors().iterator();
      while (iterator.hasNext()) {
        errorMessages.add(iterator.next().getDefaultMessage());
      }
      return Result.failure(ResultCode.PARAM_IS_BLANK, errorMessages);
    }
  }

  @DeleteMapping("")
  public Result unFollowOne(@RequestParam String memberId, @RequestParam String followingId) {
    Follow follow = new Follow(memberId, followingId);
    Boolean res = followService.unFollowOne(follow);
    if (res) {
      return Result.success();
    } else {
      return Result.failure(ResultCode.PARAM_IS_INVALID);
    }
  }

  @GetMapping("")
  public Result isFollowed(@RequestParam String memberId, @RequestParam String followingId) {
    boolean res = followerRedisService.isFollowing(memberId, followingId);
    Map<String, Integer> resMap = new HashMap<>();

    if (res) resMap.put("isFollowed", 1);
    else resMap.put("isFollowed", 0);
    return Result.success(resMap);
  }

  @GetMapping("/follower/{memberId}")
  public Result getFollowerList(@PathVariable String memberId) {
    return Result.success(followService.getFollowerList(memberId));
  }

  @GetMapping("/following/{memberId}")
  public Result getFollowingList(@PathVariable String memberId) {
    return Result.success(followService.getFollowingList(memberId));
  }
}
