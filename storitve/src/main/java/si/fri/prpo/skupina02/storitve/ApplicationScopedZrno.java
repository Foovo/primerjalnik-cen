package si.fri.prpo.skupina02.storitve;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class ApplicationScopedZrno {
    private Logger log = Logger.getLogger(ApplicationScopedZrno.class.getName());

    @PostConstruct
    private void init() {
        id = UUID.randomUUID();
        log.info("Initialization of " + ApplicationScopedZrno.class.getSimpleName() + " [" + id + "]");
    }

    @PreDestroy
    private void destroy() {
        log.info("Destruction of " + ApplicationScopedZrno.class.getSimpleName() + " [" + id + "]");
    }

    public UUID getId() {
        return id;
    }

    private UUID id;
}
