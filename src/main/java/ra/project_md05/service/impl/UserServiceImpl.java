package ra.project_md05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.project_md05.constants.RoleName;
import ra.project_md05.model.dto.request.UpdateUserRequest;
import ra.project_md05.model.entity.Roles;
import ra.project_md05.model.entity.Users;
import ra.project_md05.repository.IRoleRepository;
import ra.project_md05.repository.IUserRepository;
import ra.project_md05.service.IRoleService;
import ra.project_md05.service.IUserService;


import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    IRoleRepository roleRepository;
    @Autowired
    private IRoleService roleService;
//    @Autowired
//    IAddressRepository addressRepository;
//    @Autowired
//    IShoppingCartRepository shoppingCartRepository;
//    @Autowired
//    IProductRepository productRepository;
//    @Autowired
//    IWishListRepository wishListRepository;

    @Override
    public Page<Users> getUserPaging(String searchName, Integer page, Integer itemPage, String orderBy, String direction) {
        Pageable pageable = null;
        if(orderBy!=null && !orderBy.isEmpty()){
            // co sap xep
            Sort sort = null;
            switch (direction){
                case "ASC":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "DESC":
                    sort = Sort.by(orderBy).descending();
                    break;
            }
            pageable = PageRequest.of(page, itemPage,sort);
        }else{
            pageable = PageRequest.of(page, itemPage);
        }

        if(searchName!=null && !searchName.isEmpty()){
            return userRepository.findUsersByUsernameAndSorting(searchName,pageable);
        }else{
            return userRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Users> getUsers(int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(sortField);
        sort = sortDirection.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }

    @Override
    public Users getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new NoSuchElementException("Không tồn tại người dùng"));
    }

    @Override
    public Users getUserByUserName(String username) {
        return userRepository.findUsersByUsername(username).orElseThrow(()->new NoSuchElementException("Không tồn tại người dùng"));
    }

    @Override
    public Users updateUserStatus(Long userId) {
        Users user = getUserById(userId);
        user.setStatus(!user.getStatus());
        return userRepository.save(user);
    }

    @Override
    public boolean changePassword(String oldPass, String newPass, String confirmNewPass) {
        Users currentUser = getCurrentLoggedInUser();

        if (!passwordEncoder.matches(oldPass, currentUser.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu cũ không đúng !");
        }
        if (!newPass.equals(confirmNewPass)) {
            throw new IllegalArgumentException("Nhập lại mật khẩu không chính xác !");
        }

        currentUser.setPassword(passwordEncoder.encode(newPass));
        currentUser.setUpdatedAt(new Date());
        userRepository.save(currentUser);
        return true;
    }

    @Override
    public Users updateUser(UpdateUserRequest updateUserRequest) {
        Users updateUser = getCurrentLoggedInUser();

        if (updateUserRequest.getFullName() != null) {
            updateUser.setFullName(updateUserRequest.getFullName());
        }
        if (updateUserRequest.getEmail() != null) {
            updateUser.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getPhone() != null) {
            updateUser.setPhone(updateUserRequest.getPhone());
        }
        if (updateUserRequest.getAddress() != null) {
            updateUser.setAddress(updateUserRequest.getAddress());
        }
        if (updateUserRequest.getAvatar() != null) {
            updateUser.setAvatar(updateUserRequest.getAvatar());
        }
        updateUser.setUpdatedAt(new Date());
        return userRepository.save(updateUser);
    }

    @Override
    public Users getCurrentLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findUsersByUsername(username).orElseThrow(() -> new NoSuchElementException("Không có người dùng"));
    }

    @Override
    public List<Users> searchUsers(String query) {
        return userRepository.findByUsernameContainingOrFullNameContainingOrEmailContainingOrPhoneContaining(query, query, query, query);
    }

    @Override
    public Users updateUserRole(Long userId, String newRole) {
        // Tìm người dùng theo userId
        Users user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null; // Người dùng không tồn tại
        }
        // Xóa tất cả các vai trò hiện tại của người dùng
        user.getRoles().clear();
        // Tìm vai trò mới từ RoleName
        RoleName roleName;
        try {
            roleName = RoleName.valueOf(newRole.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + newRole);
        }
        // Thêm vai trò mới cho người dùng
        Roles newRoles = roleService.findByRoleName(roleName);
        user.getRoles().add(newRoles);

        // Cập nhật người dùng trong cơ sở dữ liệu
        return userRepository.save(user);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

}
