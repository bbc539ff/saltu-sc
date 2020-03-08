package bbc539ff.saltu.post.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.exception.ResultCode;
import bbc539ff.saltu.post.pojo.LikePost;
import bbc539ff.saltu.post.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/like")
public class LikeController {
  @Autowired LikeService likeService;

  @PostMapping("")
  Result likeOne(@RequestBody LikePost likePost) {
    String postId = likePost.getPostId();
    String memberId = likePost.getMemberId();
    likeService.likeOne(postId, memberId);
    return Result.success();
  }

  @DeleteMapping("")
  Result unLikeOne(@RequestBody LikePost likePost) {
    String postId = likePost.getPostId();
    String memberId = likePost.getMemberId();
    likeService.unLikeOne(postId, memberId);
    return Result.success();
  }

  @GetMapping("/i-like/{memberId}")
  Result getILikePost(String memberId) {
    List<Map<String, Object>> list = likeService.getILikePost(memberId);
    return Result.success(list);
  }

  @GetMapping("/post-liked/{postId}")
  Result getPostLikedMember(String postId) {
    List<Object> list = likeService.getPostLikedMember(postId);
    return Result.success(list);
  }
}
