package bbc539ff.saltu.post.controller;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.exception.ResultCode;
import bbc539ff.saltu.common.utils.JwtUtil;
import bbc539ff.saltu.post.pojo.Post;
import bbc539ff.saltu.post.service.PostService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
  Result getPostById(@PathVariable String postId) {
    Map<String, Object> map = postService.getPostByPostId(postId);
    return Result.success(map);
  }

  @GetMapping("")
  Result getPost() {
    String token = request.getHeader("token");
    if (token == null || Objects.equals(token, ""))
      return Result.failure(ResultCode.PERMISSION_NO_ACCESS);
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
  Result getHomeTimeline(@PathVariable String memberId) {
    List<Map<String, Object>> list = postService.getTimelineFromRedis(memberId);
    System.out.println(list);
    return Result.success(list);
  }

  @GetMapping(path = "/profile/{memberId}")
  Result getProfileTimeline(@PathVariable String memberId) {
    List<Map<String, Object>> list = postService.getProfileFromRedis(memberId);
    return Result.success(list);
  }

  @PostMapping(path = "/upload")
  public Result uploadPicture(
      @RequestParam("file") MultipartFile file,
      @RequestParam("postId") String postId,
      @RequestParam("picNum") String picNum,
      RedirectAttributes redirectAttributes) {
    System.out.println(postId);
    boolean res = postService.uploadPicture(file, postId, picNum);
    if (res) return Result.success();
    else return Result.failure(ResultCode.DATA_IS_WRONG);
  }

  @GetMapping(path = "/hashtag")
  public Result getTop10HashTag() {
    return Result.success(postService.getTop10HashTag());
  }
}
