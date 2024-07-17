package ra.project_md05.model.dto.request;


import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserRequest {
    private String fullName;
    private String email;
    private String phone;
    @NotEmpty(message = "address must be not empty")
    @NotBlank(message = "address must be not blank")
    private String address;
    @NotEmpty(message = "avatar must be not empty")
    @NotBlank(message = "avatar must be not blank")
    private String avatar;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
}
