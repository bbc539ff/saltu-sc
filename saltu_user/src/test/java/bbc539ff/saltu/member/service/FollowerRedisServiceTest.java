package bbc539ff.saltu.member.service;

import bbc539ff.saltu.user.UserApplication;
import bbc539ff.saltu.user.pojo.Follow;
import bbc539ff.saltu.user.service.FollowerRedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest(classes = UserApplication.class)
public class FollowerRedisServiceTest {
  @Autowired FollowerRedisService followerRedisService;

  @Test
  public void loadingFollowDataFromDatabase() {
    followerRedisService.loadingFollowDataFromDatabase();
  }

  @Test
  public void getFollower() {
    List<Map<String, Object>> list = followerRedisService.getFollower("409546635241197568");
    for(Map<String, Object> map : list) {
      System.out.println(map);
    }
  }
}
