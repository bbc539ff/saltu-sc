package bbc539ff.saltu.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostServiceTest {
    @Autowired PostService postService;

    @Test
    public void getPostByPostId() {
        System.out.println(postService.getPostByPostId("409421865271169024"));
    }

    @Test
    public void uploadPicture() {
    }
}
