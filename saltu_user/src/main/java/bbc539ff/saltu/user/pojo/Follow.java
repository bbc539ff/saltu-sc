package bbc539ff.saltu.user.pojo;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@IdClass(Follow.PK.class)
public class Follow implements Serializable {
  @Id @NotBlank private String memberId;
  @Id @NotBlank private String followingId;
  private Date followingTime;

  public PK getId() {
    return new PK(memberId, followingId);
  }

  public void setId(PK id) {
    this.memberId = id.getMemberId();
    this.followingId = id.getFollowingId();
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  public static class PK implements Serializable {
    private String memberId;
    private String followingId;
  }
}
