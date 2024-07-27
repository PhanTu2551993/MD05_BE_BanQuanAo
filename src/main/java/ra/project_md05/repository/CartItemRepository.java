package ra.project_md05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_md05.model.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
