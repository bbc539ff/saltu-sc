package bbc539ff.saltu.post.service;

import bbc539ff.saltu.common.utils.SnowFlake;
import bbc539ff.saltu.post.dao.CommentDao;
import bbc539ff.saltu.post.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {
  @Autowired CommentDao commentDao;
  @Autowired SnowFlake snowFlake;

  public Comment addComment(Comment comment) {
    comment.setCommentId(Long.toString(snowFlake.nextId()));
    comment.setCommentDate(new Date());
    comment.setCommentUpdateDate(new Date());
    comment.setCommentState(1);
    return commentDao.save(comment);
  }

  public void delComment(String commentId) {
    commentDao.deleteById(commentId);
  }

  public void disableComment(String commentId) {
    commentDao.updateState(commentId, 0);
  }

  public Comment updateComment(Comment comment) {
    comment.setCommentUpdateDate(new Date());
    return commentDao.save(comment);
  }

  public Comment findCommentByCommentId(String commentId) {
    return commentDao.findById(commentId).orElse(null);
  }
}
