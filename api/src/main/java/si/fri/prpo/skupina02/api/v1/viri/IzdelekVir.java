package si.fri.prpo.skupina02.api.v1.viri;

import si.fri.prpo.skupina02.entitete.Izdelek;
import si.fri.prpo.skupina02.storitve.crud.IzdelekZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("izdelek")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class IzdelekVir {
    @Inject
    private IzdelekZrno izdelekZrno;

    @GET
    @Path("{id}")
    public Response pridobiIzdelek(@PathParam("id") Integer id) {
        Izdelek izdelek = izdelekZrno.getById(id);

        if(izdelek != null) {
            return Response.ok(izdelek).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response dodajIzdelekVTrgovini(Izdelek izdelek){

        return Response
                .status(Response.Status.CREATED)
                .entity(izdelekZrno.addIzdelek(izdelek))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniIzdelek(@PathParam("id") Integer id){
        if(izdelekZrno.deleteIzdelek(id)) {
            return Response
                    .status(Response.Status.OK)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }

    @PUT
    public Response posodobiIzdelek(Izdelek izdelek){
        if(izdelekZrno.updateIzdelek(izdelek)) {
            return Response
                    .status(Response.Status.OK)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
}
