package bbc539ff.saltu.user.service;

import bbc539ff.saltu.user.dao.FollowDao;
import bbc539ff.saltu.user.dao.MemberDao;
import bbc539ff.saltu.user.pojo.Follow;
import bbc539ff.saltu.user.pojo.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FollowService {
  @Autowired FollowDao followDao;
  @Autowired MemberDao memberDao;

  public List<Follow> findAllFollowerByMemberId(String memberId) {
    return followDao.findAllByMemberId(memberId);
  }

  public Follow followOne(Follow follow) {
    Member member = memberDao.findByMemberName(follow.getMemberId());
    Member following = memberDao.findByMemberName(follow.getFollowingId());
    if (member != null && following != null) return followDao.save(follow);
    else return null;
  }

  public Boolean unFollowOne(Follow follow) {
    follow =
        followDao
            .findById(new Follow.PK(follow.getMemberId(), follow.getFollowingId()))
            .orElse(null);
    log.info("deleting follow: " + follow);
    if (follow != null) {
      followDao.delete(follow);
      return true;
    } else {
      return false;
    }
  }
}
