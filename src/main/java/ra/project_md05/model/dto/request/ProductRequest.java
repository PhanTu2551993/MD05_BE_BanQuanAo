package ra.project_md05.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductRequest {
    @NotEmpty(message = "productName can be not empty")
    private String productName;
    @NotEmpty( message = "sku can be not empty")
    @UUID
    private String sku;

    private String description;
    private MultipartFile image;
    @NotNull(message = "categoryId can be not empty")
    private Long categoryId;
    @NotNull(message = "brandId can be not empty")
    private Long brandId;
    private Set<MultipartFile> imageSet;
    private Boolean status = true;
    private Date CreatedAt = new Date();
}
