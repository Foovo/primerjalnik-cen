package si.fri.prpo.skupina02.storitve.crud;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ApplicationScoped
public class BelezenjeKlicevZrno {
    Map<String, Integer> counter = new HashMap<>();
    Logger logger = Logger.getLogger(BelezenjeKlicevZrno.class.getName());
    public void count(String method) {
        counter.merge(method, 1, Integer::sum);
        logger.info(counter.toString());
    }
}
