package ra.project_md05.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.project_md05.constans.RoleName;
import ra.project_md05.model.dto.request.FormLogin;
import ra.project_md05.model.dto.request.FormRegister;
import ra.project_md05.model.dto.response.JwtResponse;
import ra.project_md05.model.entity.Roles;
import ra.project_md05.model.entity.Users;
import ra.project_md05.repository.IUserRepository;
import ra.project_md05.security.jwt.JwtProvider;
import ra.project_md05.security.principal.UserDetailCustom;
import ra.project_md05.service.IAuthService;
import ra.project_md05.service.IRoleService;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final IRoleService roleService;
    private final IUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    @Override
    public JwtResponse handleLogin(FormLogin formLogin) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.getUsername(), formLogin.getPassword()));
        } catch (AuthenticationException e) {
            throw new ArithmeticException("Invalid username or password.");
        }
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal();

        String accessToken = jwtProvider.generateToken(userDetailCustom);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .username(userDetailCustom.getUsername())
                .fullName(userDetailCustom.getFullName())
                .email(userDetailCustom.getEmail())
                .address(userDetailCustom.getAddress())
                .avatar(userDetailCustom.getAvatar())
                .createdAt(userDetailCustom.getCreatedAt())
                .updatedAt(userDetailCustom.getUpdatedAt())
                .isDeleted(userDetailCustom.getIsDeleted())
                .phone(userDetailCustom.getPhone())
                .status(userDetailCustom.getStatus())
                .roles(userDetailCustom.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public void handleRegister(FormRegister formRegister) {
        Set<Roles> roles = new HashSet<>();
        if (formRegister.getRoles() == null || formRegister.getRoles().isEmpty()) {
            roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
        }else {
            formRegister.getRoles().forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_ADMIN));
                    case "manager":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_MANAGER));
                    case "user":
                        roles.add(roleService.findByRoleName(RoleName.ROLE_USER));
                    default:
                        throw new RuntimeException("role not found");
                }
            });
        }

        Users users = Users.builder()
                .email(formRegister.getEmail())
                .username(formRegister.getUsername())
                .fullName(formRegister.getFullName())
                .phone(formRegister.getPhone())
                .status(true)
                .createdAt(new Date())
                .updatedAt(new Date())
                .isDeleted(true)
                .password(passwordEncoder.encode(formRegister.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(users);
    }
}
