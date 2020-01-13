package bbc539ff.saltu.user.dao;

import bbc539ff.saltu.user.pojo.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowDao extends JpaRepository<Follow, Follow.PK>, JpaSpecificationExecutor<Follow> {
  @Query(value = "select f from Follow f where f.memberId = ?1")
  List<Follow> findAllByMemberId(String memberId);
}
