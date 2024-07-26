package ra.project_md05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_md05.model.entity.Users;
import ra.project_md05.model.entity.WishList;


import java.util.List;
import java.util.Optional;

@Repository
public interface IWishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findAllByUser(Users users);
    Optional<WishList> findByWishListIdAndUser(Long wishListId, Users users);
}
