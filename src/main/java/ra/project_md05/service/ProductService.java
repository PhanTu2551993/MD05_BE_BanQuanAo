package ra.project_md05.service;

import org.springframework.data.domain.Page;
import ra.project_md05.model.dto.request.ProductRequest;
import ra.project_md05.model.dto.response.ProductResponse;

public interface ProductService {
    Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDir);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);
}
