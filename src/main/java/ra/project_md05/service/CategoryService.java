package ra.project_md05.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.project_md05.exception.DataExistException;
import ra.project_md05.model.dto.request.CategoryRequest;
import ra.project_md05.model.entity.Category;

public interface CategoryService {
    Category findById(Long id);

    Page<Category> findAll(Pageable pageable);



    void deleteById(Long id);

    boolean existsByCategoryName(String categoryName);

    Category update(CategoryRequest categoryRequest, Long categoryId) throws DataExistException;

    Category save(CategoryRequest categoryRequest) throws DataExistException;
}
