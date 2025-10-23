package br.com.unimotors.autenticacao.jwt;

import br.com.unimotors.usuario.service.UsuarioService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroJwt extends OncePerRequestFilter {

    private final TokenJwtService tokens;
    private final UsuarioService usuarios;

    public FiltroJwt(TokenJwtService tokens, UsuarioService usuarios) {
        this.tokens = tokens;
        this.usuarios = usuarios;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req, @NonNull HttpServletResponse res, @NonNull FilterChain chain)
        throws ServletException, IOException {
        var auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            var jwt = auth.substring(7);
            try {
                Claims claims = tokens.validar(jwt);
                var email = claims.getSubject();
                UserDetails detalhes = usuarios.loadUserByUsername(email);
                var autenticado = new UsernamePasswordAuthenticationToken(detalhes, null, detalhes.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(autenticado);
            } catch (Exception ex) {
                // invalid token - clear context and proceed (endpoint security will block if needed)
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(req, res);
    }
}
