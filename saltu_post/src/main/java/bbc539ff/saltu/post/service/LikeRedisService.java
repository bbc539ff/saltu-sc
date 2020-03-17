package bbc539ff.saltu.post.service;

import bbc539ff.saltu.post.dao.LikePostDao;
import bbc539ff.saltu.post.pojo.LikePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class LikeRedisService {
  @Autowired StringRedisTemplate redisTemplate;
  @Autowired LikePostDao likePostDao;
  @Autowired PostRedisService postRedisService;

  public void likeOnePost(StringRedisConnection stringRedisConnection, LikePost likePost) {
    if (redisTemplate.opsForZSet().score("ILike:" + likePost.getMemberId(), likePost.getPostId())
        != null) return;
    stringRedisConnection.hIncrBy("post:" + likePost.getPostId(), "postLikeNumber", 1);
    stringRedisConnection.zAdd(
        "ILike:" + likePost.getMemberId(), System.currentTimeMillis(), likePost.getPostId());
    stringRedisConnection.zAdd(
        "PostLiked:" + likePost.getPostId(), System.currentTimeMillis(), likePost.getMemberId());
  }

  public void unLikeOnePost(StringRedisConnection stringRedisConnection, LikePost likePost) {
    if (redisTemplate.opsForZSet().score("ILike:" + likePost.getMemberId(), likePost.getPostId())
        == null) return;
    stringRedisConnection.hIncrBy("post:" + likePost.getPostId(), "postLikeNumber", -1);
    stringRedisConnection.zRem("ILike:" + likePost.getMemberId(), likePost.getPostId());
    stringRedisConnection.zRem("PostLiked:" + likePost.getPostId(), likePost.getMemberId());
  }

  public void likePost(LikePost likePost) {
    redisTemplate.executePipelined(
        (RedisCallback<Object>)
            connection -> {
              StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
              likeOnePost(stringRedisConnection, likePost);
              return null;
            });
  }

  public void unLikePost(LikePost likePost) {
    redisTemplate.executePipelined(
        (RedisCallback<Object>)
            connection -> {
              StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
              unLikeOnePost(stringRedisConnection, likePost);
              return null;
            });
  }

  public List<Map<String, Object>> getILikePost(String memberId) {
    Set<String> set =
        redisTemplate
            .opsForZSet()
            .reverseRangeByScore("ILike:" + memberId, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    return postRedisService.getPostFromRedis(set);
  }

  public List<Object> getPostLikedMember(String postId) {
    Set<String> set =
        redisTemplate
            .opsForZSet()
            .reverseRangeByScore(
                "PostLiked:" + postId, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    return postRedisService.getMemberFromRedis(set);
  }

  public void loadingLikeDataFromDatabase() {
    List<LikePost> likePostList = likePostDao.findAll();

    for (LikePost likePost : likePostList) {
      likePost(likePost);
    }
  }

  public boolean isLiked(String postId, String memberId) {
    Long index =redisTemplate.opsForZSet().rank("ILike:" + memberId, postId);
    if(index != null) {
      return true;
    } else {
      return false;
    }
  }
}
