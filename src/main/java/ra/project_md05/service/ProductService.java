package ra.project_md05.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.project_md05.model.dto.PageDTO;
import ra.project_md05.model.dto.request.ProductRequest;
import ra.project_md05.model.dto.response.ProductResponse;
import ra.project_md05.model.entity.Category;
import ra.project_md05.model.entity.Product;
import ra.project_md05.model.entity.ProductDetail;

import java.util.List;

public interface ProductService {
    Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDir);

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    void deleteProduct(Long id);

    Page<ProductResponse> findByNameOrDescriptionContaining(String search, Pageable pageable);

    List<Product> findFirst10ByOrderByCreatedAtDesc();

    List<Product> findByCategory(Category category);

    Page<Product> findAll(Pageable pageable);

    PageDTO<ProductResponse> getAllProductRolePermitAll(Pageable pageable);

    Product getProductById(Long productId);
     List<Product> getAllProducts();

}
