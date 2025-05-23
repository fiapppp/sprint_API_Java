package br.com.vialivre.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "PERMISSAOPERFIL")
public class PermissaoPerfil extends PanacheEntity {
    @ManyToOne
    @JoinColumn(name = "ID_PERMISSAO", nullable = false)
    public Permissao permissao;

    @ManyToOne
    @JoinColumn(name = "ID_PERFIL", nullable = false)
    public Perfil perfil;
}