package bbc539ff.saltu.user.service;

import bbc539ff.saltu.user.dao.MemberDao;
import bbc539ff.saltu.user.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * structure:
 *  member:
 *   java ver: {Map<String, String> member:%memberId = new HashMap<String, String>()};
 *   Redis ver: Hash{key: "member:%id", attribute, value)}
 */

@Service
public class MemberRedisService {
  @Autowired StringRedisTemplate redisTemplate;
  @Autowired private MemberDao memberDao;

  public void addMember(StringRedisConnection stringRedisConnection, Member member) {
    stringRedisConnection.hSet("member:" + member.getMemberId(), "memberId", member.getMemberId());
    stringRedisConnection.hSet(
        "member:" + member.getMemberId(), "memberName", member.getMemberName());
    stringRedisConnection.hSet(
        "member:" + member.getMemberId(),
        "memberFollowing",
        Long.toString(member.getMemberFollowing()));
    stringRedisConnection.hSet(
        "member:" + member.getMemberId(),
        "memberFollowers",
        Long.toString(member.getMemberFollowers()));
    stringRedisConnection.hSet(
        "member:" + member.getMemberId(),
        "memberBirthDate",
        member.getMemberBirthDate() != null
            ? Long.toString(member.getMemberBirthDate().getTime())
            : "");
    stringRedisConnection.hSet(
        "member:" + member.getMemberId(),
        "memberPic",
        member.getMemberPic() != null ? member.getMemberPic() : "");
    stringRedisConnection.hSet(
        "member:" + member.getMemberId(),
        "memberLocation",
        member.getMemberLocation() != null ? member.getMemberLocation() : "");
    stringRedisConnection.hSet(
        "member:" + member.getMemberId(),
        "memberWebsite",
        member.getMemberWebsite() != null ? member.getMemberWebsite() : "");
    stringRedisConnection.hSet(
        "member:" + member.getMemberId(),
        "memberState",
        member.getMemberState() != null ? Integer.toString(member.getMemberState()) : "");
      stringRedisConnection.hSet(
              "member:" + member.getMemberId(),
              "memberCreate",
              member.getMemberCreate() != null ? Long.toString(member.getMemberCreate().getTime()) : "");
    stringRedisConnection.hSet(
        "member:" + member.getMemberId(), "LastLogin", Long.toString(System.currentTimeMillis()));
  }

  public void addMemberToRedis(Member member) {
    redisTemplate.executePipelined(
        (RedisCallback<Object>)
            connection -> {
              StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
              addMember(stringRedisConnection, member);
              return null;
            });
  }

  public void delMemberToRedis(String memberId) {
    redisTemplate.opsForHash().put("member:" + memberId, "memberState", 0);
  }

  public void loadingMemberDataFromDatabase() {
    List<Member> memberList = memberDao.findAll();
    redisTemplate.executePipelined(
        (RedisCallback<Object>)
            connection -> {
              StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
              for (Member member : memberList) {
                addMember(stringRedisConnection, member);
              }
              return null;
            });
  }

  public Map<String, Object> getMemberById(String memberId) {
      Map map = redisTemplate.opsForHash().entries("member:"+memberId);
      return map;
  }
}
