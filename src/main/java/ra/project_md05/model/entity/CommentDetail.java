package ra.project_md05.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "comment_detail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_detail_id")
    private Long id;

    private Date createdAt;
    private String review;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
