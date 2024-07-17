package ra.project_md05.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ra.project_md05.security.principal.UserDetailCustomService;


import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private final UserDetailCustomService userDetailCustomService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = getTokenFromRequest(request);
		if(token!=null && !token.isEmpty()){
			boolean valid = jwtProvider.validationToken(token);
			if(valid){
				//tach username tu token
				String username = jwtProvider.getUsernameFromToken(token);
				//lay thong tin tai khoan trong database (lien quan CustomDetailService)
				UserDetails userDetails = userDetailCustomService.loadUserByUsername(username);

				Authentication auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				//set vao SecurityContextHolder
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		filterChain.doFilter(request,response);
	}

	public String getTokenFromRequest(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring("Bearer ".length());
		}
		return null;
	}
	
}
