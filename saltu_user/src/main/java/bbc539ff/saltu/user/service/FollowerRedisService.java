package bbc539ff.saltu.user.service;

import bbc539ff.saltu.user.dao.FollowDao;
import bbc539ff.saltu.user.pojo.Follow;
import bbc539ff.saltu.user.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * structure:
 *  follower(memberId--followerId):
 *   java ver: {Set<String> follower:%memberId = new HashSet<String>(followerId)};
 *   Redis ver: ZSet{key: "follower:%memberId", followerId, timestamp)}
 *  following(memberId--followingId):
 *   java ver: {Set<String> following:%memberId = new HashSet<String>(followingId)};
 *   Redis ver: ZSet{key: "following:%memberId", followingId, timestamp)}
 */

@Service
public class FollowerRedisService {
  @Autowired StringRedisTemplate redisTemplate;
  @Autowired FollowDao followDao;

  public void addFollowing(StringRedisConnection stringRedisConnection, Follow follow) {
    // if already followed.
    if (stringRedisConnection.zScore("following:" + follow.getMemberId(), follow.getFollowingId())
        != null) {
      return;
    }
    stringRedisConnection.zAdd(
        "following:" + follow.getMemberId(), System.currentTimeMillis(), follow.getFollowingId());
  }

  public void addFollower(StringRedisConnection stringRedisConnection, Follow follow) {
    // if already followed.
    if (stringRedisConnection.zScore("following:" + follow.getMemberId(), follow.getFollowingId())
        != null) {
      return;
    }
    stringRedisConnection.zAdd(
        "follower:" + follow.getFollowingId(), System.currentTimeMillis(), follow.getMemberId());
  }

  public void delFollowing(StringRedisConnection stringRedisConnection, Follow follow) {
    // if not followed.
    if (stringRedisConnection.zScore("following:" + follow.getMemberId(), follow.getFollowingId())
        == null) {
      return;
    }
    stringRedisConnection.zRem("following:" + follow.getMemberId(), follow.getFollowingId());
  }

  public void delFollower(StringRedisConnection stringRedisConnection, Follow follow) {
    // if already followed.
    if (stringRedisConnection.zScore("following:" + follow.getMemberId(), follow.getFollowingId())
        == null) {
      return;
    }
    stringRedisConnection.zRem("follower:" + follow.getFollowingId(), follow.getMemberId());
  }

  public void followOne(Follow follow) {
    redisTemplate.executePipelined(
        (RedisCallback<Object>)
            connection -> {
              StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
              addFollowing(stringRedisConnection, follow);
              addFollower(stringRedisConnection, follow);
              return null;
            });
  }

  public void unFollowOne(Follow follow) {
    redisTemplate.executePipelined(
        (RedisCallback<Object>)
            connection -> {
              StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
              delFollowing(stringRedisConnection, follow);
              delFollower(stringRedisConnection, follow);
              return null;
            });
  }

  public void loadingFollowDataFromDatabase() {
    List<Follow> followList = followDao.findAll();
    redisTemplate.executePipelined(
        (RedisCallback<Object>)
            connection -> {
              StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
              for (Follow follow : followList) {
                addFollowing(stringRedisConnection, follow);
                addFollower(stringRedisConnection, follow);
              }
              return null;
            });
  }
}
