package ra.project_md05.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.project_md05.constans.EHttpStatus;
import ra.project_md05.model.dto.request.FormLogin;
import ra.project_md05.model.dto.request.FormRegister;
import ra.project_md05.model.dto.response.ResponseWrapper;
import ra.project_md05.model.entity.Users;
import ra.project_md05.service.IAuthService;
import ra.project_md05.service.IUserService;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@Valid @RequestBody FormLogin formLogin) {
        Users user = userService.getUserByUserName(formLogin.getUsername());
        if (user.getStatus() == Boolean.FALSE) {
            return new ResponseEntity<>(
                    ResponseWrapper.builder()
                            .eHttpStatus(EHttpStatus.FAILED)
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .data("Tài khoản của bạn đã bị khóa")
                            .build(),
                    HttpStatus.FORBIDDEN
            );
        }

        return new ResponseEntity<>(
                ResponseWrapper.builder()
                        .eHttpStatus(EHttpStatus.SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .data(authService.handleLogin(formLogin))
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> handleRegister(@Valid @RequestBody FormRegister formRegister) {
        authService.handleRegister(formRegister);
        return new ResponseEntity<>( ResponseWrapper.builder()
                .eHttpStatus(EHttpStatus.SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .data("Register successfully")
                .build(),
                HttpStatus.CREATED);
    }

}
