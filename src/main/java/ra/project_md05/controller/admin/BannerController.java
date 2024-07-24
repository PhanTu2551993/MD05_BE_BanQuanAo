package ra.project_md05.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project_md05.exception.DataExistException;
import ra.project_md05.model.dto.request.BannerRequest;
import ra.project_md05.model.dto.request.CategoryRequest;
import ra.project_md05.model.dto.response.ResponseDtoSuccess;
import ra.project_md05.model.entity.Banner;
import ra.project_md05.model.entity.Category;
import ra.project_md05.service.IBannerService;

@RestController
@RequestMapping("/api/v1/admin")
public class BannerController {
    @Autowired
    private IBannerService bannerService;

    @GetMapping("/banners")
    public ResponseEntity<?> getBanner() {
        return new ResponseEntity<>( bannerService.getAllBanners(), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Banner> getBannerById(@PathVariable Long id) {
//        Banner banner = bannerService.getBannerById(id).orElseThrow(() -> new ResourceNotFoundException("Banner not found"));
//        return ResponseEntity.ok().body(banner);
//    }
//
    @PostMapping("/banners")
    public ResponseEntity<?> createBanner(@ModelAttribute BannerRequest bannerRequest) {
    Banner banner = bannerService.createBanner(bannerRequest);
    return new ResponseEntity<>(new ResponseDtoSuccess<>(banner, HttpStatus.CREATED), HttpStatus.CREATED);
}
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Banner> updateBanner(@PathVariable Long id, @RequestBody Banner bannerDetails) {
//        Banner updatedBanner = bannerService.updateBanner(id, bannerDetails);
//        return ResponseEntity.ok(updatedBanner);
//    }
//
    @DeleteMapping("/banners/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return ResponseEntity.noContent().build();
    }
}
