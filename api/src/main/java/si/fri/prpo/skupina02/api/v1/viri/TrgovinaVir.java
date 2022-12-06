package si.fri.prpo.skupina02.api.v1.viri;

import si.fri.prpo.skupina02.entitete.Trgovina;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.TrgovinaZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("trgovina")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class TrgovinaVir {
    @Inject
    private TrgovinaZrno trgovinaZrno;

    @BeleziKlice
    @GET
    @Path("{id}")
    public Response pridobiTrgovino(@PathParam("id") Integer id) {
        Trgovina trgovina = trgovinaZrno.getById(id);

        if(trgovina != null) {
            return Response.ok(trgovina).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @BeleziKlice
    @POST
    public Response dodajTrgovino(Trgovina trgovina){

        return Response
                .status(Response.Status.CREATED)
                .entity(trgovinaZrno.addTrgovina(trgovina))
                .build();
    }

    @BeleziKlice
    @DELETE
    @Path("{id}")
    public Response odstraniTrgovino(@PathParam("id") Integer id){
        if(trgovinaZrno.deleteTrgovina(id)) {
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
    public Response posodobiTrgovino(Trgovina trgovina){
        if(trgovinaZrno.updateTrgovina(trgovina)) {
            return Response
                    .status(Response.Status.OK)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
}
