package bbc539ff.saltu.dao;

import bbc539ff.saltu.pojo.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao extends JpaRepository<Member, String>, JpaSpecificationExecutor<Member> {

  @Query(value = "select m from Member m where m.memberName = ?1")
  Member findByMemberName(String memberName);
}
