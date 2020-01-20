package bbc539ff.saltu.post.dao;

import bbc539ff.saltu.post.pojo.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikePostDao extends JpaRepository<LikePost, LikePost.PK>, JpaSpecificationExecutor<LikePost> {
  @Query(value = "select l from LikePost l where l.memberId = ?1")
  List<LikePost> findAllByMemberId(String memberId);

  @Query(value = "select l from LikePost l where l.postId = ?1")
  List<LikePost> findAllByPostId(String postId);
}
