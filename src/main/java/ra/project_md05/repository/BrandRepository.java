package ra.project_md05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.project_md05.model.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
