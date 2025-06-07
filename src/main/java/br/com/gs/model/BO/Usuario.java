package br.com.gs.model.BO;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

/**
 * Classe que representa um usuário no sistema.
 */
@Entity
@Table(name = "USUARIO")
public class Usuario extends PanacheEntity {

    @Column(name = "NOME", nullable = false, length = 50)
    public String nome;

    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    public String email;

    @Column(name = "SENHA", nullable = false, length = 255)
    public String senha;

    @Column(name = "CARGO", nullable = false, length = 50)
    public String cargo;

    @Column(name = "ORGAO", nullable = false, length = 50)
    public String orgao;
}