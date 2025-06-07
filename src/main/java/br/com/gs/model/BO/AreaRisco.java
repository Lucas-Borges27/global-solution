package br.com.gs.model.BO;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

/**
 * Classe de entidade que representa uma área de risco no sistema.
 * Mapeada para a tabela "AREA_RISCO" no banco de dados.
 */
@Entity
@Table(name = "AREA_RISCO")
public class AreaRisco extends PanacheEntity {

    @Column(name = "CIDADE", nullable = false, length = 100)
    public String cidade;

    @Column(name = "COORDENADAS", nullable = false, length = 100)
    public String coordenadas;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ALERTA_ID", nullable = false)
    public Alerta alerta;
}