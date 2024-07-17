package ra.project_md05.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FormLogin {

	@NotBlank(message = "username must be not blank")
	private String username;

	@NotEmpty(message = "password must be not empty")
	@NotBlank(message = "password must be not blank")
	private String password;
	
}
