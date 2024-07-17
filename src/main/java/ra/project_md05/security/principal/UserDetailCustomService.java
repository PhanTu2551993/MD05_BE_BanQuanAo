package ra.project_md05.security.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.project_md05.model.entity.Users;
import ra.project_md05.repository.IUserRepository;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailCustomService implements UserDetailsService {
    private final IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = userRepository.findUsersByUsername(username);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            return UserDetailCustom.builder()
                    .userId(users.getUserId())
                    .email(users.getEmail())
                    .phone(users.getPhone())
                    .fullName(users.getFullName())
                    .password(users.getPassword())
                    .status(users.getStatus())
                    .address(users.getAddress())
                    .updatedAt(users.getUpdatedAt())
                    .username(users.getUsername())
                    .createdAt(users.getCreatedAt())
                    .isDeleted(users.getIsDeleted())
                    .avatar(users.getAvatar())
                    .authorities( users.getRoles().stream()
                            .map(roles -> new SimpleGrantedAuthority(roles.getRoleName().name()))
                            .toList())
                    .build();
        }
        throw new UsernameNotFoundException("Username not found");
    }
}
