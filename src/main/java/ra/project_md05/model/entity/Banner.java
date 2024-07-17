package ra.project_md05.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "banner")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bannerName;
    private Date createdAt;
    private String description;
    private String image;
    private Boolean status;
}
