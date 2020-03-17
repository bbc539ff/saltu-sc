package bbc539ff.saltu.user.service;

import bbc539ff.saltu.user.dao.FollowDao;
import bbc539ff.saltu.user.pojo.Follow;
import bbc539ff.saltu.user.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    if (redisTemplate.opsForZSet().score("following:" + follow.getMemberId(), follow.getFollowingId())
        != null) {
      return;
    }
    stringRedisConnection.zAdd(
        "following:" + follow.getMemberId(), System.currentTimeMillis(), follow.getFollowingId());
    stringRedisConnection.hIncrBy("member:"+follow.getMemberId(), "memberFollowing", 1);
  }

  public void addFollower(StringRedisConnection stringRedisConnection, Follow follow) {
    // if already followed.
    if (redisTemplate.opsForZSet().score("follower:" + follow.getFollowingId(), follow.getMemberId())
        != null) {
      return;
    }
    stringRedisConnection.zAdd(
        "follower:" + follow.getFollowingId(), System.currentTimeMillis(), follow.getMemberId());
      stringRedisConnection.hIncrBy("member:"+follow.getFollowingId(), "memberFollowers", 1);
  }

  public void delFollowing(StringRedisConnection stringRedisConnection, Follow follow) {
    // if not followed.
    if (redisTemplate.opsForZSet().score("following:" + follow.getMemberId(), follow.getFollowingId())
        == null) {
      return;
    }
    stringRedisConnection.zRem("following:" + follow.getMemberId(), follow.getFollowingId());
      stringRedisConnection.hIncrBy("member:"+follow.getMemberId(), "memberFollowing", -1);
  }

  public void delFollower(StringRedisConnection stringRedisConnection, Follow follow) {
    // if already followed.
    if (redisTemplate.opsForZSet().score("follower:" + follow.getFollowingId(), follow.getMemberId())
        == null) {
      return;
    }
    stringRedisConnection.zRem("follower:" + follow.getFollowingId(), follow.getMemberId());
      stringRedisConnection.hIncrBy("member:"+follow.getFollowingId(), "memberFollowers", -1);
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
                followOne(follow);
              }
              return null;
            });
  }

    public boolean isFollower(String memberId, String followerId) {
      Long res = redisTemplate.opsForZSet().rank("follower:"+memberId, followerId);
      if(res != null) return true;
      else return false;
    }

    public boolean isFollowing(String memberId, String followingId) {
        Long res = redisTemplate.opsForZSet().rank("following:"+memberId, followingId);
        if(res != null) return true;
        else return false;
    }

    public List<Map<String, Object>> getFollower(String memberId) {
        List resList = redisTemplate.executePipelined(
                (RedisCallback<Object>)
                        connection -> {
                            StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                            Set<String> resSet = redisTemplate.opsForZSet().rangeByScore("follower:"+memberId, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                            for (String followerId : resSet) {
                                stringRedisConnection.hGetAll("member:"+followerId);
                            }
                            return null;
                        });
        List<Map<String, Object>> list = resList;
        return list;
    }

    public List<Map<String, Object>> getFollowing(String memberId) {
        List resList = redisTemplate.executePipelined(
                (RedisCallback<Object>)
                        connection -> {
                            StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                            Set<String> resSet = redisTemplate.opsForZSet().rangeByScore("following:"+memberId, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
                            for (String followerId : resSet) {
                                stringRedisConnection.hGetAll("member:"+followerId);
                            }
                            return null;
                        });
        List<Map<String, Object>> list = resList;
        return list;
    }
}
