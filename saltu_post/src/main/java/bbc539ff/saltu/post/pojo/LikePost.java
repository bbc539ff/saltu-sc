package bbc539ff.saltu.post.pojo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@IdClass(LikePost.PK.class)
public class LikePost implements Serializable {
  @Id @NotBlank private String postId;
  @Id @NotBlank private String memberId;
  private Date likeTime;

  public PK getId() {
    return new PK(postId, memberId);
  }

  public void setId(PK id) {
    this.postId = id.getPostId();
    this.memberId = id.getMemberId();
  }

  public LikePost(String postId, String memberId) {
    this.postId = postId;
    this.memberId = memberId;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  public static class PK implements Serializable {
    private String postId;
    private String memberId;
  }
}
