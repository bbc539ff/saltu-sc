package bbc539ff.saltu.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class PostRedisServiceTest {
  @Autowired PostRedisService postRedisService;

  @Test
  public void loadingPostDataFromDatabase() {
    postRedisService.loadingPostDataFromDatabase();
  }

  @Test
  public void getTimelineFromRedis() {
      List<Map<String, Object>> list = postRedisService.getTimelineFromRedis("409546635241197568");
      for(Map map : list){
          System.out.println(map);
      }
  }

    @Test
    public void getProfileFromRedis() {
        List<Map<String, Object>> list = postRedisService.getProfileFromRedis("407244446783115264");
        for(Map map : list){
            System.out.println(map);
        }
    }
}
