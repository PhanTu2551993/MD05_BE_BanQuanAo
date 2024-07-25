package ra.project_md05.service;

import ra.project_md05.model.entity.ShoppingCart;
import ra.project_md05.model.entity.Users;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCart> findByUser(Users user);
    ShoppingCart findById(Long id);
    void saveShoppingCart(Long userId, Long productId);
    void changeQuantity(Long userId, Long cartItemId, Integer quantity);
    void deleteShoppingCart(Long userId, Long productId);
    void deleteShoppingCart(Long userId);
}
