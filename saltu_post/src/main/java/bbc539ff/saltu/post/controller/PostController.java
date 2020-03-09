package bbc539ff.saltu.post.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.exception.ResultCode;
import bbc539ff.saltu.common.utils.JwtUtil;
import bbc539ff.saltu.post.pojo.Post;
import bbc539ff.saltu.post.service.PostService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/post")
public class PostController {
  @Autowired PostService postService;
  @Autowired HttpServletRequest request;
  @Autowired JwtUtil jwtUtil;

  @PostMapping("")
  Result addPost(@RequestBody Post post) {
    post = postService.addPost(post);
    if (post != null) {
      return Result.success(post);
    } else {
      return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }
  }

  @GetMapping("/{postId}")
  Result getPostById(String postId) {
    Post post = postService.getPostByPostId(postId);
    return Result.success(post);
  }

  @GetMapping("")
  Result getPost() {
    String token = request.getHeader("token");
    if(token == null || Objects.equals(token, "")) return Result.failure(ResultCode.PERMISSION_NO_ACCESS);
    Claims claims = jwtUtil.parseJwt(token);
    String memberId = claims.getId();
    List<Post> postList = postService.getPostByMemberId(memberId, request.getHeader("token"));
    return Result.success(postList);
  }

  @PutMapping("")
  Result updatePost(@RequestBody Post post) {
    post = postService.updatePost(post);
    return Result.success(post);
  }

  @DeleteMapping(path = "/{postId}")
  Result disablePost(@PathVariable String postId) {
    postService.disablePost(postId, 0);
    return Result.success();
  }

  @GetMapping(path = "/timeline/{memberId}")
  Result getHomeTimeline(@PathVariable String memberId){
    List<Map<String, Object>> list = postService.getTimelineFromRedis(memberId);
    System.out.println(list);
    return Result.success(list);
  }

  @GetMapping(path = "/profile/{memberId}")
  Result getProfileTimeline(@PathVariable String memberId){
    List<Map<String, Object>> list = postService.getProfileFromRedis(memberId);
    return Result.success(list);
  }
}
