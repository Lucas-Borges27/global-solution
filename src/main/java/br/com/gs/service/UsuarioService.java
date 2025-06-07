package br.com.gs.service;

import br.com.gs.model.BO.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Serviço para operações relacionadas ao usuário.
 */
@ApplicationScoped
public class UsuarioService {

    public List<Usuario> listarUsuarios() {
        return Usuario.listAll();
    }

    public Usuario buscarPorId(Long id) {
        return (Usuario) Usuario.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado"));
    }

    @Transactional
    public Usuario criar(String nome, String email, String senha, String cargo, String orgao) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("O nome não pode estar vazio.");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("O email não pode estar vazio.");
        if (senha == null || senha.isBlank()) throw new IllegalArgumentException("A senha não pode estar vazia.");
        if (cargo == null || cargo.isBlank()) throw new IllegalArgumentException("O cargo não pode estar vazio.");
        if (orgao == null || orgao.isBlank()) throw new IllegalArgumentException("O órgão não pode estar vazio.");

        if (Usuario.find("email", email).firstResult() != null) {
            throw new RuntimeException("Email já cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.nome = nome;
        usuario.email = email;
        usuario.senha = senha;
        usuario.cargo = cargo;
        usuario.orgao = orgao;

        usuario.persist();
        return usuario;
    }

    @Transactional
    public void login(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email não pode estar vazio.");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode estar vazia.");
        }

        Usuario usuario = Usuario.find("email", email).firstResult();

        if (usuario == null || !usuario.senha.equals(senha)) {
            throw new RuntimeException("Email ou senha inválidos.");
        }
    }

    @Transactional
    public Response logout(String email) {
        try {
            Usuario usuario = Usuario.find("email", email).firstResult();

            if (usuario == null) {
                throw new NotFoundException("Usuário não encontrado.");
            }

            System.out.println("Logout realizado com sucesso para o usuário: " + usuario.email);
            return Response.ok("Logout realizado com sucesso.").build();

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado.").build();
        } catch (Exception e) {
            System.err.println("Erro ao fazer logout: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro inesperado ao realizar logout.").build();
        }
    }

    @Transactional
    public Usuario atualizar(Long id, String nome, String email, String senha, String cargo, String orgao) {
        Usuario usuario = Usuario.findById(id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        if (nome != null && !nome.isBlank()) usuario.nome = nome;
        if (email != null && !email.isBlank()) usuario.email = email;
        if (senha != null && !senha.isBlank()) usuario.senha = senha;
        if (cargo != null && !cargo.isBlank()) usuario.cargo = cargo;
        if (orgao != null && !orgao.isBlank()) usuario.orgao = orgao;

        usuario.persist();
        return usuario;
    }

    @Transactional
    public boolean deletar(Long id) {
        Usuario usuario = Usuario.findById(id);
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }
        usuario.delete();
        return true;
    }
}
