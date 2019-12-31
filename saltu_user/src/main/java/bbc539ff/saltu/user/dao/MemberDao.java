package bbc539ff.saltu.user.dao;

import bbc539ff.saltu.user.pojo.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao extends JpaRepository<Member, String>, JpaSpecificationExecutor<Member> {
  @Query(value = "select m from Member m where m.memberName = ?1")
  Member findByMemberName(String memberName);

  @Modifying
  @Query("UPDATE Member SET memberStatus = ?2 WHERE memberId = ?1")
  void updateMemberStatusByMemberId(String memberId, Integer memberStatus);
}
