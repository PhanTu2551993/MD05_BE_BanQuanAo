package ra.project_md05.model.dto.request;

import lombok.*;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailRequest {
    private Long productDetailId;
    private String name;
    private Double unitPrice;
    private Integer orderQuantity;
    private Long oderId;
}
