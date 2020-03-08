package bbc539ff.saltu.post.service;

import bbc539ff.saltu.post.dao.LikePostDao;
import bbc539ff.saltu.post.pojo.LikePost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class LikeService {
  @Autowired LikePostDao likePostDao;
  @Autowired LikeRedisService likeRedisService;

  public LikePost likeOne(String postId, String memberId) {
    LikePost likePostDB = likePostDao.findById(new LikePost.PK(postId, memberId)).orElse(null);
    log.info("likePostDB" + likePostDB);
    likeRedisService.likePost(likePostDB);
    if (likePostDB == null) return likePostDao.save(new LikePost(postId, memberId));
    else return null;
  }

  public LikePost unLikeOne(String postId, String memberId) {
    LikePost likePostDB = likePostDao.findById(new LikePost.PK(postId, memberId)).orElse(null);
    log.info("likePostDB" + likePostDB);
    likeRedisService.unLikePost(likePostDB);
    if (likePostDB != null) return likePostDao.save(new LikePost(postId, memberId));
    else return null;
  }

  public List<Map<String, Object>> getILikePost(String memberId) {
    return likeRedisService.getILikePost(memberId);
  }

  public List<Object> getPostLikedMember(String postId) {
    return likeRedisService.getPostLikedMember(postId);
  }
}
