package bbc539ff.saltu.user.service;

import bbc539ff.saltu.common.utils.SnowFlake;
import bbc539ff.saltu.user.dao.MemberDao;
import bbc539ff.saltu.user.pojo.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MemberService {
  @Autowired private MemberDao memberDao;
  @Autowired BCryptPasswordEncoder encoder;
  @Autowired SnowFlake snowFlake;
  @Autowired MemberRedisService memberRedisService;
  private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

  public List<Member> findAll() {
    return memberDao.findAll();
  }

  /**
   * New member register
   *
   * @param member
   * @return
   */
  public Member saveOne(Member member) {
    member.setMemberId(Long.toString(snowFlake.nextId()));
    member.setMemberPassword(encoder.encode(member.getMemberPassword()));
    member.setMemberFollowing(new Long(0));
    member.setMemberFollowers(new Long(0));
    member.setMemberCreate(new Date());
    member.setMemberUpdate(new Date());
    member.setMemberState(1);
    logger.info("Add new member: " + member.toString());
    memberRedisService.addMemberToRedis(member);
    return memberDao.save(member);
  }

  /**
   * Disable Account(Set member_status as 0).
   *
   * @param memberId
   */
  public void disableById(String memberId) {
    memberDao.updateMemberStateByMemberId(memberId, 0);
  }

  public void updateById(Member member) {
    member.setMemberPassword(encoder.encode(member.getMemberPassword()));
    member.setMemberUpdate(new Date());
    memberRedisService.addMemberToRedis(member);
    memberDao.save(member);
  }

  public Member findById(String memberId) {
    return memberDao.findById(memberId).get();
  }

  public Member findByMemberName(String memberName) {
    Member member = memberDao.findByMemberName(memberName);
    member.setMemberPassword(null);
    return member;
  }

  public Member login(Member member) {
    // Login form data from UA
    String memberName = member.getMemberName();
    String memberPassword = member.getMemberPassword();

    // Member from DB
    Member memberFromDB = memberDao.findByMemberName(memberName);

    // Compare password
    if (memberFromDB != null && encoder.matches(memberPassword, memberFromDB.getMemberPassword())) {
      // Refresh lastLogin in redis.
      memberRedisService.redisTemplate
          .opsForHash()
          .put(
              "member: " + member.getMemberId(),
              "LastLogin",
              Long.toString(System.currentTimeMillis()));
      return memberFromDB;
    } else {
      return null;
    }
  }
}
