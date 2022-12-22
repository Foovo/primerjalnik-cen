package si.fri.prpo.skupina02.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.eclipse.microprofile.openapi.annotations.*;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Encoding;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import si.fri.prpo.skupina02.dtos.UstvariUporabnikaDTO;
import si.fri.prpo.skupina02.entitete.Uporabnik;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.anotacije.ValidirajUstvariUporabnikaDTO;
import si.fri.prpo.skupina02.storitve.crud.UporabnikZrno;
import si.fri.prpo.skupina02.storitve.upravljanje.UpravljanjeUporabnikaZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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

    @Inject
    private UpravljanjeUporabnikaZrno upravljanjeUporabnikaZrno;

    @Context
    protected UriInfo uriInfo;

    @BeleziKlice
    @GET
    @Operation(summary = "Pridobi uporabnike", description = "Vrne uporabnike.")
    @APIResponses({
        @APIResponse(
            description = "Uporabniki",
            responseCode = "200 OK",
            content = @Content(
                schema = @Schema(allOf = Uporabnik.class),
                encoding = @Encoding(headers = {
                    @Header(name = "X-Total-Count", description = "Število vrnjenih uporabnikov")
                }
            )
            )
        )
    })
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
    @Operation(summary = "Pridobi uporabnika", description = "Vrne uporabnika z id.")
    @APIResponses({
            @APIResponse(
                    description = "Uporabniki",
                    responseCode = "200 OK",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class)
                    )
            ),
            @APIResponse (
                    description = "Uporabnik ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
    public Response pridobiUporabnika(@PathParam("id") Integer id) {
        Uporabnik uporabnik = uporabnikZrno.getById(id);

        if(uporabnik != null) {
            return Response.ok(uporabnik).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @BeleziKlice
    @POST
    @Operation(summary = "Ustvari uporabnika", description = "Ustvari novega uporabnika")
    @APIResponses({
            @APIResponse(
                    description = "Uporabniki",
                    responseCode = "201 CREATED",
                    content = @Content(
                            schema = @Schema(implementation = Uporabnik.class)
                    )
            ),
            @APIResponse (
                    description = "Napaka pri ustvarjanju uporabnika",
                    responseCode = "400 BAD REQUEST"
            )
    })
    @ValidirajUstvariUporabnikaDTO
    public Response dodajUporabnika(UstvariUporabnikaDTO ustvariUporabnikaDTO){

        var uporabnik = upravljanjeUporabnikaZrno.ustvariUporabnika(ustvariUporabnikaDTO);

        if (uporabnik == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        return Response
                .status(Response.Status.CREATED)
                .entity(uporabnik)
                .build();
    }

    @BeleziKlice
    @DELETE
    @Path("{id}")
    @Operation(summary = "Odstrani uporabnika", description = "Odstrani uporabnika")
    @APIResponses({
            @APIResponse(
                    description = "Uporabnik odstranjen",
                    responseCode = "200 OK"
            ),
            @APIResponse (
                    description = "Uporabnik ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
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
    @Operation(summary = "Posodobi uporabnika", description = "Posodobi uporabnika")
    @APIResponses({
            @APIResponse(
                    description = "Uporabnik posodobljen",
                    responseCode = "200 OK"
            ),
            @APIResponse (
                    description = "Uporabnik ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })
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

    @BeleziKlice
    @GET
    @Path("{id}/avatar")
    @Operation(summary = "Pridobi avatar uporabnika", description = "Vrne avatar uporabnika z id.")
    @APIResponses({
            @APIResponse(
                    description = "Avatar",
                    responseCode = "200 OK"
            ),
            @APIResponse (
                    description = "Uporabnik ne obstaja",
                    responseCode = "404 NOT FOUND"
            )
    })

    @Produces(MediaType.APPLICATION_SVG_XML)
    public Response pridobiAvatar(@PathParam("id") Integer id) {
        Uporabnik uporabnik = uporabnikZrno.getById(id);
        if(uporabnik != null) {
            Client client = ClientBuilder.newClient();
            String url = "https://avatars.dicebear.com/api/avataaars/"+ uporabnik.getUporabnisko_ime()+".svg";
            String name = client.target(url)
                    .request(MediaType.TEXT_PLAIN)
                    .get(String.class);

            return Response.ok(name).type(new MediaType("image", "svg+xml")).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
