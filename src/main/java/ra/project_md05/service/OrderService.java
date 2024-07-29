package ra.project_md05.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.project_md05.constants.OrderStatus;
import ra.project_md05.model.dto.request.OrderRequest;
import ra.project_md05.model.dto.response.OrderResponseRoleAdmin;
import ra.project_md05.model.entity.Orders;

import java.util.List;


public interface OrderService {

    Page<Orders> findAll(Pageable pageable);

    Page<OrderResponseRoleAdmin> getAllOrderRoleAdmin(Pageable pageable);

    OrderResponseRoleAdmin updateOrderStatusById(Long orderId, OrderStatus status);

    Orders addOrderUser(OrderRequest orderRequest);
}
