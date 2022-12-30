package si.fri.prpo.skupina02.api.v1.viri;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Encoding;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.skupina02.dtos.UstvariKosaricoDTO;
import si.fri.prpo.skupina02.entitete.Kosarica;
import si.fri.prpo.skupina02.entitete.Uporabnik;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.KosaricaZrno;
import si.fri.prpo.skupina02.storitve.upravljanje.UpravljanjeKosariceZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("kosarica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, PUT, DELETE")
public class KosaricaVir {
    @Inject
    private KosaricaZrno kosaricaZrno;

    @Inject
    private UpravljanjeKosariceZrno upravljanjeKosariceZrno;

    @Context
    protected UriInfo uriInfo;

    @BeleziKlice
    @GET
    @Operation(summary = "Pridobi košarice", description = "Vrne košarice.")
    @APIResponses({
            @APIResponse(
                    description = "Košarice",
                    responseCode = "200 OK",
                    content = @Content(
                            schema = @Schema(allOf = Kosarica.class),
                            encoding = @Encoding(headers = {
                                    @Header(name = "X-Total-Count", description = "Število vrnjenih košaric")
                            }
                            )
                    )
            )
    })
    public Response pridobiKosarice() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        var entitete = kosaricaZrno.get(query);
        var entitete_count = kosaricaZrno.getCount(query);
        return Response
                .ok(entitete)
                .header("X-Total-Count", entitete_count)
                .build();
    }

    @BeleziKlice
    @GET
    @Path("{id}")
    @Operation(summary = "Pridobi košarico", description = "Vrne košarico z id.")
    @APIResponses({
            @APIResponse(
                    description = "Košarica",
                    responseCode = "200 OK",
                    content = @Content(
                            schema = @Schema(implementation = Kosarica.class)
                    )
            ),
            @APIResponse (
                    description = "Košarica ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
    public Response pridobiKosarico(@PathParam("id") Integer id) {
        Kosarica kosarica = kosaricaZrno.getById(id);

        if(kosarica != null) {
            return Response.ok(kosarica).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @BeleziKlice
    @POST
    @Operation(summary = "Ustvari košarico", description = "Ustvari novo košarico")
    @APIResponses({
            @APIResponse(
                    description = "Košarica",
                    responseCode = "200 OK",
                    content = @Content(
                            schema = @Schema(implementation = Kosarica.class)
                    )
            ),
            @APIResponse (
                    description = "Napaka pri ustvarjanju košarice",
                    responseCode = "400 BAD REQUEST"
            )
    })
    public Response dodajKosarico(UstvariKosaricoDTO ustvariKosaricoDTO){

        var kosarica = upravljanjeKosariceZrno.ustvariKosarico(ustvariKosaricoDTO);

        if (kosarica == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        return Response
                .status(Response.Status.CREATED)
                .entity(kosaricaZrno.addKosarica(kosarica))
                .build();
    }

    @BeleziKlice
    @DELETE
    @Path("{id}")
    @Operation(summary = "Odstrani košarico", description = "Odstrani košarico")
    @APIResponses({
            @APIResponse(
                    description = "Košarica odstranjena",
                    responseCode = "200 OK"
            ),
            @APIResponse (
                    description = "Košarica ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
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
    @Operation(summary = "Posodobi košarico", description = "Posodobi košarico")
    @APIResponses({
            @APIResponse(
                    description = "Košarica posodobljena",
                    responseCode = "200 OK"
            ),
            @APIResponse (
                    description = "Košarica ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
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
