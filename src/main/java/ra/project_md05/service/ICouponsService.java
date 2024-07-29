package ra.project_md05.service;

import org.springframework.stereotype.Service;
import ra.project_md05.model.dto.request.CouponsRequest;
import ra.project_md05.model.entity.Coupons;

import java.util.List;
import java.util.Optional;


public interface ICouponsService {
    List<Coupons> getAllCoupons();
    Coupons createCoupons (CouponsRequest couponsRequest);
    void deleteCoupons (Long id);
    Optional<Coupons> validateCoupon(String code);
}
