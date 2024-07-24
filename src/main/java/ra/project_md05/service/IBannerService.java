package ra.project_md05.service;

import ra.project_md05.model.dto.request.BannerRequest;
import ra.project_md05.model.entity.Banner;

import java.util.List;
import java.util.Optional;

public interface IBannerService {
    List<Banner> getAllBanners();
    Optional<Banner> getBannerById(Long id);
    Banner createBanner(BannerRequest bannerRequest);
    Banner updateBanner(Long id, Banner bannerDetails);
    void deleteBanner(Long id);
}
