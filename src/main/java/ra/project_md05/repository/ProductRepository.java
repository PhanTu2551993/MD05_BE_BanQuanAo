package ra.project_md05.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.project_md05.model.entity.Category;
import ra.project_md05.model.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findFirst10ByOrderByCreatedAtDesc();
    Page<Product> findByProductNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);
    List<Product> findByCategory(Category category);

    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:searchTerm% OR p.description LIKE %:searchTerm%")
    List<Product> findByNameOrDescriptionContaining(@Param("searchTerm") String searchTerm);
}
