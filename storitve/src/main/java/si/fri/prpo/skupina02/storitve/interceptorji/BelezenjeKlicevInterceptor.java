package si.fri.prpo.skupina02.storitve.interceptorji;

import si.fri.prpo.skupina02.storitve.anotacije.BeleziKlice;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    @AroundInvoke
    public void BelezenjeKlicev() {

    }
}
