package br.com.gs.resource;

import br.com.gs.model.BO.Usuario;
import br.com.gs.service.UsuarioService;
import jakarta.inject.Inject;
import io.quarkus.vertx.http.runtime.CurrentVertxRequest;
import io.vertx.core.http.HttpServerRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
@Produces("application/json")
@Consumes("application/json")
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @GET
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return Response.ok(usuario).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criar(Usuario usuario) {
        Usuario novoUsuario = usuarioService.criar(usuario.nome, usuario.email, usuario.senha, usuario.cargo, usuario.orgao);
        return Response.status(Response.Status.CREATED).entity(novoUsuario).build();
    }

    @Inject
    CurrentVertxRequest currentVertxRequest;

    @POST
    @Path("/login")
    public Response login(Usuario usuario) {
        try {
            usuarioService.login(usuario.email, usuario.senha);
            Usuario usuarioLogado = Usuario.find("email", usuario.email).firstResult();
            if (usuarioLogado == null) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário não encontrado após login.").build();
            }
            // Create session and store user ID
            HttpServerRequest request = currentVertxRequest.getCurrent().request();
            request.response().putHeader("Set-Cookie",
                    "userId=" + usuarioLogado.id +
                            "; Path=/" +
                            "; HttpOnly" +
                            "; Secure" +
                            "; SameSite=None"
            );
            return Response.ok(usuarioLogado).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciais inválidas.").build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout() {
        // Clear the userId cookie
        HttpServerRequest request = currentVertxRequest.getCurrent().request();
        request.response().putHeader("Set-Cookie",
                "userId=; Path=/; Max-Age=0; HttpOnly; Secure; SameSite=None"
        );

        return Response.ok("Logout successful").build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario.nome, usuario.email, usuario.senha, usuario.cargo, usuario.orgao);
        return Response.ok(usuarioAtualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        if (usuarioService.deletar(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado.").build();
    }
}