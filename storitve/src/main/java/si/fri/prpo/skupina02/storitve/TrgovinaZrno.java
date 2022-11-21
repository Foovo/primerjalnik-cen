package si.fri.prpo.skupina02.storitve;

import si.fri.prpo.skupina02.entitete.Trgovina;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@ApplicationScoped
public class TrgovinaZrno {

    private Logger log = Logger.getLogger(TrgovinaZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Initialization of " + TrgovinaZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destruction of " + TrgovinaZrno.class.getSimpleName());
    }

    @PersistenceContext(name = "primerjalnik-cen-jpa")
    private EntityManager em;

    @Transactional
    public Trgovina getById(int id) {
        return em.find(Trgovina.class, id);
    }

    @Transactional
    public void deleteTrgovina(int id) {
        Trgovina trgovina = getById(id);

        if(trgovina != null) {
            em.remove(trgovina);
        }
    }

    @Transactional
    public void addTrgovina(Trgovina trgovina) {
        if(trgovina != null) {
            em.persist(trgovina);
        }
    }

    @Transactional
    public void updateTrgovina(Trgovina trgovina, int id) {
        Trgovina t = getById(id);
        if(t != null && trgovina != null) {
            trgovina.setId(id);
            em.merge(t);
        }
    }
}
