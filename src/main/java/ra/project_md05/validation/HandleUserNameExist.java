package ra.project_md05.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ra.project_md05.repository.IUserRepository;


@Component
@RequiredArgsConstructor
public class HandleUserNameExist implements ConstraintValidator<UserNameExist,String> {
	private final IUserRepository userRepository;
	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		return !userRepository.existsByUsername(s);
	}
}
