package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md05.model.dto.request.CouponsRequest;
import ra.project_md05.model.entity.Coupons;
import ra.project_md05.repository.CouponsRepository;
import ra.project_md05.service.ICouponsService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CouponsServiceImpl implements ICouponsService {
    @Autowired
    private CouponsRepository couponsRepository;

    @Override
    public List<Coupons> getAllCoupons() {
        return couponsRepository.findAll();
    }

    @Override
    public Coupons createCoupons(CouponsRequest couponsRequest) {
        Coupons coupons = new Coupons();
        coupons.setCode(couponsRequest.getCode());
        coupons.setDiscount(couponsRequest.getDiscount());
        coupons.setQuantity(couponsRequest.getQuantity());
        coupons.setStatus(true);
        coupons.setEndDate(couponsRequest.getEndDate());
        coupons.setStartDate(couponsRequest.getStartDate());
        coupons.setTitle(couponsRequest.getTitle());
        return couponsRepository.save(coupons);
    }

    @Override
    public void deleteCoupons(Long id) {
        Coupons coupons = couponsRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Coupons not found"));
        couponsRepository.delete(coupons);
    }

    @Override
    public Optional<Coupons> validateCoupon(String code) {
        return couponsRepository.findByCodeAndStatusIsTrue(code);
    }
}
