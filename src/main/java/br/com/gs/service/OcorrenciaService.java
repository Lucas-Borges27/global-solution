package br.com.gs.service;

import br.com.gs.model.BO.Ocorrencia;
import br.com.gs.model.BO.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.Date;
import java.util.List;

/**
 * Serviço para operações relacionadas a ocorrências.
 */
@ApplicationScoped
public class OcorrenciaService {

    public List<Ocorrencia> listarOcorrencias() {
        return Ocorrencia.listAll();
    }

    public List<Ocorrencia> listarOcorrenciasPorUsuario(Usuario usuario) {
        if (usuario == null || usuario.id == null) {
            throw new IllegalArgumentException("Usuário inválido para listar ocorrências.");
        }
        return Ocorrencia.list("usuario.id", usuario.id);
    }

    public Ocorrencia buscarPorId(Long id) {
        return (Ocorrencia) Ocorrencia.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Ocorrência com ID " + id + " não encontrada"));
    }

    @Transactional
    public Ocorrencia criar(String tipoOcorrencia, String descricao, String localizacao, String status, Date dataOcorrencia, Usuario usuario) {
        if (tipoOcorrencia == null || tipoOcorrencia.isBlank()) throw new IllegalArgumentException("O tipo da ocorrência não pode estar vazio.");
        if (descricao == null || descricao.isBlank()) throw new IllegalArgumentException("A descrição não pode estar vazia.");
        if (localizacao == null || localizacao.isBlank()) throw new IllegalArgumentException("A localização não pode estar vazia.");
        if (status == null || status.isBlank()) throw new IllegalArgumentException("O status não pode estar vazio.");
        if (dataOcorrencia == null) throw new IllegalArgumentException("A data da ocorrência não pode estar vazia.");
        if (usuario == null || usuario.id == null) throw new IllegalArgumentException("O usuário associado não pode estar vazio.");
    
        // BUSCA O USUÁRIO COMPLETO NO BANCO
        Usuario usuarioCompleto = Usuario.findById(usuario.id);
        if (usuarioCompleto == null) throw new IllegalArgumentException("Usuário não encontrado no sistema.");
    
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.tipoOcorrencia = tipoOcorrencia;
        ocorrencia.descricao = descricao;
        ocorrencia.localizacao = localizacao;
        ocorrencia.status = status;
        ocorrencia.dataOcorrencia = dataOcorrencia;
        ocorrencia.usuario = usuarioCompleto; // Agora com os dados completos
    
        ocorrencia.persist();
        return ocorrencia;
    }

    @Transactional
    public Ocorrencia atualizar(Long id, String tipoOcorrencia, String descricao, String localizacao, String status, Date dataOcorrencia, Usuario usuario) {
        Ocorrencia ocorrencia = Ocorrencia.findById(id);

        if (ocorrencia == null) {
            throw new NotFoundException("Ocorrência não encontrada.");
        }

        if (tipoOcorrencia != null && !tipoOcorrencia.isBlank()) ocorrencia.tipoOcorrencia = tipoOcorrencia;
        if (descricao != null && !descricao.isBlank()) ocorrencia.descricao = descricao;
        if (localizacao != null && !localizacao.isBlank()) ocorrencia.localizacao = localizacao;
        if (status != null && !status.isBlank()) ocorrencia.status = status;
        if (dataOcorrencia != null) ocorrencia.dataOcorrencia = dataOcorrencia;
        if (usuario != null && usuario.id != null) ocorrencia.usuario = usuario;

        ocorrencia.persist();
        return ocorrencia;
    }

    @Transactional
    public boolean deletar(Long id) {
        Ocorrencia ocorrencia = Ocorrencia.findById(id);
        if (ocorrencia == null) {
            throw new NotFoundException("Ocorrência não encontrada.");
        }
        ocorrencia.delete();
        return true;
    }
}