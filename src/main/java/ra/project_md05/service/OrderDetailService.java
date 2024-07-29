package ra.project_md05.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.project_md05.model.dto.response.OrderResponseRoleAdmin;
import ra.project_md05.model.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetail(Long orderId);
}
