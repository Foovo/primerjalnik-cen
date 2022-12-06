package si.fri.prpo.skupina02.api.v1.viri;

import si.fri.prpo.skupina02.entitete.IzdelekVTrgovini;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.IzdelekVTrgoviniZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("izdelekVTrgovini")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class IzdelekVTrgoviniVir {
    @Inject
    private IzdelekVTrgoviniZrno izdelekVTrgoviniZrno;

    @BeleziKlice
    @GET
    @Path("{id}")
    public Response pridobiIzdelekVTrgovini(@PathParam("id") Integer id) {
        IzdelekVTrgovini izdelekVTrgovini = izdelekVTrgoviniZrno.getById(id);

        if(izdelekVTrgovini != null) {
            return Response.ok(izdelekVTrgovini).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @BeleziKlice
    @POST
    public Response dodajIzdelekVTrgovini(IzdelekVTrgovini izdelekVTrgovini){

        return Response
                .status(Response.Status.CREATED)
                .entity(izdelekVTrgoviniZrno.addIzdelekVTrgovini(izdelekVTrgovini))
                .build();
    }

    @BeleziKlice
    @DELETE
    @Path("{id}")
    public Response odstraniIzdelekVTrgovini(@PathParam("id") Integer id){
        if(izdelekVTrgoviniZrno.deleteIzdelekVTrgovini(id)) {
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
    public Response posodobiIzdelekVTrgovini(IzdelekVTrgovini izdelekVTrgovini){
        if(izdelekVTrgoviniZrno.updateIzdelekVTrgovini(izdelekVTrgovini)) {
            return Response
                    .status(Response.Status.OK)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
}
