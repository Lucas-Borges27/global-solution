package br.com.gs.resource;

import br.com.gs.model.BO.Alerta;
import br.com.gs.service.AlertaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/alertas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlertaResource {

    @Inject
    AlertaService alertaService;

    @GET
    public List<Alerta> listarAlertas() {
        return alertaService.listarAlertas();
    }

    @GET
    @Path("/{id}")
    public Alerta buscarPorId(@PathParam("id") Long id) {
        return alertaService.buscarPorId(id);
    }

    @POST
    public Response criar(Alerta alerta) {
        Alerta novoAlerta = alertaService.criar(alerta.prioridade, alerta.justificativa, alerta.ocorrencia);
        return Response.status(Response.Status.CREATED).entity(novoAlerta).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, Alerta alerta) {
        Alerta alertaAtualizado = alertaService.atualizar(id, alerta.prioridade, alerta.justificativa, alerta.ocorrencia);
        return Response.ok(alertaAtualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        alertaService.deletar(id);
        return Response.noContent().build();
    }
}