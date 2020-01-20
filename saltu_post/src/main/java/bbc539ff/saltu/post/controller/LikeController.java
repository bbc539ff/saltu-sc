package bbc539ff.saltu.post.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.exception.ResultCode;
import bbc539ff.saltu.post.pojo.LikePost;
import bbc539ff.saltu.post.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {
  @Autowired LikeService likeService;

  @PostMapping("")
  Result likeOne(@RequestBody LikePost likePost) {
    String postId = likePost.getPostId();
    String memberId = likePost.getMemberId();
    int status = likeService.redisLikePost(postId, memberId);
    if (status == 0) return Result.success(likeService.getMemberIdByPostId(likePost.getPostId()));
    else return Result.failure(ResultCode.DATA_ALREADY_EXISTED);
  }

  @DeleteMapping("")
  Result unLikeOne(@RequestBody LikePost likePost) {
    String postId = likePost.getPostId();
    String memberId = likePost.getMemberId();
    int status = likeService.redisUnLikePost(postId, memberId);
    if (status == 0) return Result.success(likeService.getMemberIdByPostId(likePost.getPostId()));
    else return Result.failure(ResultCode.RESULE_DATA_NONE);
  }
}
