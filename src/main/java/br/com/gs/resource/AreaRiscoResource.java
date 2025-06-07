package br.com.gs.resource;

import br.com.gs.model.BO.AreaRisco;
import br.com.gs.service.AreaRiscoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/areas-risco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AreaRiscoResource {

    @Inject
    AreaRiscoService areaRiscoService;

    @GET
    public List<AreaRisco> listarAreasRisco() {
        return areaRiscoService.listarAreasRisco();
    }

    @GET
    @Path("/{id}")
    public AreaRisco buscarPorId(@PathParam("id") Long id) {
        return areaRiscoService.buscarPorId(id);
    }

    @POST
    public Response criar(AreaRisco areaRisco) {
        AreaRisco novaAreaRisco = areaRiscoService.criar(areaRisco.cidade, areaRisco.coordenadas, areaRisco.alerta);
        return Response.status(Response.Status.CREATED).entity(novaAreaRisco).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, AreaRisco areaRisco) {
        AreaRisco areaRiscoAtualizada = areaRiscoService.atualizar(id, areaRisco.cidade, areaRisco.coordenadas, areaRisco.alerta);
        return Response.ok(areaRiscoAtualizada).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        areaRiscoService.deletar(id);
        return Response.noContent().build();
    }
}