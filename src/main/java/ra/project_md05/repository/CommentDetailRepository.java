package ra.project_md05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.project_md05.model.entity.Comment;
import ra.project_md05.model.entity.CommentDetail;

import java.util.List;

@Repository
public interface CommentDetailRepository extends JpaRepository<CommentDetail, Long> {
    @Query("SELECT cd FROM CommentDetail cd WHERE cd.comment.id = :commentId")
    List<CommentDetail> findByCommentId(@Param("commentId") Long commentId);
}
