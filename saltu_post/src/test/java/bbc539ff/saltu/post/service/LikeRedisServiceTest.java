package bbc539ff.saltu.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
public class LikeRedisServiceTest {
  @Autowired LikeRedisService likeRedisService;

  @Test
  public void loadingLikeDataFromDatabase() {
    likeRedisService.loadingLikeDataFromDatabase();
  }

  @Test
  public void getILikePost() {
    List<Map<String, Object>> set = likeRedisService.getILikePost("407244446783115264");
    System.out.println(set);
  }

  @Test
  public void getPostLikedMember() {
    List<Object> set = likeRedisService.getPostLikedMember("409421865271169024");
    System.out.println(set);
  }
}
