package bbc539ff.saltu.user.service;

import bbc539ff.saltu.user.dao.FollowDao;
import bbc539ff.saltu.user.pojo.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
  @Autowired
  FollowDao followDao;

  public List<Follow> findAllFollowerByMemberId(String memberId){
    return followDao.findAllByMemberId(memberId);
  }

  public Follow followOne(Follow follow){
    return followDao.save(follow);
  }

  public void unFollowOne(Follow follow){
    followDao.delete(follow);
  }
}
