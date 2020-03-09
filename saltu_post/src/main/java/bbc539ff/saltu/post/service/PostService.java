package bbc539ff.saltu.post.service;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.utils.SnowFlake;
import bbc539ff.saltu.post.client.MemberClient;
import bbc539ff.saltu.post.dao.PostDao;
import bbc539ff.saltu.post.pojo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
  @Autowired PostDao postDao;
  @Autowired MemberClient memberClient;
  @Autowired PostRedisService postRedisService;

  @Autowired SnowFlake snowFlake;

  public Post addPost(Post post) {
    post.setPostId(Long.toString(snowFlake.nextId()));
    post.setPostDate(new Date());
    post.setPostOriginal("");
    post.setPostRepostId("");
    post.setPostRepostNumber(0);
    post.setPostLikeNumber(0);
    post.setPostCommentNumber(0);
    post.setPostState(1);
    post.setPostPic1("");
    post.setPostPic2("");
    post.setPostPic3("");
    post.setPostPic4("");
    post.setPostPic5("");
    post.setPostPic6("");
    post.setPostPic7("");
    post.setPostPic8("");
    post.setPostPic9("");

    postRedisService.addPostToRedis(post);
    return postDao.save(post);
  }

  public Post updatePost(Post post) {
    return postDao.save(post);
  }

  public Post getPostByPostId(String postId) {
    return postDao.findById(postId).get();
  }

  public List<Post> getPostByMemberId(String memberId, String token) {
    Result result = memberClient.findByMemberId(memberId, token);
    if (result.getCode() == 1) {
      return postDao.findAllByMemberId(memberId);
    } else {
      return null;
    }
  }

  public void disablePost(String postId, Integer postState) {
    postDao.updateState(postId, postState);
  }

  public List<Map<String, Object>> getTimelineFromRedis(String memberId) {
    return postRedisService.getTimelineFromRedis(memberId);
  }

  public List<Map<String, Object>> getProfileFromRedis(String memberId) {
    List<Map<String, Object>> list = postRedisService.getProfileFromRedis(memberId);
    return list;
  }
}
