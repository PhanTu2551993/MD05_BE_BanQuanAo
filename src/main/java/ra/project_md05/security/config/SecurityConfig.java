package ra.project_md05.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import ra.project_md05.constans.RoleName;
import ra.project_md05.exception.AccessDenied;
import ra.project_md05.exception.JwtEntryPoint;
import ra.project_md05.security.jwt.JwtTokenFilter;
import ra.project_md05.security.principal.UserDetailCustomService;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailCustomService userDetailCustomService;
    private final JwtEntryPoint jwtEntryPoint;
    private final AccessDenied accessDenied;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(config -> config.configurationSource(request -> {
                    CorsConfiguration cf = new CorsConfiguration();
                    cf.setAllowedOrigins(List.of("http://localhost:5173/"));
                    cf.setAllowedMethods(List.of("*"));
                    cf.setAllowCredentials(true);
                    cf.setAllowedHeaders(List.of("*"));
                    cf.setExposedHeaders(List.of("*"));
                    return cf;
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(
                        exception -> exception
                                .authenticationEntryPoint(jwtEntryPoint)
                                .accessDeniedHandler(accessDenied)
                )
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        url -> url
                                .requestMatchers("/api/v1/admin/**").hasAnyAuthority(RoleName.ROLE_ADMIN.name())
                                .requestMatchers("/api/v1/manager/**").hasAnyAuthority(RoleName.ROLE_MANAGER.name())
                                .requestMatchers("/api/v1/user/**").hasAnyAuthority(RoleName.ROLE_USER.name())
                                .anyRequest().permitAll()//còn lại không cần xác thực
                )
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailCustomService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}
