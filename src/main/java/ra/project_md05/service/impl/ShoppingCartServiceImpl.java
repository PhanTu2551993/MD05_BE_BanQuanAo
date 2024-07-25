package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md05.model.entity.Product;
import ra.project_md05.model.entity.ShoppingCart;
import ra.project_md05.model.entity.Users;
import ra.project_md05.repository.IUserRepository;
import ra.project_md05.repository.ProductRepository;
import ra.project_md05.repository.ShoppingCartRepository;
import ra.project_md05.service.IUserService;
import ra.project_md05.service.ShoppingCartService;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private IUserService userService;

    @Override
    public List<ShoppingCart> findByUser(Users user) {
        return shoppingCartRepository.findByUser(user);
    }

    @Override
    public ShoppingCart findById(Long id) {
        return shoppingCartRepository.findById(id).orElseThrow(() -> new RuntimeException("shopping cart not found"));
    }

    @Override
    public void saveShoppingCart(Long userId, Long productId) {
//        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Users user = userService.getCurrentLoggedInUser() ;

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found"));
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUser(user);
        boolean check = false; //mac dinh la chua co san pham
        for (ShoppingCart cart : shoppingCarts) {
            if (cart.getProduct().equals(product)) {
                int orderQuantity = cart.getOrderQuantity();
                cart.setOrderQuantity(orderQuantity + 1);
                shoppingCartRepository.save(cart);
                check = true; //sp co trong gio hang
                break;
            }
        }
        if (!check) { // sp chua co trong gio hang
            ShoppingCart cart = new ShoppingCart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setOrderQuantity(1);
            shoppingCartRepository.save(cart);
        }

    }

    @Override
    public void changeQuantity(Long userId, Long cartItemId, Integer quantity) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        ShoppingCart cartItems = shoppingCartRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart Item Not Found"));
        Product product = cartItems.getProduct();
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUser(user);

        boolean check = false;
        for (ShoppingCart cart : shoppingCarts) {
            if (cart.getProduct().equals(product)) {
                int orderQuantity = cart.getOrderQuantity();
                if (quantity <= product.getStock()) {
                    cart.setOrderQuantity(quantity + cart.getOrderQuantity());
                    shoppingCartRepository.save(cart);
                    check = true;
                } else {
                    throw new RuntimeException("Requested quantity exceeds available stock");
                }

            }
        }
        if (!check) {
            ShoppingCart cart = new ShoppingCart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setOrderQuantity(quantity);
            shoppingCartRepository.save(cart);
        }
    }

    @Override
    public void deleteShoppingCart(Long userId, Long productId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product Not Found"));
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUser(user);
        boolean check = false;
        for (ShoppingCart cart : shoppingCarts) {
            if (cart.getProduct().equals(product)) {
                shoppingCartRepository.delete(cart);
            }
        }
    }

    @Override
    public void deleteShoppingCart(Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUser(user);
        boolean check = false;
        for (ShoppingCart cart : shoppingCarts) {
            if (cart.getUser().equals(user)) {
                shoppingCartRepository.delete(cart);
            }
        }
    }
}
