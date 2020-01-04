package bbc539ff.saltu.post.service;

import bbc539ff.saltu.common.utils.SnowFlake;
import bbc539ff.saltu.post.dao.PostDao;
import bbc539ff.saltu.post.pojo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService {
  @Autowired PostDao postDao;

  @Autowired
  SnowFlake snowFlake;

  public Post addPost(Post post) {
    post.setPostId(Long.toString(snowFlake.nextId()));
    post.setPostDate(new Date());
    return postDao.save(post);
  }

  public Post updatePost(Post post) {
    return postDao.save(post);
  }

  public Post getPostByPostId(String postId) {
    return postDao.findById(postId).get();
  }

  public List<Post> getPostByMemberId(String memberId) {
    return postDao.findByMemberId(memberId);
  }

  public void disablePost(String postId, Integer postState) {
    postDao.updateState(postId, postState);
  }
}
