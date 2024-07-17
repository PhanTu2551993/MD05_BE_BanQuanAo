package ra.project_md05.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordRequest {
    @NotEmpty(message = "oldPass must be not empty")
    @NotBlank(message = "oldPass must be not blank")
    private String oldPass;
    @NotEmpty(message = "newPass must be not empty")
    @NotBlank(message = "newPass must be not blank")
    private String newPass;
    @NotEmpty(message = "confirmNewPass must be not empty")
    @NotBlank(message = "confirmNewPass must be not blank")
    private String confirmNewPass;
}
