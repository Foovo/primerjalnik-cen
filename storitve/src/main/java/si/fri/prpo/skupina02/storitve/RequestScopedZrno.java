package si.fri.prpo.skupina02.storitve;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import java.util.UUID;
import java.util.logging.Logger;

@RequestScoped
public class RequestScopedZrno {
    private Logger log = Logger.getLogger(RequestScopedZrno.class.getName());

    @PostConstruct
    private void init() {
        id = UUID.randomUUID();
        log.info("Initialization of " + RequestScopedZrno.class.getSimpleName() + " [" + id + "]");
    }

    @PreDestroy
    private void destroy() {
        log.info("Destruction of " + RequestScopedZrno.class.getSimpleName() + " [" + id + "]");
    }

    public UUID getId() {
        return id;
    }

    private UUID id;
}
