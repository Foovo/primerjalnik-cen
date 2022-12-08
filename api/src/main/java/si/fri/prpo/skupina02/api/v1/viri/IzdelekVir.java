package si.fri.prpo.skupina02.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina02.entitete.Izdelek;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.IzdelekZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("izdelek")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class IzdelekVir {
    @Inject
    private IzdelekZrno izdelekZrno;

    @Context
    protected UriInfo uriInfo;

    @BeleziKlice
    @GET
    public Response pridobiUporabnike() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        var entitete = izdelekZrno.get(query);
        var entitete_count = izdelekZrno.getCount(query);
        return Response
                .ok(entitete)
                .header("X-Total-Count", entitete_count)
                .build();
    }

    @BeleziKlice
    @GET
    @Path("{id}")
    public Response pridobiIzdelek(@PathParam("id") Integer id) {
        Izdelek izdelek = izdelekZrno.getById(id);

        if(izdelek != null) {
            return Response.ok(izdelek).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @BeleziKlice
    @POST
    public Response dodajIzdelekVTrgovini(Izdelek izdelek){

        return Response
                .status(Response.Status.CREATED)
                .entity(izdelekZrno.addIzdelek(izdelek))
                .build();
    }

    @BeleziKlice
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

    @BeleziKlice
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
