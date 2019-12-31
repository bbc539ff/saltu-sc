package bbc539ff.saltu.user.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member implements Serializable {
  @Id private String memberId;

  @NotBlank
  @Size(min = 3, max = 16, message = "memberName between 3-16")
  private String memberName;

  @NotBlank
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
  private Boolean memberStatus;
  private Date memberCreate;
  private Date memberUpdate;
}
