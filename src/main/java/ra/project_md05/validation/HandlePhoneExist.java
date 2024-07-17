package ra.project_md05.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ra.project_md05.repository.IUserRepository;


@Component
@RequiredArgsConstructor
public class HandlePhoneExist implements ConstraintValidator<PhoneExist,String> {
	private final IUserRepository userRepository;
//	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^(03|05|07|08|09)\\d{8}$");
	@Override
	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		return !userRepository.existsByPhone(s);
//		return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
	}
}
