package ra.project_md05.service;

import ra.project_md05.model.dto.request.CommentsRequest;
import ra.project_md05.model.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(CommentsRequest commentsRequest);
    List<Comment> getCommentsByProduct(Long productId);
    void deleteComment(Long id);

    Comment updateCommentStatus(Long id, Boolean status);
}
