package bbc539ff.saltu.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class LikeRedisServiceTest {
  @Autowired LikeRedisService likeRedisService;

  @Test
  public void loadingLikeDataFromDatabase() {
    likeRedisService.loadingLikeDataFromDatabase();
  }

  @Test
  public void getILikePost() {
    List<Map<String, Object>> list = likeRedisService.getILikePost("409546635241197568");
    System.out.println(list);
  }

  @Test
  public void getPostLikedMember() {
    List<Object> set = likeRedisService.getPostLikedMember("409421865271169024");
    System.out.println(set);
  }
}
