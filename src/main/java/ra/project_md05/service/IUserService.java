package ra.project_md05.service;

import org.springframework.data.domain.Page;
import ra.project_md05.model.dto.request.UpdateUserRequest;
import ra.project_md05.model.entity.Users;

import java.util.List;


public interface IUserService {
    Page<Users> getUserPaging(String searchName, Integer page, Integer itemPage, String orderBy, String direction);
    Page<Users> getUsers(int page, int size, String sortField, String sortDirection);
    Users getUserById(Long id);
    Users getUserByUserName(String username);
    Users updateUserStatus(Long userId);
    boolean changePassword(String oldPass, String newPass, String confirmNewPass);
    Users updateUser(UpdateUserRequest updateUserRequest);
    Users updateAvatarUser(UpdateUserRequest updateUserRequest);
    Users getCurrentLoggedInUser();
    List<Users> searchUsers(String query);
    Users updateUserRole(Long userId, String newRole);
}
