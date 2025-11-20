package br.com.unimotors.negocio.model;

import br.com.unimotors.usuario.model.Usuario;
import jakarta.persistence.*;

@Entity
@Table(name = "lojas_usuarios")
public class LojaUsuario {

    @EmbeddedId
    private LojaUsuarioId id;

    @ManyToOne(optional = false)
    @MapsId("lojaId")
    @JoinColumn(name = "loja_id")
    private Loja loja;

    @ManyToOne(optional = false)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "papel_na_loja", length = 30)
    private String papelNaLoja;

    public LojaUsuario() {
    }

    public LojaUsuario(Loja loja, Usuario usuario, String papelNaLoja) {
        this.loja = loja;
        this.usuario = usuario;
        this.papelNaLoja = papelNaLoja;
        this.id = new LojaUsuarioId(loja.getId(), usuario.getId());
    }

    public LojaUsuarioId getId() {
        return id;
    }

    public void setId(LojaUsuarioId id) {
        this.id = id;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPapelNaLoja() {
        return papelNaLoja;
    }

    public void setPapelNaLoja(String papelNaLoja) {
        this.papelNaLoja = papelNaLoja;
    }
}

