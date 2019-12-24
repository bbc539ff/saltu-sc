package bbc539ff.saltu.service;

import bbc539ff.saltu.dao.MemberDao;
import bbc539ff.saltu.pojo.Member;
import bbc539ff.saltu.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class MemberService {

  @Autowired private MemberDao memberDao;

  @Value("${snowflake.dataCenterId}")
  Long dataCenterId;

  @Value("${snowflake.machineId}")
  Long machineId;

  public List<Member> findAll() {
    return memberDao.findAll();
  }

  public void saveOne(Member member) {
    member.setMemberId(Long.toString(new SnowFlake(dataCenterId, machineId).nextId()));
    memberDao.save(member);
  }

  public void deleteById(String memberId) {
    memberDao.deleteById(memberId);
  }

  public void updateById(Member member) {
    memberDao.save(member);
  }

  public Member findById(String memberId) {
    return memberDao.findById(memberId).get();
  }

  public Member login(Map<String, String> loginForm) {
    // Login form data from UA
    String memberName = loginForm.get("memberName");
    String memberPassword = loginForm.get("memberPassword");
    Boolean rememberMe = Boolean.parseBoolean(loginForm.get("rememberMe"));

    // Member from DB
    Member member = memberDao.findByMemberName(memberName);

    // Compare password
    if (member != null && Objects.equals(member.getMemberPassword(), memberPassword)) {
      return member;
    } else {
      return null;
    }
  }
}
