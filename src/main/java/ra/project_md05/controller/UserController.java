package ra.project_md05.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ra.project_md05.exception.CustomException;
import ra.project_md05.model.dto.request.*;
import ra.project_md05.model.dto.response.ProductResponse;
import ra.project_md05.model.dto.response.ResponseDtoSuccess;
import ra.project_md05.model.dto.response.ShoppingCartResponse;
import ra.project_md05.model.dto.response.UserResponse;
import ra.project_md05.model.dto.response.converter.UserConverter;
import ra.project_md05.model.entity.*;
import ra.project_md05.security.principal.UserDetailCustom;
import ra.project_md05.model.entity.Address;
import ra.project_md05.model.entity.Category;
import ra.project_md05.model.entity.Product;
import ra.project_md05.model.entity.Users;
import ra.project_md05.security.principal.UserDetailCustom;
import ra.project_md05.service.*;
import ra.project_md05.service.ShoppingCartService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
//    @Autowired
//    private IAddressService addressService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private IAddressService addressService;
//    @Autowired
//    private IShoppingCartService shoppingCartService;
    @Autowired
    private IWishListService wishListService;
//    @Autowired
//    private IOrderService orderService;

    // API: Danh sách Sản phẩm mới: Lấy ra 10 Sản phẩm được thêm gần đây nhất
    @GetMapping("/products/new-products")
    public ResponseEntity<?> getNewProducts() {
        List<Product> productList = productService.findFirst10ByOrderByCreatedAtDesc();
        return getResponseEntity(productList);
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

    // API: Danh sách Sản phẩm theo Danh Mục
    @GetMapping("/products/categories/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> productList = productService.findByCategory(categoryService.findById(categoryId));
        return getResponseEntity(productList);
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



    @PutMapping("/account/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) throws CustomException {
        boolean result = userService.changePassword(changePasswordRequest.getOldPass(), changePasswordRequest.getNewPass(), changePasswordRequest.getConfirmNewPass());
        if (result) {
            return ResponseEntity.ok("Đổi mật khẩu thành công !!");
        } else {
            return ResponseEntity.badRequest().body("Thay đổi mật khẩu thất bại");
        }
    }

    @PatchMapping("/account")
    public ResponseEntity<UserResponse> updateUser( @ModelAttribute UpdateUserRequest updateUserRequest) {
        Users updatedUser = userService.updateUser(updateUserRequest);
        UserResponse userResponses = UserConverter.toUserResponse(updatedUser);
        return ResponseEntity.ok(userResponses);
    }

    @PatchMapping("/avatar")
    public ResponseEntity<UserResponse> updateAvatarUser( @ModelAttribute UpdateUserRequest updateUserRequest) {
        Users updatedUser = userService.updateAvatarUser(updateUserRequest);
        UserResponse userResponses = UserConverter.toUserResponse(updatedUser);
        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/account")
    public ResponseEntity<UserResponse> getInfoUser() {
        Users infoUser = userService.getCurrentLoggedInUser();
        UserResponse userResponses = UserConverter.toUserResponse(infoUser);
        return ResponseEntity.ok(userResponses);
    }

    @PostMapping("/account/addresses")
    public ResponseEntity<Address> addAddress(@Valid @RequestBody AddressRequest addressRequest) {
        Address newAddress = addressService.addNewAddress(addressRequest);
        return new ResponseEntity<>(newAddress, HttpStatus.OK);
    }
    //hien thi san pham trong gio hang
    @GetMapping("/cart/list")
    public ResponseEntity<?> getCartList(@AuthenticationPrincipal UserDetailCustom userDetail) {
        Users user = userService.getCurrentLoggedInUser();
        List<ShoppingCart> cartList = shoppingCartService.findByUser(user);
        List<ShoppingCartResponse> cartResponses = cartList.stream().map(cart ->
                new ShoppingCartResponse(
                        cart.getProduct().getProductId(),
                        cart.getProduct().getProductName(),
                        cart.getProduct().getImage(),
                        cart.getProduct().getPrice(),
                        cart.getOrderQuantity()
                )
        ).collect(Collectors.toList());
        return new ResponseEntity<>(new ResponseDtoSuccess<>(cartResponses, HttpStatus.OK), HttpStatus.OK);

    }


    //them san pham vao gio hang
    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@AuthenticationPrincipal UserDetailCustom userDetails, @RequestBody FormAddToCartRequest formAddToCartRequest) {
        shoppingCartService.saveShoppingCart(userDetails.getUserId(), formAddToCartRequest.getProductId());
        Users user = userService.getUserById(userDetails.getUserId());
        List<ShoppingCart> shoppingCartList = shoppingCartService.findByUser(user);

        List<ShoppingCartResponse> cartResponses = shoppingCartList.stream().map(cart ->
                new ShoppingCartResponse(
                        cart.getProduct().getProductId(),
                        cart.getProduct().getProductName(),
                        cart.getProduct().getImage(),
                        cart.getProduct().getPrice(),
                        cart.getOrderQuantity()
                )
        ).collect(Collectors.toList());

        return new ResponseEntity<>(new ResponseDtoSuccess<>(cartResponses, HttpStatus.OK), HttpStatus.OK);
    }

//    @PostMapping("/account/addresses")
//    public ResponseEntity<AddressResponse> addNewAddress(@Valid @RequestBody AddressRequest addressRequest) {
//        Address newAddress = addressService.addNewAddress(addressRequest);
//        AddressResponse addressResponse = AddressConverter.toAddressResponse(newAddress);
//        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
//    }
//
    @GetMapping("/account/addresses")
    public ResponseEntity<List<Address>> getUserAddresses() {
        List<Address> addresses = addressService.getUserAddresses();
        return ResponseEntity.ok(addresses);

    }
//
//    @GetMapping("/account/addresses/{addressId}")
//    public ResponseEntity<AddressResponse> getAddressByAddressId(@PathVariable Long addressId) {
//        AddressResponse addressResponse = addressService.getAddressByAddressId(addressId);
//        return ResponseEntity.ok(addressResponse);
//    }
//
    @DeleteMapping("/account/addresses/{addressId}")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long addressId) {
        addressService.deleteAddressById(addressId);
        return ResponseEntity.ok().body("đã xóa thành công địa chỉ có ID : " + addressId);
    }
//
//    @PostMapping("/cart/add")
//    public ResponseEntity<ShoppingCartResponse> addToCart(@RequestBody AddToCartRequest addToCartRequest) {
//        ShoppingCart shoppingCart = shoppingCartService.addToCart(addToCartRequest);
//        ShoppingCartResponse shoppingCartResponse = ShoppingCartConverter.convertToResponse(shoppingCart);
//        return ResponseEntity.ok(shoppingCartResponse);
//    }
//
//    @GetMapping("/cart/list")
//    public ResponseEntity<List<ShoppingCartResponse>> getUserShoppingCarts() {
//        List<ShoppingCartResponse> shoppingCarts = shoppingCartService.getShoppingCart();
//        return ResponseEntity.ok(shoppingCarts);
//
//    }
//
//    @DeleteMapping("/cart/items/{cartItemId}")
//    public ResponseEntity<?> removeFromCart(@PathVariable Long cartItemId) {
//        shoppingCartService.removeFromCart(cartItemId);
//        return ResponseEntity.ok().body("đã xóa thành công sản phẩm trong giỏ hàng có ID : " + cartItemId);
//    }
//
//    @DeleteMapping("/cart/clear")
//    public ResponseEntity<?> removeAllFromCart() {
//        shoppingCartService.removeAllFromCart();
//        return ResponseEntity.ok().body("đã xóa thành công tất cả sản phẩm trong giỏ hàng");
//    }
//
//    @PutMapping("/cart/items/{cartItemId}")
//    public ResponseEntity<ShoppingCartResponse> updateCartItemQuantity(@PathVariable Long cartItemId,
//                                                                       @RequestParam Integer quantity) {
//        ShoppingCart updatedCart = shoppingCartService.updateCartItemQuantity(cartItemId, quantity);
//        ShoppingCartResponse response = ShoppingCartConverter.convertToResponse(updatedCart);
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/cart/checkout")
//    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
//        Order order = orderService.placeOrder(orderRequest);
//        OrderResponse orderResponse = OrderConverter.toOrderResponse(order);
//        return ResponseEntity.ok(orderResponse);
//    }
//
    @PostMapping("/wish-list")
    public ResponseEntity<WishList> addWishList(@RequestBody WishListRequest wishListRequest) {
        WishList addedWishList = wishListService.addWishList(wishListRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedWishList);
    }

    @GetMapping("/wish-list")
    public ResponseEntity<List<WishList>> getUserWishList() {
        List<WishList> wishListResponseList = wishListService.getWishList();
        return ResponseEntity.ok(wishListResponseList);

    }

    @DeleteMapping("/wish-list/{wishListId}")
    public ResponseEntity<?> deleteWishList(@PathVariable Long wishListId) {
        wishListService.deleteWishList(wishListId);
        return ResponseEntity.ok().body("đã xóa thành công sản phẩm trong danh sách yêu thích có ID : " + wishListId);
    }

//    @GetMapping("/history")
//    public ResponseEntity<List<OrderResponse>> getAllOrders() {
//        List<Order> orders = orderService.getAllUserOrders();
//        List<OrderResponse> orderResponseList = orders.stream()
//                .map(OrderConverter::toOrderResponse)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(orderResponseList, HttpStatus.OK);
//    }
//
//    @GetMapping("/history/{serialNumber}")
//    public ResponseEntity<OrderResponse> getHistory(@PathVariable String serialNumber) {
//        Order order = orderService.getOrderBySerialNumber(serialNumber);
//        OrderResponse orderResponse = OrderConverter.toOrderResponse(order);
//        return ResponseEntity.ok(orderResponse);
//    }
//
//    @GetMapping("/historyOrder/{orderStatus}")
//    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@PathVariable OrderStatus orderStatus) {
//        List<Order> orders = orderService.getHistoryOrdersByStatus(orderStatus);
//        List<OrderResponse> orderResponseList = orders.stream()
//                .map(OrderConverter::toOrderResponse)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(orderResponseList, HttpStatus.OK);
//    }
//
//    @PutMapping("/history/cancel/{orderId}")
//    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
//        Boolean result = orderService.cancelOrder(orderId);
//
//        if (result) {
//            return ResponseEntity.ok("Đơn hàng đã được hủy thành công!");
//        } else {
//            return ResponseEntity.badRequest().body("Không thể hủy đơn hàng. Đơn hàng không ở trạng thái chờ xác nhận.");
//        }
//    }


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
                .categoryId(product.getCategory().getCategoryId())
                .createdAt(product.getUpdatedAt())
                .build();
    }
}