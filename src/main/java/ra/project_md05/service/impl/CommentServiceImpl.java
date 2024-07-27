package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md05.model.dto.request.CommentsRequest;
import ra.project_md05.model.entity.Comment;
import ra.project_md05.model.entity.Product;
import ra.project_md05.model.entity.Users;
import ra.project_md05.model.entity.WishList;
import ra.project_md05.repository.CommentRepository;
import ra.project_md05.repository.ProductRepository;
import ra.project_md05.service.ICommentService;
import ra.project_md05.service.IUserService;
import ra.project_md05.service.ProductService;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private ProductService  productService;

    public Comment addComment(CommentsRequest commentsRequest) {
        Users currentUser = userService.getCurrentLoggedInUser();
        if (currentUser == null) {
            throw new RuntimeException("Người dùng chưa đăng nhập");
        }
        Product product = productService.getProductById(commentsRequest.getProductId());

        Comment comment = Comment.builder()
                .user(currentUser)
                .product(product)
                .createdAt(new Date())
                .comment(commentsRequest.getComment())
                .status(false)
                .build();

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByProduct(Long productId) {
        return commentRepository.findByProductProductId(productId);
    }
}
