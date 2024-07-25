package ra.project_md05.service;



import ra.project_md05.model.dto.request.AddressRequest;
import ra.project_md05.model.entity.Address;

import java.util.List;

public interface IAddressService {
    Address addNewAddress(AddressRequest addressRequest);
    List<Address> getUserAddresses();
    Address getAddressByAddressId(Long addressId);
    void deleteAddressById(Long addressId);
}
