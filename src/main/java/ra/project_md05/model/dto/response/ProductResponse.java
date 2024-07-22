package ra.project_md05.model.dto.response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ProductResponse {
    private Long id;
    private String productName;
    private String sku;
    private String description;
    private String imageUrl;
    private Long categoryId;
    private Long brandId;
    private boolean status;
    private Date createdAt;
}
