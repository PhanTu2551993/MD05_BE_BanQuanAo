package ra.project_md05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_md05.model.entity.Coupons;

import java.util.Optional;

@Repository
public interface CouponsRepository extends JpaRepository<Coupons, Long> {

    Optional<Coupons> findByCodeAndStatusIsTrue(String code);
}
