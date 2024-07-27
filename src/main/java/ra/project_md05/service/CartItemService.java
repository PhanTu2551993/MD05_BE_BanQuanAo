package ra.project_md05.service;

import ra.project_md05.exception.CustomException;
import ra.project_md05.model.entity.CartItem;
import ra.project_md05.model.entity.ShoppingCart;

public interface CartItemService {
    ShoppingCart updateQuantity(Long id, int quantity) throws CustomException;
}
