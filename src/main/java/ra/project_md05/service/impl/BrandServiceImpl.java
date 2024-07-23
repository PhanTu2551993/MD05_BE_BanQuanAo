package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md05.model.entity.Brand;
import ra.project_md05.repository.BrandRepository;
import ra.project_md05.service.BrandService;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;


    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
