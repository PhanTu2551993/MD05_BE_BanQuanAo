package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project_md05.model.dto.request.AddressRequest;
import ra.project_md05.model.entity.Address;
import ra.project_md05.model.entity.Users;
import ra.project_md05.repository.IAddressRepository;
import ra.project_md05.service.IAddressService;
import ra.project_md05.service.IUserService;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private IAddressRepository addressRepository;
    @Autowired
    private IUserService userService;

    @Override
    public Address addNewAddress(AddressRequest addressRequest) {
        Users currentUser = userService.getCurrentLoggedInUser();
        Address newAddress = Address.builder()
                .users(currentUser)
                .district(addressRequest.getDistrict())
                .streetAddress(addressRequest.getStreetAddress())
                .ward(addressRequest.getWard())
                .province(addressRequest.getProvince())
                .priority(false)
                .phone(addressRequest.getPhone())
                .receiveName(addressRequest.getReceiveName())
                .build();
        return addressRepository.save(newAddress);
    }

    @Override
    public List<Address> getUserAddresses() {
        Users currentUser = userService.getCurrentLoggedInUser();
        List<Address> addresses = addressRepository.findByUsers(currentUser);
        return addresses;
    }

    @Override
    public Address getAddressByAddressId(Long addressId) {
        Users currentUser = userService.getCurrentLoggedInUser();
        Address address = addressRepository.findByAddressIdAndUsers(addressId, currentUser)
                .orElseThrow(() -> new NoSuchElementException("Không tồn tại địa chỉ có ID: " + addressId));
        return address;
    }


    @Override
    public void deleteAddressById(Long addressId) {
        Users currentUser = userService.getCurrentLoggedInUser();
        Address address = addressRepository.findByAddressIdAndUsers(addressId, currentUser)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy địa chỉ"));

        addressRepository.delete(address);
    }
}
