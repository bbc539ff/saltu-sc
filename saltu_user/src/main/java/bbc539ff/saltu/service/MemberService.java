package bbc539ff.saltu.service;

import bbc539ff.saltu.dao.MemberDao;
import bbc539ff.saltu.pojo.Member;
import bbc539ff.saltu.utils.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class MemberService {

  @Autowired private MemberDao memberDao;
  @Autowired BCryptPasswordEncoder encoder;
  @Autowired SnowFlake snowFlake;
  private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

  public List<Member> findAll() {
    return memberDao.findAll();
  }

  public void saveOne(Member member) {
    member.setMemberId(Long.toString(snowFlake.nextId()));
    member.setMemberPassword(encoder.encode(member.getMemberPassword()));
    logger.info(member.toString());
    memberDao.save(member);
  }

  public void deleteById(String memberId) {
    memberDao.deleteById(memberId);
  }

  public void updateById(Member member) {
    member.setMemberPassword(encoder.encode(member.getMemberPassword()));
    memberDao.save(member);
  }

  public Member findById(String memberId) {
    return memberDao.findById(memberId).get();
  }

  public Member login(Member member) {
    // Login form data from UA
    String memberName = member.getMemberName();
    String memberPassword = member.getMemberPassword();

    // Member from DB
    Member memberFromDB = memberDao.findByMemberName(memberName);

    // Compare password
    if (memberFromDB != null && encoder.matches(memberPassword, memberFromDB.getMemberPassword())) {
      return memberFromDB;
    } else {
      return null;
    }
  }
}
