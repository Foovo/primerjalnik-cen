package si.fri.prpo.skupina02.storitve;

import si.fri.prpo.skupina02.entitete.IzdelekVTrgovini;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.logging.Logger;

public class IzdelekVTrgoviniZrno {

    private Logger log = Logger.getLogger(IzdelekVTrgoviniZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Initialization of " + IzdelekVTrgoviniZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destruction of " + IzdelekVTrgoviniZrno.class.getSimpleName());
    }

    @PersistenceContext(name = "primerjalnik-cen-jpa")
    private EntityManager em;

    @Transactional
    public IzdelekVTrgovini getById(int id) {
        return em.find(IzdelekVTrgovini.class, id);
    }

    @Transactional
    public void deleteIzdelekVTrgovini(int id) {
        IzdelekVTrgovini izdelek = getById(id);

        if(izdelek != null) {
            em.remove(izdelek);
        }
    }

    @Transactional
    public void addIzdelekVTrgovini(IzdelekVTrgovini izdelek) {
        if(izdelek != null) {
            em.persist(izdelek);
        }
    }

    @Transactional
    public void updateIzdelekVTrgovini(IzdelekVTrgovini izdelek, int id) {
        IzdelekVTrgovini i = getById(id);
        if(i != null && izdelek != null) {
            izdelek.setId(id);
            em.merge(i);
        }
    }
}
