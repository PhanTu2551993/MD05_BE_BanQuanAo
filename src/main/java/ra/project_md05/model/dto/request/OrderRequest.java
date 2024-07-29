package ra.project_md05.model.dto.request;

import jakarta.persistence.*;
import lombok.*;
import ra.project_md05.constants.OrderStatus;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequest {

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
        private String coupon;
        private Long userId;
        private Long couponsId;
        private List<OrderDetailRequest> orderDetails;

}
