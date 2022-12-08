package si.fri.prpo.skupina02.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina02.entitete.Uporabnik;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.UporabnikZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("uporabnik")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UporabnikVir {

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Context
    protected UriInfo uriInfo;

    @BeleziKlice
    @GET
    public Response pridobiUporabnike() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        var entitete = uporabnikZrno.get(query);
        var entitete_count = uporabnikZrno.getCount(query);
        return Response
                .ok(entitete)
                .header("X-Total-Count", entitete_count)
                .build();
    }

    @BeleziKlice
    @GET
    @Path("{id}")
    public Response pridobiUporabnika(@PathParam("id") Integer id) {
        Uporabnik uporabnik = uporabnikZrno.getById(id);
        if(uporabnik != null) {
            return Response.ok(uporabnik).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @BeleziKlice
    @POST
    public Response dodajUporabnika(Uporabnik uporabnik){

        return Response
                .status(Response.Status.CREATED)
                .entity(uporabnikZrno.addUporabnik(uporabnik))
                .build();
    }

    @BeleziKlice
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

    @BeleziKlice
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
