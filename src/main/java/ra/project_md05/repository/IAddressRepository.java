package ra.project_md05.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project_md05.model.entity.Address;
import ra.project_md05.model.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUsers(Users users);
    Optional<Address> findByAddressIdAndUsers(Long addressId, Users users);
}
