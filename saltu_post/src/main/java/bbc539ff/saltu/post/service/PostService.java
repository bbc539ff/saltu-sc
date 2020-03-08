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
