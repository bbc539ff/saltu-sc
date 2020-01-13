package bbc539ff.saltu.user.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.exception.ResultCode;
import bbc539ff.saltu.user.pojo.Follow;
import bbc539ff.saltu.user.service.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/follow")
public class FollowController {
  @Autowired FollowService followService;

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
  public Result unFollowOne(@Valid @RequestBody Follow follow, Errors errors) {
    if (!errors.hasErrors()) {
      Boolean res = followService.unFollowOne(follow);
      if (res) {
        return Result.success();
      } else {
        return Result.failure(ResultCode.PARAM_IS_INVALID);
      }

    } else {
      List<String> errorMessages = new ArrayList<>();
      Iterator<FieldError> iterator = errors.getFieldErrors().iterator();
      while (iterator.hasNext()) {
        errorMessages.add(iterator.next().getDefaultMessage());
      }
      return Result.failure(ResultCode.PARAM_IS_BLANK, errorMessages);
    }
  }
}
