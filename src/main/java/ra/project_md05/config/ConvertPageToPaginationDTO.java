package ra.project_md05.config;

import org.springframework.data.domain.Page;
import ra.project_md05.model.dto.PageDTO;

public class ConvertPageToPaginationDTO {
    public static <T> PageDTO<T> convertPageToPaginationDTO(Page<T> page)
    {
        PageDTO<T> paginationDTO = new PageDTO<>();
        paginationDTO.setPageSize(page.getSize());
        paginationDTO.setTotalPages(page.getTotalPages());
        paginationDTO.setPageNumber(page.getNumber()+1);
        paginationDTO.setContent(page.getContent());
        paginationDTO.setTotalElements(page.getTotalElements());
        return paginationDTO;
    }
}
