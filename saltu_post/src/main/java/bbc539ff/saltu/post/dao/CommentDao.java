package bbc539ff.saltu.post.dao;

import bbc539ff.saltu.post.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends JpaRepository<Comment, String>, JpaSpecificationExecutor<Comment> {
  @Modifying
  @Query("UPDATE Comment SET commentState = ?2 WHERE commentId = ?1")
  void updateState(String commentId, Integer commentState);
}
