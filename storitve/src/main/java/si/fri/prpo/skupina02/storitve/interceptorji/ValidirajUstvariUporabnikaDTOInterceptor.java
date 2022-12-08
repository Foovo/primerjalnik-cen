package si.fri.prpo.skupina02.storitve.interceptorji;

import si.fri.prpo.skupina02.dtos.UstvariTrgovinoDTO;
import si.fri.prpo.skupina02.dtos.UstvariUporabnikaDTO;
import si.fri.prpo.skupina02.storitve.ApplicationScopedZrno;
import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.BelezenjeKlicevZrno;
import si.fri.prpo.skupina02.storitve.izjeme.NeveljavenUstvariUporabnikaDTOIzjema;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Objects;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
@Priority(0)
public class ValidirajUstvariUporabnikaDTOInterceptor {
    private Logger log = Logger.getLogger(ValidirajUstvariUporabnikaDTOInterceptor.class.getName());

    @AroundInvoke
    public Object validirajUstvariUporabnikaDTO(InvocationContext context) throws Exception {
        try {
            if (context.getParameters().length != 1)
                throw new NeveljavenUstvariUporabnikaDTOIzjema("Napačno število parametrov");

            if (!(context.getParameters()[0] instanceof UstvariUporabnikaDTO dto))
                throw new NeveljavenUstvariUporabnikaDTOIzjema("DTO ni tipa UstvariUporabnikaDTO");

            if (dto.getIme() == null || Objects.equals(dto.getIme(), ""))
                throw new NeveljavenUstvariUporabnikaDTOIzjema("Neveljavno prazno ime");

            if (dto.getPriimek() == null || Objects.equals(dto.getPriimek(), ""))
                throw new NeveljavenUstvariUporabnikaDTOIzjema("Neveljaven prazen priimek");

            if (dto.getUporabnisko_ime() == null || dto.getUporabnisko_ime().length() < 5)
                throw new NeveljavenUstvariUporabnikaDTOIzjema("Uporabnisko ime mora imeti vsaj 5 znakov");

            if (dto.getUporabnisko_ime().contains(" "))
                throw new NeveljavenUstvariUporabnikaDTOIzjema("Uporabnisko ime ne sme vsebovati presledkov");

        } catch (NeveljavenUstvariUporabnikaDTOIzjema izjema) {
            log.severe(izjema.getMessage());
            throw izjema;
        }

        return context.proceed();
    }
}
