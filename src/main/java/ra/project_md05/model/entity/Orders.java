package ra.project_md05.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ra.project_md05.constans.OrderStatus;

import java.util.Date;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private Date createdAt;

    private String district;
    private String note;
    private String phone;
    private String province;
    private Date receiveAt;
    private String receiveName;
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String streetAddress;
    private Double totalDiscountedPrice;
    private Double totalPrice;
    private Double totalPriceAfterCoupons;
    private String ward;

    @ManyToOne
    @JoinColumn(name = "coupons_id")
    private Coupons coupon;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
