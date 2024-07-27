package ra.project_md05.service;

import ra.project_md05.model.entity.CommentDetail;

import java.util.List;

public interface ICommentDetailService {
    CommentDetail addCommentDetail(CommentDetail commentDetail);
    List<CommentDetail> getCommentDetailsByComment(Long commentId);
}
