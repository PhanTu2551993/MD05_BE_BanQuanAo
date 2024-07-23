package ra.project_md05.service;

import org.springframework.http.ResponseEntity;
import ra.project_md05.model.entity.Brand;

import java.util.List;


public interface BrandService {
    List<Brand> getAllBrands();
}
