package ra.project_md05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_md05.model.entity.Product;
import ra.project_md05.model.entity.ShoppingCart;
import ra.project_md05.model.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findByUser(Users user);
    Optional<ShoppingCart> findByUserAndProduct(Users user, Product product);
}
