package bbc539ff.saltu.member.service;

import bbc539ff.saltu.user.UserApplication;
import bbc539ff.saltu.user.service.MemberRedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest(classes = UserApplication.class)
public class MemberRedisServiceTest {
  @Autowired MemberRedisService memberRedisService;

  @Test
  public void loadingMemberDataFromDatabase() {
    memberRedisService.loadingMemberDataFromDatabase();
  }

  @Test
  public void getMemberById() {
    memberRedisService.getMemberById("409546635241197568");
  }
}
