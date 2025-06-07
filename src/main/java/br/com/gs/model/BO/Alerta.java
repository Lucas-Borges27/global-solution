package br.com.gs.model.BO;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

/**
 * Classe de entidade que representa um alerta no sistema.
 * Mapeada para a tabela "ALERTA" no banco de dados.
 */
@Entity
@Table(name = "ALERTA")
public class Alerta extends PanacheEntity {

    @Column(name = "PRIORIDADE", nullable = false, length = 20)
    public String prioridade;

    @Column(name = "JUSTIFICATIVA", nullable = false, length = 255)
    public String justificativa;

    @ManyToOne
    @JoinColumn(name = "OCORRENCIA_ID", nullable = false)
    public Ocorrencia ocorrencia;
}