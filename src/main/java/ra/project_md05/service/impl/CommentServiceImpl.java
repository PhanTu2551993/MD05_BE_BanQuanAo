package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md05.model.entity.Comment;
import ra.project_md05.repository.ICommentRepository;
import ra.project_md05.service.CommentService;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired

    private ICommentRepository commentRepository;
    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }


    @Override
    public Comment updateCommentStatus(Long id, Boolean status) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setStatus(status);
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
