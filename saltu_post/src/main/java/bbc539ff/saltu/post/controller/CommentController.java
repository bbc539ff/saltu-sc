package bbc539ff.saltu.post.controller;

import bbc539ff.saltu.post.pojo.Comment;
import bbc539ff.saltu.post.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
  @Autowired CommentService commentService;

  @PostMapping("")
  public Comment addComment(@RequestBody Comment comment) {
    return commentService.addComment(comment);
  }

  @DeleteMapping("/{commentId}")
  public void disableComment(@PathVariable String commentId) {
    commentService.disableComment(commentId);
  }

  @PutMapping("")
  public Comment updateComment(@RequestBody Comment comment) {
    return commentService.updateComment(comment);
  }

  @GetMapping("/{commentId}")
  public Comment findCommentByCommentId(@PathVariable String commentId) {
    return commentService.findCommentByCommentId(commentId);
  }
}
