package bbc539ff.saltu.post.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
  @Id private String postId;
  private String memberId;
  private String postContent;
  private Date postDate;
  private String postOriginal;
  private String postRepostId;
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
