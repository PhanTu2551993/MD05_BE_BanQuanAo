package ra.project_md05.model.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "config")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_id")
    private Long id;

    private String configName;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;
}
