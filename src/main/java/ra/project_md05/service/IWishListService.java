package ra.project_md05.service;


import ra.project_md05.model.dto.request.WishListRequest;
import ra.project_md05.model.entity.WishList;

import java.util.List;

public interface IWishListService {
    WishList addWishList(WishListRequest wishListRequest);
    List<WishList> getWishList();
    void deleteWishList(Long wishListId);
}
