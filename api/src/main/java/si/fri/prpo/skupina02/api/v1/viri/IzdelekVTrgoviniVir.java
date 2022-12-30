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
import si.fri.prpo.skupina02.dtos.DodajIzdelekVTrgovinoDTO;
import si.fri.prpo.skupina02.entitete.IzdelekVTrgovini;
import si.fri.prpo.skupina02.entitete.Uporabnik;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.IzdelekVTrgoviniZrno;
import si.fri.prpo.skupina02.storitve.upravljanje.UpravljanjeIzdelkovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("izdelekVTrgovini")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, PUT, DELETE")
public class IzdelekVTrgoviniVir {
    @Inject
    private IzdelekVTrgoviniZrno izdelekVTrgoviniZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;

    @Context
    protected UriInfo uriInfo;

    @BeleziKlice
    @GET
    @Operation(summary = "Pridobi izdelke v trgovini", description = "Vrne izdelke v trgovini.")
    @APIResponses({
            @APIResponse(
                    description = "Izdelki v trgovini",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(allOf = IzdelekVTrgovini.class),
                            encoding = @Encoding(headers = {
                                    @Header(name = "X-Total-Count", description = "Število vrnjenih izdelkov v trgovini")
                            }
                            )
                    )
            )
    })
    public Response pridobiIzdelkeVTrgovini() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        var entitete = izdelekVTrgoviniZrno.get(query);
        var entitete_count = izdelekVTrgoviniZrno.getCount(query);
        return Response
                .ok(entitete)
                .header("X-Total-Count", entitete_count)
                .build();
    }

    @BeleziKlice
    @GET
    @Path("{id}")
    @Operation(summary = "Pridobi izdelek v trgovini", description = "Vrne izdelek v trgovini z id.")
    @APIResponses({
            @APIResponse(
                    description = "Izdelek v trgovini",
                    responseCode = "200",
                    content = @Content(
                            schema = @Schema(implementation = IzdelekVTrgovini.class)
                    )
            ),
            @APIResponse (
                    description = "Izdelek v trgovini ne obstaja",
                    responseCode = "404"
            )
    })
    public Response pridobiIzdelekVTrgovini(@PathParam("id") Integer id) {
        IzdelekVTrgovini izdelekVTrgovini = izdelekVTrgoviniZrno.getById(id);

        if(izdelekVTrgovini != null) {
            return Response.ok(izdelekVTrgovini).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @BeleziKlice
    @POST
    @Operation(summary = "Dodaj izdelek v trgovino", description = "Ustvari nov izdelek v trgovini")
    @APIResponses({
            @APIResponse(
                    description = "Izdelek v trgovini",
                    responseCode = "201",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class)
                    )
            ),
            @APIResponse (
                    description = "Napaka pri ustvarjanju izdelka v trgovini",
                    responseCode = "400"
            )
    })
    public Response dodajIzdelekVTrgovini(DodajIzdelekVTrgovinoDTO dodajIzdelekVTrgovinoDTO){

        var izdelekVTrgovini = upravljanjeIzdelkovZrno.dodajIzdelekVTrgovino(dodajIzdelekVTrgovinoDTO);

        if (izdelekVTrgovini == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        return Response
                .status(Response.Status.CREATED)
                .entity(izdelekVTrgoviniZrno.addIzdelekVTrgovini(izdelekVTrgovini))
                .build();
    }

    @BeleziKlice
    @DELETE
    @Path("{id}")
    @Operation(summary = "Odstrani izdelek iz trgovine", description = "Odstrani izdelek iz trgovine")
    @APIResponses({
            @APIResponse(
                    description = "Izdelek v trgovini odstranjen",
                    responseCode = "200"
            ),
            @APIResponse (
                    description = "Izdelek v trgovini ne obstaja",
                    responseCode = "404"
            )
    })
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
    @Operation(summary = "Posodobi izdelek v trgovini", description = "Posodobi izdelek v trgovini")
    @APIResponses({
            @APIResponse(
                    description = "Izdelek v trgovini posodobljen",
                    responseCode = "200"
            ),
            @APIResponse (
                    description = "Izdelek v trgovini ne obstaja",
                    responseCode = "404"
            )
    })
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
