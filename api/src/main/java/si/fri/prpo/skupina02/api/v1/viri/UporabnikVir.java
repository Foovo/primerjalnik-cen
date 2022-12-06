package si.fri.prpo.skupina02.api.v1.viri;

import si.fri.prpo.skupina02.entitete.Uporabnik;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.UporabnikZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("uporabnik")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UporabnikVir {

    @Inject
    private UporabnikZrno uporabnikZrno;

    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@PathParam("id") Integer id) {
        Uporabnik uporabnik = uporabnikZrno.getById(id);

        if(uporabnik != null) {
            return Response.ok(uporabnik).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response dodajUporabnika(Uporabnik uporabnik){

        return Response
                .status(Response.Status.CREATED)
                .entity(uporabnikZrno.addUporabnik(uporabnik))
                .build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniUporabnika(@PathParam("id") Integer id){
        if(uporabnikZrno.deleteUporabnik(id)) {
            return Response
                    .status(Response.Status.OK)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }

    @PUT
    public Response posodobiUporabnika(Uporabnik uporabnik){
        if(uporabnikZrno.updateUporabnik(uporabnik)) {
            return Response
                    .status(Response.Status.OK)
                    .build();
        }

        return Response
                .status(Response.Status.NOT_FOUND)
                .build();
    }
}
