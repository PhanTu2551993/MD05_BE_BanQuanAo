package ra.project_md05.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ra.project_md05.repository.IUserRepository;


@Component
@RequiredArgsConstructor
public class HandleEmailExist implements ConstraintValidator<EmailExist,String> {
	private final IUserRepository userRepository;
	@Override
	public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
		return email != null &&!userRepository.existsByEmail(email);
	}
}
