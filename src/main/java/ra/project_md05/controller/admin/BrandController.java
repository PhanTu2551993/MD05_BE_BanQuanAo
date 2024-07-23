package ra.project_md05.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.project_md05.service.BrandService;

@RestController
@RequestMapping("/api/v1/admin")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @GetMapping("/brands")
    public ResponseEntity<?> getBrand() {
        return new ResponseEntity<>( brandService.getAllBrands(), HttpStatus.OK);
    }
}
