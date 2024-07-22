package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.project_md05.exception.DataExistException;
import ra.project_md05.exception.DataNotFoundException;
import ra.project_md05.model.dto.request.CategoryRequest;
import ra.project_md05.model.entity.Category;
import ra.project_md05.repository.CategoryRepository;
import ra.project_md05.service.CategoryService;
import ra.project_md05.service.UploadService;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UploadService uploadService;

    @Override
    public Category findById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return categoryOptional.orElseThrow(() -> new NoSuchElementException("Không tồn tại Category có Id này"));
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }


    @Override
    public void deleteById(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tồn tại danh mục"));
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean existsByCategoryName(String categoryName) {
        return categoryRepository.existsByCategoryName(categoryName);
    }


    @Override
    public Category update(CategoryRequest categoryRequest, Long categoryId) throws DataExistException {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("Category not found"));

        if (categoryRequest.getCategoryName() != null) {
            category.setCategoryName(categoryRequest.getCategoryName());
        }

        if (categoryRequest.getDescription() != null) {
            category.setDescription(categoryRequest.getDescription());
        }

        if (categoryRequest.getImage() != null && !categoryRequest.getImage().isEmpty()) {
            String imageUrl = uploadService.uploadFileToServer(categoryRequest.getImage());
            category.setImage(imageUrl);
        }

        if (categoryRequest.getStatus() != null) {
            category.setStatus(categoryRequest.getStatus());
        }

        return categoryRepository.save(category);

    }

    @Override
    public Category save(CategoryRequest categoryRequest) throws DataExistException {
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            throw new DataExistException("Tên Danh mục đã tồn tại", "categoryName");
        }
        String imageUrl = null;
        if (categoryRequest.getImage() != null && !categoryRequest.getImage().isEmpty()){
            imageUrl = uploadService.uploadFileToServer(categoryRequest.getImage());

        }
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setDescription(categoryRequest.getDescription());
        category.setImage(imageUrl);
        category.setStatus(true);
        return categoryRepository.save(category);
    }
}
