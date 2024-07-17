package ra.project_md05.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.project_md05.validation.EmailExist;
import ra.project_md05.validation.PhoneExist;
import ra.project_md05.validation.UserNameExist;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FormRegister {
	@UserNameExist(message = "Tên đăng nhập đã tồn tại")
	@NotEmpty(message = "username must be not empty")
	@NotBlank(message = "username must be not blank")
	private String username;
	@NotEmpty(message = "username must be not empty")
	@NotBlank(message = "username must be not blank")
	private String fullName;
	@PhoneExist(message = "Số điện thoại đã tồn tại")
	@NotEmpty(message = "username must be not empty")
	@NotBlank(message = "username must be not blank")
	private String phone;
	@Email(message = "Email không đúng định dạng")
	@EmailExist(message = "Email đã tồn tại")
	@NotEmpty(message = "username must be not empty")
	@NotBlank(message = "username must be not blank")
	private String email;
	@NotEmpty(message = "username must be not empty")
	@NotBlank(message = "username must be not blank")
	private String password;
	private Set<String> roles;
}
