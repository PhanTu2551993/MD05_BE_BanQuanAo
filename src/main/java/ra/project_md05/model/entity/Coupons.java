package ra.project_md05.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "coupons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Coupons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupons_id")
    private Long couponsId;

    private String code;
    private Integer discount;
    private Date endDate;
    private Integer quantity;
    private Date startDate;
    private Boolean status;
    private String title;
}
