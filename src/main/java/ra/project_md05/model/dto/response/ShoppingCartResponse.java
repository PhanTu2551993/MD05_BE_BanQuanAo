package ra.project_md05.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ShoppingCartResponse {
    private Long productId;
    private String productName;
    private String productImage;
    private Double productPrice;
    private Integer orderQuantity;
}
