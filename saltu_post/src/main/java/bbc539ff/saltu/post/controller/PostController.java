package bbc539ff.saltu.post.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.utils.JwtUtil;
import bbc539ff.saltu.post.pojo.Post;
import bbc539ff.saltu.post.service.PostService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
  @Autowired PostService postService;
  @Autowired HttpServletRequest request;
  @Autowired JwtUtil jwtUtil;

  @PostMapping("")
  Result addPost(@RequestBody Post post) {
    post = postService.addPost(post);
    return Result.success(post);
  }

  @GetMapping("/{postId}")
  Result getPostById(String postId) {
    Post post = postService.getPostByPostId(postId);
    return Result.success(post);
  }

  @GetMapping("")
  Result getPost() {
    String token = request.getHeader("token");
    Claims claims = jwtUtil.parseJwt(token);
    String memberId = claims.getId();
    List<Post> postList = postService.getPostByMemberId(memberId);
    return Result.success(postList);
  }

  @PutMapping("")
  Result updatePost(@RequestBody Post post) {
    post = postService.updatePost(post);
    return Result.success(post);
  }

  @DeleteMapping(path = "/{postId}")
  Result disablePost(String postId) {
    postService.disablePost(postId, 0);
    return Result.success();
  }
}
