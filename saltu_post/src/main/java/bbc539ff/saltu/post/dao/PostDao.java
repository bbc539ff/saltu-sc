package bbc539ff.saltu.post.dao;

import bbc539ff.saltu.post.pojo.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao extends JpaRepository<Post, String> {
    @Modifying
    @Query("UPDATE Post SET postState = ?2 WHERE postId = ?1")
    void updateState(String postId, Integer postState);

    @Query("SELECT p FROM Post p WHERE p.memberId = ?1")
    List<Post> findAllByMemberId(String memberId);
}
