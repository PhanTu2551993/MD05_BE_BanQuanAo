package ra.project_md05.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ra.project_md05.model.entity.Users;


import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long>, PagingAndSortingRepository<Users,Long> {
    Optional<Users> findUsersByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    @Query("select u from Users u where u.fullName like concat('%',:fullName,'%')")
    Page<Users> findUsersByUsernameAndSorting(String fullName, Pageable pageable);
    List<Users> findByUsernameContainingOrFullNameContainingOrEmailContainingOrPhoneContaining(String username, String fullName, String email, String phone);
}
