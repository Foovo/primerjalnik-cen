package si.fri.prpo.skupina02.storitve.interceptorji;

import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;
import si.fri.prpo.skupina02.storitve.crud.BelezenjeKlicevZrno;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    @Inject
    BelezenjeKlicevZrno belezenjeKlicevZrno;

    @AroundInvoke
    public Object BelezenjeKlicev(InvocationContext context) throws Exception {
        String method = context.getMethod().toString();
        belezenjeKlicevZrno.count(method);

        return context.proceed();
    }
}
