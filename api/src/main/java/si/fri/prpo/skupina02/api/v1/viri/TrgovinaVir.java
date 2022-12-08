package si.fri.prpo.skupina02.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Encoding;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.skupina02.dtos.UstvariTrgovinoDTO;
import si.fri.prpo.skupina02.entitete.Trgovina;
import si.fri.prpo.skupina02.entitete.Uporabnik;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.TrgovinaZrno;
import si.fri.prpo.skupina02.storitve.upravljanje.UpravljanjeTrgovinZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("trgovina")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class TrgovinaVir {
    @Inject
    private TrgovinaZrno trgovinaZrno;

    @Inject
    private UpravljanjeTrgovinZrno upravljanjeTrgovinZrno;

    @Context
    protected UriInfo uriInfo;

    @BeleziKlice
    @GET
    @Operation(summary = "Pridobi trgovine", description = "Vrne trgovine.")
    @APIResponses({
            @APIResponse(
                    description = "Trgovine",
                    responseCode = "200 OK",
                    content = @Content(
                            schema = @Schema(allOf = Trgovina.class),
                            encoding = @Encoding(headers = {
                                    @Header(name = "X-Total-Count", description = "Število vrnjenih trgovin")
                            }
                            )
                    )
            )
    })
    public Response pridobiTrgovine() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        var entitete = trgovinaZrno.get(query);
        var entitete_count = trgovinaZrno.getCount(query);
        return Response
                .ok(entitete)
                .header("X-Total-Count", entitete_count)
                .build();
    }

    @BeleziKlice
    @GET
    @Path("{id}")
    @Operation(summary = "Pridobi trgovino", description = "Vrne trgovino z id.")
    @APIResponses({
            @APIResponse(
                    description = "Trgovina",
                    responseCode = "200 OK",
                    content = @Content(
                            schema = @Schema(implementation = Trgovina.class)
                    )
            ),
            @APIResponse (
                    description = "Trgovina ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
    public Response pridobiTrgovino(@PathParam("id") Integer id) {
        Trgovina trgovina = trgovinaZrno.getById(id);

        if(trgovina != null) {
            return Response.ok(trgovina).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @BeleziKlice
    @POST
    @Operation(summary = "Ustvari trgovino", description = "Ustvari novo trgovino")
    @APIResponses({
            @APIResponse(
                    description = "Trgovina",
                    responseCode = "200 OK",
                    content = @Content(
                            schema = @Schema(implementation = Trgovina.class)
                    )
            ),
            @APIResponse (
                    description = "Napaka pri ustvarjanju trgovine",
                    responseCode = "400 BAD REQUEST"
            )
    })
    public Response dodajTrgovino(UstvariTrgovinoDTO ustvariTrgovinoDTO){

        var trgovina = upravljanjeTrgovinZrno.ustvariTrgovino(ustvariTrgovinoDTO);

        if (trgovina == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        return Response
                .status(Response.Status.CREATED)
                .entity(trgovinaZrno.addTrgovina(trgovina))
                .build();
    }

    @BeleziKlice
    @DELETE
    @Path("{id}")
    @Operation(summary = "Odstrani trgovino", description = "Odstrani trgovino")
    @APIResponses({
            @APIResponse(
                    description = "Trgovina odstranjena",
                    responseCode = "200 OK"
            ),
            @APIResponse (
                    description = "Trgovina ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
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
    @Operation(summary = "Posodobi trgovino", description = "Posodobi trgovino")
    @APIResponses({
            @APIResponse(
                    description = "Trgovina posodobljena",
                    responseCode = "200 OK"
            ),
            @APIResponse (
                    description = "Trgovina ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
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
