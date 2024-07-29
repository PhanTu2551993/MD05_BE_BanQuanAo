package ra.project_md05.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CouponsUserResponse {
    private Boolean valid;
    private Integer discount;
    private Long couponsId;
}
