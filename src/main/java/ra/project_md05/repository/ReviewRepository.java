package ra.project_md05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_md05.model.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
