package br.com.gs.resource;

import br.com.gs.model.BO.Ocorrencia;
import br.com.gs.model.BO.Usuario;
import br.com.gs.service.OcorrenciaService;
import br.com.gs.service.UsuarioService;
import io.quarkus.vertx.http.runtime.CurrentVertxRequest;
import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/ocorrencias")
@Produces("application/json")
@Consumes("application/json")
public class OcorrenciaResource {

    @Inject
    OcorrenciaService ocorrenciaService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    CurrentVertxRequest currentVertxRequest;

    @GET
    public List<Ocorrencia> listarOcorrencias() {
        HttpServerRequest request = currentVertxRequest.getCurrent().request();
        io.vertx.core.http.Cookie userIdCookie = null;
        if (request != null && request.headers() != null) {
            userIdCookie = request.getCookie("userId");
        }
        if (userIdCookie == null) {
            System.err.println("Usuário não autenticado: cookie userId não encontrado.");
            throw new WebApplicationException("Usuário não autenticado.", Response.Status.UNAUTHORIZED);
        }
        Long userId;
        try {
            userId = Long.parseLong(userIdCookie.getValue());
            System.out.println("UserId cookie value: " + userId);
        } catch (NumberFormatException e) {
            System.err.println("ID de usuário inválido no cookie: " + userIdCookie.getValue());
            throw new WebApplicationException("ID de usuário inválido no cookie.", Response.Status.BAD_REQUEST);
        }
        Usuario usuarioLogado = usuarioService.buscarPorId(userId);
        if (usuarioLogado == null) {
            System.err.println("Usuário não encontrado para ID: " + userId);
            throw new WebApplicationException("Usuário não encontrado.", Response.Status.UNAUTHORIZED);
        }
        System.out.println("Usuário logado: " + usuarioLogado.nome + " (ID: " + usuarioLogado.id + ")");
        return ocorrenciaService.listarOcorrenciasPorUsuario(usuarioLogado);
    }

    @GET
    @Path("/todas")
    public List<Ocorrencia> listarTodasOcorrencias() {
        return ocorrenciaService.listarOcorrencias();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Ocorrencia ocorrencia = ocorrenciaService.buscarPorId(id);
            return Response.ok(ocorrencia).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    public Response criar(Ocorrencia ocorrencia) {
        HttpServerRequest request = currentVertxRequest.getCurrent().request();
        io.vertx.core.http.Cookie userIdCookie = null;
        if (request != null && request.headers() != null) {
            userIdCookie = request.getCookie("userId");
        }
        if (userIdCookie == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário não autenticado.").build();
        }
        Long userId;
        try {
            userId = Long.parseLong(userIdCookie.getValue());
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID de usuário inválido no cookie.").build();
        }
        Usuario usuarioLogado = usuarioService.buscarPorId(userId);
        if (usuarioLogado == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Usuário não encontrado.").build();
        }
        Ocorrencia novaOcorrencia = ocorrenciaService.criar(
                ocorrencia.tipoOcorrencia,
                ocorrencia.descricao,
                ocorrencia.localizacao,
                ocorrencia.status,
                ocorrencia.dataOcorrencia,
                usuarioLogado
        );
        return Response.status(Response.Status.CREATED).entity(novaOcorrencia).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Ocorrencia ocorrencia) {
        Ocorrencia ocorrenciaAtualizada = ocorrenciaService.atualizar(
                id,
                ocorrencia.tipoOcorrencia,
                ocorrencia.descricao,
                ocorrencia.localizacao,
                ocorrencia.status,
                ocorrencia.dataOcorrencia,
                ocorrencia.usuario
        );
        return Response.ok(ocorrenciaAtualizada).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        if (ocorrenciaService.deletar(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Ocorrência não encontrada.").build();
    }
}
