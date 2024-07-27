package ra.project_md05.model.dto.request;


import lombok.*;


import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentsRequest {
    private String comment;

    private Date createdAt;

    private Boolean status;

    private Long productId;

    private Long userId;
}
