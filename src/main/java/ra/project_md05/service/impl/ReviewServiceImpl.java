package ra.project_md05.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md05.model.entity.Review;
import ra.project_md05.repository.ReviewRepository;
import ra.project_md05.service.ReviewService;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}
