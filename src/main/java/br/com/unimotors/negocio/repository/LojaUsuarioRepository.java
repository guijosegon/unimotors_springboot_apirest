package br.com.unimotors.negocio.repository;

import br.com.unimotors.negocio.model.LojaUsuario;
import br.com.unimotors.negocio.model.LojaUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LojaUsuarioRepository extends JpaRepository<LojaUsuario, LojaUsuarioId> {
}

