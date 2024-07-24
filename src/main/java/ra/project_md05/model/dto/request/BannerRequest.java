package ra.project_md05.model.dto.request;

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
public class BannerRequest {
    private String bannerName;
    private Date createdAt;
    private String description;
    private MultipartFile image;
    private Boolean status ;
}
