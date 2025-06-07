package br.com.gs.service;


import br.com.gs.model.BO.Alerta;
import br.com.gs.model.BO.Ocorrencia;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class AlertaService {

    public List<Alerta> listarAlertas() {
        return Alerta.listAll();
    }

    public Alerta buscarPorId(Long id) {
        return (Alerta) Alerta.findByIdOptional(id)
                .orElseThrow(() -> new IllegalArgumentException("Alerta com ID " + id + " não encontrado"));
    }

    @Transactional
    public Alerta criar(String prioridade, String justificativa, Ocorrencia ocorrencia) {
        if (prioridade == null || prioridade.isBlank()) throw new IllegalArgumentException("A prioridade não pode estar vazio.");
        if (justificativa == null || justificativa.isBlank()) throw new IllegalArgumentException("A justificativa não pode estar vazia.");
        if (ocorrencia == null || ocorrencia.id == null) throw new IllegalArgumentException("A ocorrencia associada não pode estar vazio.");

        Ocorrencia OcorrenciaCompleta = Ocorrencia.findById(ocorrencia.id);
        if (OcorrenciaCompleta == null) throw new IllegalArgumentException("Ocorrência não encontrado no sistema.");

        Alerta alerta = new Alerta();
        alerta.prioridade = prioridade;
        alerta.justificativa = justificativa;
        alerta.ocorrencia = ocorrencia;

        alerta.persist();
        return alerta;
    }

    @Transactional
    public Alerta atualizar(Long id, String prioridade, String justificativa, Ocorrencia ocorrencia) {
        Alerta alerta = Alerta.findById(id);

        if (alerta == null) {
            throw new IllegalArgumentException("Alerta não encontrado.");
        }
        if (prioridade != null && !prioridade.isBlank()) alerta.prioridade = prioridade;
        if (justificativa != null && !justificativa.isBlank()) alerta.justificativa = justificativa;
        if (ocorrencia != null && ocorrencia.id != null) alerta.ocorrencia = ocorrencia;

        alerta.persist();
        return alerta;
    }

    @Transactional
    public boolean deletar(Long id) {
        Alerta alerta = Alerta.findById(id);
        if (alerta == null) {
            throw new NotFoundException("Ocorrência não encontrada.");
        }
        alerta.delete();
        return true;
    }
}
