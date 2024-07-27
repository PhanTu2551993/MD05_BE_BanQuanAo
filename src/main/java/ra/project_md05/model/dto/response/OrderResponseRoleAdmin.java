package ra.project_md05.model.dto.response;

import lombok.*;
import ra.project_md05.constants.OrderStatus;
import ra.project_md05.model.entity.Orders;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderResponseRoleAdmin {
    private Long orderId;

    private String serialNumber;

    private String userName;

    private Double totalPrice;

    private OrderStatus status;

    private String note;

    private String receiveName;

    private String receiveAddress;

    private Date createdAt;

    private Date receivedAt;

}
