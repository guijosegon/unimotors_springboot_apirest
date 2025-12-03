package br.com.unimotors.usuario.service;

import br.com.unimotors.usuario.model.Usuario;
import br.com.unimotors.usuario.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Profile("seed")
public class UsuarioSeedFixRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(UsuarioSeedFixRunner.class);

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioSeedFixRunner(UsuarioRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        List<String> seedEmails = Arrays.asList(
                "admin@unimotors.com",
                "vendedor1@unimotors.com",
                "vendedor2@unimotors.com",
                "comprador1@unimotors.com"
        );

        for (String email : seedEmails) {
            Optional<Usuario> opt = repo.findByEmail(email);
            if (opt.isEmpty()) {
                continue;
            }
            Usuario usuario = opt.get();
            String hash = usuario.getSenhaHash();
            // Se a senha atual não confere com "123456", corrige para usar o hash gerado pelo PasswordEncoder
            if (hash == null || !passwordEncoder.matches("123456", hash)) {
                usuario.setSenhaHash(passwordEncoder.encode("123456"));
                repo.save(usuario);
                log.info("Senha do usuário seed {} ajustada para o padrão de desenvolvimento.", email);
            }
        }
    }
}
