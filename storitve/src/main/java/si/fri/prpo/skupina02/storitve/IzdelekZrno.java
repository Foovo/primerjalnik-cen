package si.fri.prpo.skupina02.storitve;

import si.fri.prpo.skupina02.entitete.Izdelek;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@ApplicationScoped
public class IzdelekZrno {

    private Logger log = Logger.getLogger(IzdelekZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Initialization of " + IzdelekZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destruction of " + IzdelekZrno.class.getSimpleName());
    }

    @PersistenceContext(name = "primerjalnik-cen-jpa")
    private EntityManager em;

    @Transactional
    public Izdelek getById(int id) {
        return em.find(Izdelek.class, id);
    }

    @Transactional
    public void deleteIzdelek(int id) {
        Izdelek izdelek = getById(id);

        if(izdelek != null) {
            em.remove(izdelek);
        }
    }

    @Transactional
    public void addIzdelek(Izdelek izdelek) {
        if(izdelek != null) {
            em.persist(izdelek);
        }
    }

    @Transactional
    public void updateKosarica(Izdelek izdelek, int id) {
        Izdelek i = getById(id);
        if(i != null && izdelek != null) {
            izdelek.setId(id);
            em.merge(i);
        }
    }
}
