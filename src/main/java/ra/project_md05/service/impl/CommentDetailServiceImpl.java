package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md05.model.entity.CommentDetail;
import ra.project_md05.repository.CommentDetailRepository;
import ra.project_md05.service.ICommentDetailService;

import java.util.List;

@Service
public class CommentDetailServiceImpl implements ICommentDetailService {
    @Autowired
    private CommentDetailRepository commentDetailRepository;

    @Override
    public CommentDetail addCommentDetail(CommentDetail commentDetail) {
        return commentDetailRepository.save(commentDetail);
    }

    @Override
    public List<CommentDetail> getCommentDetailsByComment(Long commentId) {
        return commentDetailRepository.findByCommentId(commentId);
    }
}
