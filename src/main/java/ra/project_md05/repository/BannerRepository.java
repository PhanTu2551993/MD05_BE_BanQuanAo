package ra.project_md05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_md05.model.entity.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Long> {
}
