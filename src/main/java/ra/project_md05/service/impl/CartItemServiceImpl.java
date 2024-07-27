package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ra.project_md05.exception.CustomException;
import ra.project_md05.model.entity.ShoppingCart;
import ra.project_md05.repository.CartItemRepository;
import ra.project_md05.repository.ShoppingCartRepository;
import ra.project_md05.service.CartItemService;


    @Service
    public class CartItemServiceImpl implements CartItemService {
        @Autowired
        private CartItemRepository cartItemRepository;
        @Autowired
        private ShoppingCartRepository shoppingCartRepository;

        @Override
        public ShoppingCart updateQuantity(Long id, int quantity) throws CustomException {
            ShoppingCart cartItem = shoppingCartRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Cart item not found", HttpStatus.OK));

            cartItem.setOrderQuantity(quantity);
            return shoppingCartRepository.save(cartItem);
        }
    }

