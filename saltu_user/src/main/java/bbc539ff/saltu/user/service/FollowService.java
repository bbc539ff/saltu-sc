package bbc539ff.saltu.user.service;

import bbc539ff.saltu.user.dao.FollowDao;
import bbc539ff.saltu.user.dao.MemberDao;
import bbc539ff.saltu.user.pojo.Follow;
import bbc539ff.saltu.user.pojo.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class FollowService {
  @Autowired FollowDao followDao;
  @Autowired MemberDao memberDao;
  @Autowired FollowerRedisService followerRedisService;

  public List<Follow> findAllFollowerByMemberId(String memberId) {
    return followDao.findAllByMemberId(memberId);
  }

  public Follow followOne(Follow follow) {
    Member member = memberDao.findById(follow.getMemberId()).orElse(null);
    Member following = memberDao.findById(follow.getFollowingId()).orElse(null);
    followerRedisService.followOne(follow);
    if (member != null && following != null) {
      memberDao.incMemberFollowingByMemberId(member.getMemberId(), 1);
      return followDao.save(follow);
    }
    else return null;
  }

  public Boolean unFollowOne(Follow follow) {
    follow =
        followDao
            .findById(new Follow.PK(follow.getMemberId(), follow.getFollowingId()))
            .orElse(null);
    followerRedisService.unFollowOne(follow);
    if (follow != null) {
      followDao.delete(follow);
      return true;
    } else {
      return false;
    }
  }

  public List<Map<String, Object>> getFollowerList(String memberId) {
    return followerRedisService.getFollower(memberId);
  }

  public List<Map<String, Object>> getFollowingList(String memberId) {
    return followerRedisService.getFollowing(memberId);
  }
}
