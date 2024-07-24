package ra.project_md05.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ra.project_md05.model.dto.PageDTO;
import ra.project_md05.model.dto.request.ProductRequest;
import ra.project_md05.model.dto.response.ProductResponse;
import ra.project_md05.model.entity.Category;
import ra.project_md05.model.entity.Product;
import ra.project_md05.repository.BrandRepository;
import ra.project_md05.repository.CategoryRepository;
import ra.project_md05.repository.ProductRepository;
import ra.project_md05.service.ProductService;
import ra.project_md05.service.UploadService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private UploadService uploadService;

    @Override
    public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(this::convertToResponse);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = convertToEntity(productRequest);
        Product savedProduct = productRepository.save(product);
        return convertToResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id " + id));
        product.setProductName(productRequest.getProductName());
        product.setSku(productRequest.getSku());
        product.setDescription(productRequest.getDescription());
        product.setImage(uploadService.uploadFileToServer(productRequest.getImage()));
        product.setCategory(categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new NoSuchElementException("Category not found with id " + productRequest.getCategoryId())));
        product.setBrand(brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new NoSuchElementException("Brand not found with id " + productRequest.getBrandId())));
        product.setStatus(productRequest.getStatus());
        product.setCreatedAt(productRequest.getCreatedAt());
        product.setUpdatedAt(new Date());
        Product updatedProduct = productRepository.save(product);
        return convertToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id " + id));
        productRepository.delete(product);
    }

    @Override
    public Page<ProductResponse> findByNameOrDescriptionContaining(String search, Pageable pageable) {
        Page<Product> products = productRepository.findByProductNameContainingOrDescriptionContaining(search, search, pageable);
        return products.map(this::convertToResponse);
    }

    @Override
    public List<Product> findFirst10ByOrderByCreatedAtDesc() {
        return productRepository.findFirst10ByOrderByCreatedAtDesc();
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }


    @Override
    public PageDTO<ProductResponse> getAllProductRolePermitAll(Pageable pageable) {
        Page<Product> productPage = findAll(pageable);
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (Product product : productPage) {
            ProductResponse productResponse = ProductResponse.builder()
                    .id(product.getProductId())
                    .sku(product.getSku())
                    .productName(product.getProductName())
                    .categoryId(product.getCategory().getCategoryId())
                    .createdAt(product.getCreatedAt())
                    .brandId(product.getBrand().getId())
                    .imageUrl(product.getImage())
                    .description(product.getDescription())
                    .build();

            productResponseList.add(productResponse);
        }
        return new PageDTO<>(new PageImpl<>(productResponseList, pageable, productPage.getTotalElements()));
    }

    private ProductResponse convertToResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getProductId());
        productResponse.setProductName(product.getProductName());
        productResponse.setSku(product.getSku());
        productResponse.setDescription(product.getDescription());
        productResponse.setImageUrl(product.getImage());
        productResponse.setCategoryId(product.getCategory().getCategoryId());
        productResponse.setBrandId(product.getBrand().getId());
        productResponse.setStatus(product.getStatus());
        productResponse.setCreatedAt(product.getCreatedAt());
        return productResponse;
    }

    private Product convertToEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setSku(productRequest.getSku());
        product.setDescription(productRequest.getDescription());
        product.setImage(uploadService.uploadFileToServer(productRequest.getImage()));
        product.setCategory(categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category not found with id " + productRequest.getCategoryId())));
        product.setBrand(brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new NoSuchElementException("Brand not found with id " + productRequest.getBrandId())));
        product.setStatus(productRequest.getStatus());
        product.setCreatedAt(productRequest.getCreatedAt());
        product.setUpdatedAt(new Date());
        return product;
    }

}
