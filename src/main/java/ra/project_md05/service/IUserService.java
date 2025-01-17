package ra.project_md05.service;

import org.springframework.data.domain.Page;
import ra.project_md05.model.dto.request.UpdateUserRequest;
import ra.project_md05.model.entity.Users;

import java.util.List;


public interface IUserService {
    Page<Users> getUserPaging(String searchName, Integer page, Integer itemPage, String orderBy, String direction);
    Page<Users> getAllUser(Integer page, Integer itemPage, String orderBy, String direction);
    Users getUserById(Long id);
    Users getUserByUserName(String username);
    Users updateUserStatus(Long userId, Boolean status);
    boolean changePassword(String oldPass, String newPass, String confirmNewPass);
    Users updateUser(UpdateUserRequest updateUserRequest);
    Users getCurrentLoggedInUser();
    List<Users> findByUsernameContainingIgnoreCase(String username);
}
