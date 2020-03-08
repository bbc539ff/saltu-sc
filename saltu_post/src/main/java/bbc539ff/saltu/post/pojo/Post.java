package bbc539ff.saltu.post.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Post implements Serializable {
  @Id
  private String postId;
  private String memberId;
  @Length(max = 200, message = "postContent should be less than 200 characters")
  private String postContent;
  private Date postDate;
  private String postOriginal;
  private String postRepostId;
  private Integer postRepostNumber;
  private Integer postCommentNumber;
  private Integer postLikeNumber;
  private Integer postState;
  private String postPic1;
  private String postPic2;
  private String postPic3;
  private String postPic4;
  private String postPic5;
  private String postPic6;
  private String postPic7;
  private String postPic8;
  private String postPic9;
}
