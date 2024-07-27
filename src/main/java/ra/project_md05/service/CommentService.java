package ra.project_md05.service;

import ra.project_md05.model.entity.Comment;

public interface CommentService {
    void deleteComment(Long id);

    Comment updateCommentStatus(Long id, Boolean status);
}
