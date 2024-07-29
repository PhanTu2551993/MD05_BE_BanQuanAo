package ra.project_md05.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.project_md05.model.entity.OrderDetail;
import ra.project_md05.model.entity.Orders;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrder(Orders order);
    List<OrderDetail> findByOrderId(Long orderId);

}
