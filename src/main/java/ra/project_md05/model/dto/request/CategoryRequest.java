package ra.project_md05.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryRequest {
    @NotEmpty(message = "CategoryName can be not empty")
    private String categoryName;
    private String description;
    private MultipartFile image;
    private Date createdAt = new Date();
    private Boolean status = true;

}
