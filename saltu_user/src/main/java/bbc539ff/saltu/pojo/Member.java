package bbc539ff.saltu.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Member implements Serializable {
  @Id private String memberId;
  @NotBlank(message = "memberName should not be blank")
  @Size(min = 3, max = 16, message = "memberName between 3-16")
  private String memberName;
  @NotBlank(message = "memberPassword should not be blank")
//  @Size(min = 8, max = 16, message = "memberPassword between 8-16")
  private String memberPassword;
  @Size(min = 11, max = 11, message = "memberPhone equals 11")
  private String memberPhone;
  private String memberEmail;
  @Min(value = 0, message = "memberFollowing bigger than 0")
  private Long memberFollowing;
  @Min(value = 0, message = "memberFollowers bigger than 0")
  private Long memberFollowers;
  private Date memberBirthDate;
  private String memberPic;
  private String memberLocation;
  private String memberWebsite;
  private Date memberCreate;
  private Date memberUpdate;

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getMemberName() {
    return memberName;
  }

  public void setMemberName(String memberName) {
    this.memberName = memberName;
  }

  public String getMemberPassword() {
    return memberPassword;
  }

  public void setMemberPassword(String memberPassword) {
    this.memberPassword = memberPassword;
  }

  public String getMemberPhone() {
    return memberPhone;
  }

  public void setMemberPhone(String memberPhone) {
    this.memberPhone = memberPhone;
  }

  public String getMemberEmail() {
    return memberEmail;
  }

  public void setMemberEmail(String memberEmail) {
    this.memberEmail = memberEmail;
  }

  public Long getMemberFollowing() {
    return memberFollowing;
  }

  public void setMemberFollowing(Long memberFollowing) {
    this.memberFollowing = memberFollowing;
  }

  public Long getMemberFollowers() {
    return memberFollowers;
  }

  public void setMemberFollowers(Long memberFollowers) {
    this.memberFollowers = memberFollowers;
  }

  public Date getMemberBirthDate() {
    return memberBirthDate;
  }

  public void setMemberBirthDate(Date memberBirthDate) {
    this.memberBirthDate = memberBirthDate;
  }

  public Date getMemberCreate() {
    return memberCreate;
  }

  public void setMemberCreate(Date memberCreate) {
    this.memberCreate = memberCreate;
  }

  public Date getMemberUpdate() {
    return memberUpdate;
  }

  public void setMemberUpdate(Date memberUpdate) {
    this.memberUpdate = memberUpdate;
  }

  public String getMemberPic() {
    return memberPic;
  }

  public void setMemberPic(String memberPic) {
    this.memberPic = memberPic;
  }

  public String getMemberLocation() {
    return memberLocation;
  }

  public void setMemberLocation(String memberLocation) {
    this.memberLocation = memberLocation;
  }

  public String getMemberWebsite() {
    return memberWebsite;
  }

  public void setMemberWebsite(String memberWebsite) {
    this.memberWebsite = memberWebsite;
  }

  @Override
  public String toString() {
    return "Member{" +
            "memberId='" + memberId + '\'' +
            ", memberName='" + memberName + '\'' +
            ", memberPassword='" + memberPassword + '\'' +
            ", memberPhone='" + memberPhone + '\'' +
            ", memberEmail='" + memberEmail + '\'' +
            ", memberFollowing=" + memberFollowing +
            ", memberFollowers=" + memberFollowers +
            ", memberBirthDate=" + memberBirthDate +
            ", memberCreate=" + memberCreate +
            ", memberUpdate=" + memberUpdate +
            ", memberPic='" + memberPic + '\'' +
            ", memberLocation='" + memberLocation + '\'' +
            ", memberWebsite='" + memberWebsite + '\'' +
            '}';
  }
}
