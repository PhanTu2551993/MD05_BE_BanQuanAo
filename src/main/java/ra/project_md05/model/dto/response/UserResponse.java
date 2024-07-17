package ra.project_md05.model.dto.response;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private String username;
    private String email;
    private String fullName;
    private String status;
    private String avatar;
    private String phone;
    private String address;
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    private String isDeleted;
}
