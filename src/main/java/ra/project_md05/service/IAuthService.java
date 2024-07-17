package ra.project_md05.service;


import ra.project_md05.model.dto.request.FormLogin;
import ra.project_md05.model.dto.request.FormRegister;
import ra.project_md05.model.dto.response.JwtResponse;

public interface IAuthService {

    JwtResponse handleLogin(FormLogin formLogin);
    void handleRegister(FormRegister formRegister);
}
