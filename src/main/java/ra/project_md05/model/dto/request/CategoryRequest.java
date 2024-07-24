package ra.project_md05.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ra.project_md05.model.entity.Category;
import ra.project_md05.validation.NameExist;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryRequest {
    @NotEmpty(message = "CategoryName can be not empty")
    @NameExist(entityClass = Category.class, existName = "categoryName", message = "Category already exists")
    private String categoryName;
    private String description;
    private MultipartFile image;
    private Date createdAt = new Date();
    private Boolean status = true;

}
