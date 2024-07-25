package ra.project_md05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_md05.model.entity.Product;
import ra.project_md05.model.entity.ProductDetail;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findByProduct(Product product);
}
