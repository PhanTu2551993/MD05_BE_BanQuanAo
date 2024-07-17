package ra.project_md05.model.dto.response.converter;


import ra.project_md05.model.dto.response.UserResponse;
import ra.project_md05.model.entity.Users;

public class UserConverter {
    public static UserResponse toUserResponse(Users users) {
        return UserResponse.builder()
                .username(users.getUsername())
                .fullName(users.getFullName())
                .email(users.getEmail())
                .phone(users.getPhone())
                .address(users.getAddress())
                .avatar(users.getAvatar())
                .updatedAt(users.getUpdatedAt())
                .createdAt(users.getCreatedAt())
                .status(users.getStatus()?"Đang hoạt động":"Đã khóa")
                .isDeleted(users.getIsDeleted()?"Hoạt động":"Xóa")
                .build();
    }
}
