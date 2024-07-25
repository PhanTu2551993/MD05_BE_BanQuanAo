package ra.project_md05.model.dto.response;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ProductDetailResponse {

   private String productName;
    private String categoryName;
    private String brandName;
    private int stock;
    private double price;
}