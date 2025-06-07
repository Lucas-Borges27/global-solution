package br.com.gs.model.BO;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.util.Date;

/**
 * Classe de entidade que representa uma ocorrência no sistema.
 * Mapeada para a tabela "OCORRENCIA" no banco de dados.
 */
@Entity
@Table(name = "OCORRENCIA")
public class Ocorrencia extends PanacheEntity {

    @Column(name = "TIPO_OCORRENCIA", nullable = false, length = 50)
    public String tipoOcorrencia;

    @Column(name = "DESCRICAO", nullable = false, length = 255)
    public String descricao;

    @Column(name = "LOCALIZACAO", nullable = false, length = 100)
    public String localizacao;

    @Column(name = "STATUS", nullable = false, length = 30)
    public String status;

    @Column(name = "DATA_OCORRENCIA", nullable = false)
    public Date dataOcorrencia;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    public Usuario usuario;

}