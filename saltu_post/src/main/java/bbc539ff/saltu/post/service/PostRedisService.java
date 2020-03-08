package bbc539ff.saltu.post.service;

import bbc539ff.saltu.post.dao.PostDao;
import bbc539ff.saltu.post.pojo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * structure:
 * post:
 *  java ver: {Map<String, String> post:%postId = new HashMap<String, String>()};
 *  Redis ver: Hash{key: "post:%id", attribute, value)}
 * profile(personal timeline):
 *  java ver: {Set<String> profile:%memberId = new HashSet<String>(postId)};
 *  Redis ver: ZSet{key: "profile:%memberId", postId, timestamp)}
 * home(following timeline):
 *  java ver: {Set<String> home:%memberId = new HashSet<String>(postId)};
 *  Redis ver: ZSet{key: "home:%memberId", postId, timestamp)}
 */

@Service
public class PostRedisService {

  @Autowired PostDao postDao;
  @Autowired StringRedisTemplate redisTemplate;

  public void addOnePost(StringRedisConnection stringRedisConnection, Post post) {
    stringRedisConnection.hSet("post:" + post.getPostId(), "postId", post.getPostId());
    stringRedisConnection.hSet("post:" + post.getPostId(), "memberId", post.getMemberId());
    stringRedisConnection.hSet("post:" + post.getPostId(), "postContent", post.getPostContent());
    stringRedisConnection.hSet(
        "post:" + post.getPostId(), "postDate", Long.toString(post.getPostDate().getTime()));
    stringRedisConnection.hSet("post:" + post.getPostId(), "postOriginal", post.getPostOriginal());
    stringRedisConnection.hSet("post:" + post.getPostId(), "postRepostId", post.getPostRepostId());
    stringRedisConnection.hSet(
        "post:" + post.getPostId(),
        "postRepostNumber",
        Integer.toString(post.getPostRepostNumber()));
    stringRedisConnection.hSet(
        "post:" + post.getPostId(),
        "postCommentNumber",
        Integer.toString(post.getPostCommentNumber()));
    stringRedisConnection.hSet(
        "post:" + post.getPostId(), "postLikeNumber", Integer.toString(post.getPostLikeNumber()));
    stringRedisConnection.hSet(
        "post:" + post.getPostId(), "postState", Integer.toString(post.getPostState()));
    stringRedisConnection.hSet("post:" + post.getPostId(), "postPic1", post.getPostPic1());
    stringRedisConnection.hSet("post:" + post.getPostId(), "postPic2", post.getPostPic2());
    stringRedisConnection.hSet("post:" + post.getPostId(), "postPic3", post.getPostPic3());
    stringRedisConnection.hSet("post:" + post.getPostId(), "postPic4", post.getPostPic4());
    stringRedisConnection.hSet("post:" + post.getPostId(), "postPic5", post.getPostPic5());
    stringRedisConnection.hSet("post:" + post.getPostId(), "postPic6", post.getPostPic6());
    stringRedisConnection.hSet("post:" + post.getPostId(), "postPic7", post.getPostPic7());
    stringRedisConnection.hSet("post:" + post.getPostId(), "postPic8", post.getPostPic8());
    stringRedisConnection.hSet("post:" + post.getPostId(), "postPic9", post.getPostPic9());
  }

  public void addPostToRedis(Post post) {
    redisTemplate.executePipelined(
        (RedisCallback<Object>)
            connection -> {
              StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
              addOnePost(stringRedisConnection, post);
              // Add postId to profile: memberId zSet.
              stringRedisConnection.zAdd(
                  "profile:" + post.getMemberId(), System.currentTimeMillis(), post.getPostId());
              // Add postId to home: followerId zSet.
              Set<String> followerSet =
                  redisTemplate
                      .opsForZSet()
                      .rangeByScore(
                          "follower:" + post.getMemberId(),
                          Double.NEGATIVE_INFINITY,
                          Double.POSITIVE_INFINITY,
                          0,
                          1000);
              System.out.println("follower:" + post.getMemberId() + followerSet);
              if (followerSet != null) {
                for (String followerId : followerSet) {
                  stringRedisConnection.zAdd(
                      "home:" + followerId, System.currentTimeMillis(), post.getPostId());
                }
              }
              return null;
            });
  }

    public List<Map<String, Object>> getPostFromRedis(Set<String> postSet) {
        // Get post object.
        List<Object> result =  redisTemplate.executePipelined(
                (RedisCallback<Object>)
                        connection -> {
                            StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                            if (postSet != null) {
                                for (String postId : postSet) {
                                    stringRedisConnection.hGetAll("post:" + postId);
                                }
                            }
                            return null;
                        });
        List<Map<String, Object>> postList = (List)result;

        // Get member Object.
        result =  redisTemplate.executePipelined(
                (RedisCallback<Object>)
                        connection -> {
                            StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                            for(int i = 0;i<postList.size();i++) {
                                Map<String, Object> map = postList.get(i);
                                String memberId = (String)map.get("memberId");
                                stringRedisConnection.hGetAll("member:"+memberId);
                            }
                            return null;
                        });
        List<Map<String, Object>> memberList = (List)result;
        for(int i = 0;i<postList.size();i++) {
            Map<String, Object> map = postList.get(i);
            map.put("member", memberList.get(i));
        }
        return postList;
    }

  public List<Map<String, Object>> getTimelineFromRedis(String memberId) {
    Set<String> postSet =
        redisTemplate
            .opsForZSet()
            .rangeByScore(
                "home:" + memberId, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 10);
    return getPostFromRedis(postSet);
  }

  public List<Map<String, Object>> getProfileFromRedis(String memberId) {
    Set<String> postSet =
        redisTemplate
            .opsForZSet()
            .rangeByScore(
                "profile:" + memberId, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, 10);

      return getPostFromRedis(postSet);
  }

  public void loadingPostDataFromDatabase() {
    List<Post> postList = postDao.findAll();
    for (Post post : postList) {
      addPostToRedis(post);
    }
  }

    public List<Object> getMemberFromRedis(Set<String> memberSet) {
        List<Object> result = redisTemplate.executePipelined(
                (RedisCallback<Object>)
                        connection -> {
                            StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                            if (memberSet != null) {
                                for (String memberId : memberSet) {
                                    stringRedisConnection.hGetAll("member:" + memberId);
                                }
                            }
                            return null;
                        });
        return result;
    }
}
