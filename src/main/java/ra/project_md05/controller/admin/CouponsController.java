package ra.project_md05.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md05.model.dto.request.CouponsRequest;
import ra.project_md05.model.entity.Coupons;
import ra.project_md05.service.ICouponsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class CouponsController {
    @Autowired
    private ICouponsService couponsService;

    @GetMapping("/coupons")
    public ResponseEntity<List<Coupons>> getAllCoupons() {
        return new ResponseEntity<>(couponsService.getAllCoupons(), HttpStatus.OK);
    }

    @PostMapping("/coupons")
    public ResponseEntity<Coupons> addCoupon(@RequestBody CouponsRequest couponsRequest) {
        Coupons coupons = couponsService.createCoupons(couponsRequest);
        return new ResponseEntity<>(coupons, HttpStatus.CREATED);
    }

    @DeleteMapping("/coupons/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id) {
        couponsService.deleteCoupons(id);
        return ResponseEntity.noContent().build();
    }
}
