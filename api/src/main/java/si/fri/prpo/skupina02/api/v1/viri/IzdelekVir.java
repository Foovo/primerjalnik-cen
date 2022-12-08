package si.fri.prpo.skupina02.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Encoding;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.skupina02.dtos.UstvariIzdelekDTO;
import si.fri.prpo.skupina02.entitete.Izdelek;
import si.fri.prpo.skupina02.entitete.Uporabnik;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.IzdelekZrno;
import si.fri.prpo.skupina02.storitve.upravljanje.UpravljanjeIzdelkovZrno;

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

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;

    @Context
    protected UriInfo uriInfo;

    @BeleziKlice
    @GET
    @Operation(summary = "Pridobi izdelke", description = "Vrne izdelke.")
    @APIResponses({
            @APIResponse(
                    description = "Izdelki",
                    responseCode = "200 OK",
                    content = @Content(
                            schema = @Schema(allOf = Izdelek.class),
                            encoding = @Encoding(headers = {
                                    @Header(name = "X-Total-Count", description = "Število vrnjenih izdelkov")
                            }
                            )
                    )
            )
    })
    public Response pridobiIzdelke() {
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
    @Operation(summary = "Pridobi izdelek", description = "Vrne izdelek z id.")
    @APIResponses({
            @APIResponse(
                    description = "Izdelk",
                    responseCode = "200 OK",
                    content = @Content(
                            schema = @Schema(implementation = Izdelek.class)
                    )
            ),
            @APIResponse (
                    description = "Izdelek ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
    public Response pridobiIzdelek(@PathParam("id") Integer id) {
        Izdelek izdelek = izdelekZrno.getById(id);

        if(izdelek != null) {
            return Response.ok(izdelek).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @BeleziKlice
    @POST
    @Operation(summary = "Ustvari izdelek", description = "Ustvari nov izdelek")
    @APIResponses({
            @APIResponse(
                    description = "Izdelek",
                    responseCode = "200 OK",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class)
                    )
            ),
            @APIResponse (
                    description = "Napaka pri ustvarjanju izdelka",
                    responseCode = "400 BAD REQUEST"
            )
    })
    public Response dodajIzdelek(UstvariIzdelekDTO ustvariIzdelekDTO){

        var izdelek = upravljanjeIzdelkovZrno.ustvariIzdelek(ustvariIzdelekDTO);

        if (izdelek == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        return Response
                .status(Response.Status.CREATED)
                .entity(izdelekZrno.addIzdelek(izdelek))
                .build();
    }

    @BeleziKlice
    @DELETE
    @Path("{id}")
    @Operation(summary = "Odstrani izdelek", description = "Odstrani izdelek")
    @APIResponses({
            @APIResponse(
                    description = "Izdelek odstranjen",
                    responseCode = "200 OK"
            ),
            @APIResponse (
                    description = "Izdelek ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
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
    @Operation(summary = "Posodobi izdelek", description = "Posodobi izdelek")
    @APIResponses({
            @APIResponse(
                    description = "Izdelek posodobljen",
                    responseCode = "200 OK"
            ),
            @APIResponse (
                    description = "Izdelek ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
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
