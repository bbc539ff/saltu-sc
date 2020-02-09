package bbc539ff.saltu.post.pojo;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Comment {
  @Id
  private String commentId;
  private String memberId;
  private String postId;
  @Length(min = 0, max = 400)
  private String commentContent;
  private Date commentDate;
  private Date commentUpdateDate;
  private Integer commentState;
  private String commentPic;
}
