package br.com.gs.service;

import br.com.gs.model.BO.AreaRisco;
import br.com.gs.model.BO.Alerta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class AreaRiscoService {

    public List<AreaRisco> listarAreasRisco() {
        return AreaRisco.listAll();
    }

    public AreaRisco buscarPorId(Long id) {
        return (AreaRisco) AreaRisco.findByIdOptional(id)
                .orElseThrow(() -> new IllegalArgumentException("Área de risco com ID " + id + " não encontrada"));
    }

    @Transactional
    public AreaRisco criar(String cidade, String coordenadas, Alerta alerta) {
        if (cidade == null || cidade.isBlank()) throw new IllegalArgumentException("A cidade não pode estar vazia.");
        if (coordenadas == null || coordenadas.isBlank()) throw new IllegalArgumentException("As coordenadas não podem estar vazias.");
        if (alerta == null || alerta.id == null) throw new IllegalArgumentException("O alerta associado não pode estar vazio.");

        Alerta alertaCompleto = Alerta.findById(alerta.id);
        if (alertaCompleto == null) throw new IllegalArgumentException("Alerta não encontrado no sistema.");

        AreaRisco areaRisco = new AreaRisco();
        areaRisco.cidade = cidade;
        areaRisco.coordenadas = coordenadas;
        areaRisco.alerta = alerta;

        areaRisco.persist();
        return areaRisco;
    }

    @Transactional
    public AreaRisco atualizar(Long id, String cidade, String coordenadas, Alerta alerta) {
        AreaRisco areaRisco = AreaRisco.findById(id);

        if (areaRisco == null) {
            throw new IllegalArgumentException("Área de risco não encontrada.");
        }
        if (cidade != null && !cidade.isBlank()) areaRisco.cidade = cidade;
        if (coordenadas != null && !coordenadas.isBlank()) areaRisco.coordenadas = coordenadas;
        if (alerta != null && alerta.id != null) areaRisco.alerta = alerta;

        areaRisco.persist();
        return areaRisco;
    }

    @Transactional
    public boolean deletar(Long id) {
        AreaRisco areaRisco = AreaRisco.findById(id);
        if (areaRisco == null) {
            throw new NotFoundException("Área de risco não encontrada.");
        }
        areaRisco.delete();
        return true;
    }
}