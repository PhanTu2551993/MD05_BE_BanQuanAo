package ra.project_md05.model.dto.request;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CouponsRequest {

    private String code;
    private Integer discount;
    private Date endDate;
    private Integer quantity;
    private Date startDate;
    private Boolean status;
    private String title;
}
