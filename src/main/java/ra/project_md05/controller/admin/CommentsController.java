package ra.project_md05.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md05.config.ConvertPageToPaginationDTO;
import ra.project_md05.constants.EHttpStatus;

import ra.project_md05.model.dto.response.ResponseWrapper;
import ra.project_md05.model.entity.Comment;
import ra.project_md05.repository.CommentRepository;
import ra.project_md05.service.CommentService;

@RestController
@RequestMapping("/api/v1/admin/comment")
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;
    @GetMapping()
    public ResponseEntity<?> findAll(@PageableDefault(page = 0, size = 5, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        return new ResponseEntity<>(new ResponseWrapper<>(
                EHttpStatus.SUCCESS,
                HttpStatus.OK.value(),
                ConvertPageToPaginationDTO.convertPageToPaginationDTO(comments)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCommentStatus(@PathVariable Long id, @RequestBody Comment updatedComment) {
        try {
            Comment comment = commentService.updateCommentStatus(id, updatedComment.getStatus());
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update comment status");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok().body("Comment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete comment");
        }
    }
}
