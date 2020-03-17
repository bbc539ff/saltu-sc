package bbc539ff.saltu.post.service;

import bbc539ff.saltu.common.exception.Result;
import bbc539ff.saltu.common.utils.SnowFlake;
import bbc539ff.saltu.post.client.MemberClient;
import bbc539ff.saltu.post.dao.PostDao;
import bbc539ff.saltu.post.pojo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
public class PostService {
  @Autowired PostDao postDao;
  @Autowired MemberClient memberClient;
  @Autowired PostRedisService postRedisService;

  @Autowired SnowFlake snowFlake;

  @Value("${upload.path}")
  String UPLOAD_PATH;

  public Post addPost(Post post) {
    post.setPostId(Long.toString(snowFlake.nextId()));
    post.setPostDate(new Date());
    post.setPostOriginal("");
    post.setPostRepostId("");
    post.setPostRepostNumber(0);
    post.setPostLikeNumber(0);
    post.setPostCommentNumber(0);
    post.setPostState(1);
    if (!Objects.equals(post.getPostPic1().trim(), ""))
      post.setPostPic1(post.getPostId() + File.separator + post.getPostPic1());
    if (!Objects.equals(post.getPostPic2().trim(), ""))
      post.setPostPic2(post.getPostId() + File.separator + post.getPostPic2());
    if (!Objects.equals(post.getPostPic3().trim(), ""))
      post.setPostPic3(post.getPostId() + File.separator + post.getPostPic3());
    if (!Objects.equals(post.getPostPic4().trim(), ""))
      post.setPostPic4(post.getPostId() + File.separator + post.getPostPic4());
    if (!Objects.equals(post.getPostPic5().trim(), ""))
      post.setPostPic5(post.getPostId() + File.separator + post.getPostPic5());
    if (!Objects.equals(post.getPostPic6().trim(), ""))
      post.setPostPic6(post.getPostId() + File.separator + post.getPostPic6());
    if (!Objects.equals(post.getPostPic7().trim(), ""))
      post.setPostPic7(post.getPostId() + File.separator + post.getPostPic7());
    if (!Objects.equals(post.getPostPic8().trim(), ""))
      post.setPostPic8(post.getPostId() + File.separator + post.getPostPic8());
    if (!Objects.equals(post.getPostPic9().trim(), ""))
      post.setPostPic9(post.getPostId() + File.separator + post.getPostPic9());

    postRedisService.addPostToRedis(post);
    return postDao.save(post);
  }

  public Post updatePost(Post post) {
    postRedisService.addPostToRedis(post);
    return postDao.save(post);
  }

  public Map<String, Object> getPostByPostId(String postId) {
    Set<String> set = new HashSet<>();
    set.add(postId);
    List<Map<String, Object>> postList = postRedisService.getPostFromRedis(set);
    return postList.get(0);
  }

  public List<Post> getPostByMemberId(String memberId, String token) {
    Result result = memberClient.findByMemberId(memberId, token);
    if (result.getCode() == 1) {
      return postDao.findAllByMemberId(memberId);
    } else {
      return null;
    }
  }

  public void disablePost(String postId, Integer postState) {
    postDao.updateState(postId, postState);
    postRedisService.deletePostFromRedis(postId);
  }

  public List<Map<String, Object>> getTimelineFromRedis(String memberId) {
    return postRedisService.getTimelineFromRedis(memberId);
  }

  public List<Map<String, Object>> getProfileFromRedis(String memberId) {
    List<Map<String, Object>> list = postRedisService.getProfileFromRedis(memberId);
    return list;
  }

  public boolean uploadPicture(MultipartFile file, String postId, String picNum) {
    System.out.println(file.getOriginalFilename());
    if (file.isEmpty()) {
      System.out.println("No file!");
      return false;
    }
    try {
      // Get the file and save it somewhere
      byte[] bytes = file.getBytes();
      Path savePath =
          Paths.get(
              UPLOAD_PATH
                  + File.separator
                  + postId
                  + File.separator
                  + picNum
                  + ".jpg");
      // Make parent folder.
      File saveFile = new File(UPLOAD_PATH
              + File.separator
              + postId
              + File.separator
              + picNum
              + ".jpg");
      if(!saveFile.getParentFile().exists()) saveFile.getParentFile().mkdirs();
      // Write in path.
      Files.write(savePath, bytes);

    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  public Object[] getTop10HashTag() {
    return postRedisService.getTop10HashTag().toArray();
  }
}
