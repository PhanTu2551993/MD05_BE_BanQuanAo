package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md05.model.dto.request.BannerRequest;
import ra.project_md05.model.entity.Banner;
import ra.project_md05.repository.BannerRepository;
import ra.project_md05.service.IBannerService;
import ra.project_md05.service.UploadService;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BannerServiceImpl implements IBannerService {

    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private UploadService uploadService;

    @Override
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    @Override
    public Optional<Banner> getBannerById(Long id) {
        return bannerRepository.findById(id);
    }

    @Override
    public Banner createBanner(BannerRequest bannerRequest) {
        String imageUrl = null;
        if (bannerRequest.getImage() != null && !bannerRequest.getImage().isEmpty()){
            imageUrl = uploadService.uploadFileToServer(bannerRequest.getImage());
        }

        Banner banner = new Banner();
        banner.setBannerName(bannerRequest.getBannerName());
        banner.setDescription(bannerRequest.getDescription());
        banner.setImage(imageUrl);
        banner.setCreatedAt(new Date());
        banner.setStatus(true);
        return bannerRepository.save(banner);
    }

    @Override
    public Banner updateBanner(Long id, Banner bannerDetails) {
        Banner banner = bannerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Banner not found"));
        banner.setBannerName(bannerDetails.getBannerName());
        banner.setDescription(bannerDetails.getDescription());
        banner.setImage(bannerDetails.getImage());
        banner.setStatus(bannerDetails.getStatus());
        return bannerRepository.save(banner);
    }

    @Override
    public void deleteBanner(Long id) {
        Banner banner = bannerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Banner not found"));
        bannerRepository.delete(banner);
    }
}
