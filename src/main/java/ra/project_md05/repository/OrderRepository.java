package ra.project_md05.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ra.project_md05.constants.OrderStatus;
import ra.project_md05.model.entity.Orders;
import ra.project_md05.model.entity.Users;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Orders findFirstByUserOrderByCreatedAtDesc(Users user);

    Page<Orders> findByStatus(OrderStatus status, Pageable pageable);

    Page<Orders> findByUser(Users user, Pageable pageable);

    Orders findBySerialNumber(String serialNumber);

    Page<Orders> findByUserAndStatus(Users user, OrderStatus status, Pageable pageable);

    @Query("SELECT o FROM Orders o WHERE o.user.userId = :userId")
    List<Orders> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
