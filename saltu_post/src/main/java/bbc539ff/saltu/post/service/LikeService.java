package bbc539ff.saltu.post.service;

import bbc539ff.saltu.post.dao.LikePostDao;
import bbc539ff.saltu.post.pojo.LikePost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class LikeService {
  @Autowired private RedisTemplate<String, String> redisTemplate;
  @Autowired LikePostDao likePostDao;

  public LikePost likeOne(String postId, String memberId) {
    LikePost likePostDB = likePostDao.findById(new LikePost.PK(postId, memberId)).orElse(null);
    log.info("likePostDB" + likePostDB);
    if (likePostDB == null) return likePostDao.save(new LikePost(postId, memberId));
    else return null;
  }

  /**
   * MEMBER_POST_MAP: A HashMap, use memberId as key, the value is a Set contains postId.
   * POST_LIKE_COUNT_KEY: The number of likes got of this post; MEMBER_LIKE_COUNT_KEY: The number of
   * member's likes.
   *
   * @param postId
   * @param memberId
   * @return -1: Already liked; 0: succeed.
   */
  public Integer redisLikePost(String postId, String memberId) {
    if(redisTemplate.opsForSet().isMember("postId:"+postId, memberId)) return -1;
    else {
      redisTemplate.opsForSet().add("postId:"+postId, memberId);
      redisTemplate.opsForSet().add("memberId:"+memberId, postId);
      redisTemplate.opsForHash().increment("POST_LIKE_COUNT", postId, 1);
      redisTemplate.opsForHash().increment("MEMBER_LIKE_OTHER_COUNT", memberId, 1);
      return 0;
    }
  }

  public Integer redisUnLikePost(String postId, String memberId) {
    if(!redisTemplate.opsForSet().isMember("postId:"+postId, memberId)) return -1;
    else {
      redisTemplate.opsForSet().remove("postId:"+postId, memberId);
      redisTemplate.opsForSet().remove("memberId:"+memberId, postId);
      redisTemplate.opsForHash().increment("POST_LIKE_COUNT", postId, -1);
      redisTemplate.opsForHash().increment("MEMBER_LIKE_OTHER_COUNT", memberId, -1);
      return 0;
    }
  }

  public Set<String> getPostIdByMemberId(String memberId) {
    return redisTemplate.opsForSet().members("memberId:"+memberId);
  }

  public Set<String> getMemberIdByPostId(String postId) {
    return redisTemplate.opsForSet().members("postId:"+postId);
  }

  public String getPostLikeCount(String postId) {
    return redisTemplate.opsForValue().get("POST_LIKE_COUNT");
  }

  public String getMemberLikeCount(String memberId) {
    return redisTemplate.opsForValue().get("MEMBER_LIKE_OTHER_COUNT");
  }
}
