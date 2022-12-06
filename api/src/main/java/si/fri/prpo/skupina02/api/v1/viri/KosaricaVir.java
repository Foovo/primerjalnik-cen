package si.fri.prpo.skupina02.api.v1.viri;

import si.fri.prpo.skupina02.entitete.Kosarica;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.KosaricaZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("kosarica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class KosaricaVir {
    @Inject
    private KosaricaZrno kosaricaZrno;

    @BeleziKlice
    @GET
    @Path("{id}")
    public Response pridobiKosarico(@PathParam("id") Integer id) {
        Kosarica kosarica = kosaricaZrno.getById(id);

        if(kosarica != null) {
            return Response.ok(kosarica).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @BeleziKlice
    @POST
    public Response dodajKosarico(Kosarica kosarica){

        return Response
                .status(Response.Status.CREATED)
                .entity(kosaricaZrno.addKosarica(kosarica))
                .build();
    }

    @BeleziKlice
    @DELETE
    @Path("{id}")
    public Response odstraniKosarico(@PathParam("id") Integer id){
        if(kosaricaZrno.deleteKosarica(id)) {
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
    public Response posodobiKosarico(Kosarica kosarica){
        if(kosaricaZrno.updateKosarica(kosarica)) {
            return Response
                    .status(Response.Status.OK)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
}
