package ra.project_md05.model.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDetailRequest {
    private Long id;
    private String productDetailName;
    private double unitPrice;
    private MultipartFile image;
    private int stock;
    private Long colorId;
    private Long productId;
    private boolean status = true;
    private Set<ConfigRequest> configs;
}
