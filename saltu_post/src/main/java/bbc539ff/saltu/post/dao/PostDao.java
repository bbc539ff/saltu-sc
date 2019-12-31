package bbc539ff.saltu.post.dao;

import bbc539ff.saltu.post.pojo.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao extends JpaRepository<Post, String> {
}
