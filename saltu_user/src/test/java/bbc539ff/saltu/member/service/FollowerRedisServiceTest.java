package bbc539ff.saltu.member.service;

import bbc539ff.saltu.user.UserApplication;
import bbc539ff.saltu.user.service.FollowerRedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UserApplication.class)
public class FollowerRedisServiceTest {
  @Autowired FollowerRedisService followerRedisService;

  @Test
  public void loadingFollowDataFromDatabase() {
    followerRedisService.loadingFollowDataFromDatabase();
  }
}
