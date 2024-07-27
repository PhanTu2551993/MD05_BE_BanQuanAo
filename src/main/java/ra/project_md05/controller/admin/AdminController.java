package ra.project_md05.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md05.exception.DataExistException;
import ra.project_md05.model.dto.PageDTO;
import ra.project_md05.model.dto.request.CategoryRequest;
import ra.project_md05.model.dto.request.FormChangeOrderStatus;
import ra.project_md05.model.dto.request.ProductDetailRequest;
import ra.project_md05.model.dto.request.ProductRequest;
import ra.project_md05.model.dto.response.*;
import ra.project_md05.model.dto.response.converter.UserConverter;
import ra.project_md05.model.entity.Category;
import ra.project_md05.model.entity.Product;
import ra.project_md05.model.entity.ProductDetail;
import ra.project_md05.model.entity.Users;
import ra.project_md05.service.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "2") int size,
                                      @RequestParam(defaultValue = "userId") String sortField,
                                      @RequestParam(defaultValue = "asc") String sortDirection) {

        Page<Users> users = userService.getUsers(page, size, sortField, sortDirection);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //lay ve danh sach cac quyen-
    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);

    }
    // phân quyền ngươ dùng
    @PutMapping("/{userId}/role")
    public ResponseEntity<Users> updateUserRole(@PathVariable Long userId, @RequestBody Map<String, String> roleRequest) {
        String newRole = roleRequest.get("role");
        Users updatedUser = userService.updateUserRole(userId, newRole);
        return ResponseEntity.ok(updatedUser);
    }

    //search user theo tên
    @GetMapping("/users/search")
    public ResponseEntity<?> getUsersByName(@RequestParam String query) {
        return new ResponseEntity<>(userService.searchUsers(query), HttpStatus.OK);
    }

    //khoa mo tai khoan nguoi dung
    @PatchMapping("/users/{userId}")
    public ResponseEntity<UserResponse> changeUserStatus(@PathVariable Long userId) {
        Users changeUserStatus = userService.updateUserStatus(userId);
        return new ResponseEntity<>(UserConverter.toUserResponse(changeUserStatus), HttpStatus.OK);
    }

    // API: Lấy về danh sách tất cả danh mục (sắp xếp và phân trang)
    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategory(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "2") int size,
                                            @RequestParam(defaultValue = "categoryId") String sortBy,
                                            @RequestParam(defaultValue = "asc") String sortDir) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Category> categoryPage = categoryService.findAll(pageable);
        return new ResponseEntity<>(new ResponseDtoSuccess<>(categoryPage, HttpStatus.OK), HttpStatus.OK);
    }

    //API them moi danh muc
    @PostMapping("/categories")
    public ResponseEntity<?> addNewCategory(@ModelAttribute CategoryRequest categoryRequest) throws DataExistException {
        Category category = categoryService.save(categoryRequest);
        return new ResponseEntity<>(new ResponseDtoSuccess<>(category, HttpStatus.CREATED), HttpStatus.CREATED);
    }

    //API cap nhat category
    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @ModelAttribute CategoryRequest categoryRequest) throws DataExistException {
        Category category = categoryService.update(categoryRequest, id);
        return new ResponseEntity<>(new ResponseDtoSuccess<>(category, HttpStatus.OK), HttpStatus.OK);

    }

    //API delete category
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Category deleted successfully", HttpStatus.OK), HttpStatus.OK);
    }


    //API hien thi tat ca san pham
    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "productId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Page<ProductResponse> products = productService.getAllProducts(page, size, sortBy, sortDir);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    //API them moi san pham
    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductRequest productRequest) {
        ProductResponse product = productService.createProduct(productRequest);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    //API edit sp
    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @ModelAttribute ProductRequest productRequest) {
        ProductResponse product = productService.updateProduct(id, productRequest);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    //API xoa sp
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/search")
    public ResponseEntity<?> search(
            @RequestParam(name = "search") String search,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "3") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> productPage = productService.findByNameOrDescriptionContaining(search, pageable);

        if (productPage.isEmpty()) {
            return new ResponseEntity<>("Không tìm thấy Sản phẩm có tên: " + search, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    // Chuyển đổi đối tượng Product
    private ResponseEntity<?> getResponseEntity(List<Product> productList) {
        List<ProductResponse> productResponses = productList.stream()
                .map(this::convertToProductResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productResponses, HttpStatus.OK), HttpStatus.OK);
    }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getProductId())
                .sku(product.getSku())
                .productName(product.getProductName())
                .description(product.getDescription())
                .imageUrl(product.getImage())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryId(product.getCategory().getCategoryId())
                .brandId(product.getBrand().getId())
                .createdAt(product.getUpdatedAt())
                .build();
    }




    // API: Danh sách Sản phẩm theo Danh Mục
    @GetMapping("/products/categories/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> productList = productService.findByCategory(categoryService.findById(categoryId));
        return getResponseEntity(productList);
    }
    // API: Danh sách tất cả đơn hàng: - /api.myservice.com/v1/admin/orders
    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrder(Pageable pageable) {
        Page<OrderResponseRoleAdmin> orderPage = orderService.getAllOrderRoleAdmin(pageable);
        List<OrderResponseRoleAdmin> orderList = orderPage.getContent();
        return new ResponseEntity<>(new ResponseDtoSuccess<>(orderList, HttpStatus.OK), HttpStatus.OK);
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<ResponseDtoSuccess<OrderResponseRoleAdmin>> changeOrderStatus(
            @PathVariable Long orderId,
            @RequestBody FormChangeOrderStatus formChangeOrderStatus) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>( orderService.updateOrderStatusById(orderId, formChangeOrderStatus.getOrderStatusName()), HttpStatus.OK), HttpStatus.OK);
    }


}
