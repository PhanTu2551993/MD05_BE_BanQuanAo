package ra.project_md05.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ra.project_md05.model.entity.Product;
import ra.project_md05.validation.NameExist;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductRequest {
    @NotEmpty(message = "productName can be not empty")
    @NameExist(entityClass = Product.class, existName = "productName", message = "Product already exists")
    private String productName;
    @NotEmpty( message = "sku can be not empty")
    private String sku = UUID.randomUUID().toString();
    private String description;
    private MultipartFile image;
    @NotNull(message = "categoryId can be not empty")
    private Long categoryId;
    @NotNull(message = "brandId can be not empty")
    private Long brandId;
    private Integer stock;
    private Double price;
    private Boolean status = true;
    private Date CreatedAt = new Date();
}
