package si.fri.prpo.skupina02.api.v1.exceptionmappers;

import si.fri.prpo.skupina02.storitve.izjeme.NeveljavenUstvariUporabnikaDTOIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeveljavenUstvariUporabnikaDTOIzjemaExceptionMapper implements ExceptionMapper<NeveljavenUstvariUporabnikaDTOIzjema> {
    @Override
    public Response toResponse(NeveljavenUstvariUporabnikaDTOIzjema exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\": \"" + exception.getMessage() + "\"}")
                .build();
    }
}
