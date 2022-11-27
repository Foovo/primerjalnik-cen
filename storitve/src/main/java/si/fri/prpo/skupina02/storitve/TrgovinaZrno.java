package si.fri.prpo.skupina02.storitve;

import si.fri.prpo.skupina02.entitete.Izdelek;
import si.fri.prpo.skupina02.entitete.IzdelekVTrgovini;
import si.fri.prpo.skupina02.entitete.Trgovina;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
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

    public List<Trgovina> getAll() {
        return em.createNamedQuery("Trgovina.getAll")
                .getResultList();
    }

    public List<String> getImeByLokacija(String lokacija) {
        return em.createNamedQuery("Trgovina.getImeByLokacija")
                .setParameter(1, lokacija)
                .getResultList();
    }

    public List<IzdelekVTrgovini> getIzdelkiById(int id) {
        return em.createNamedQuery("Trgovina.getIzdelkiById")
                .setParameter(1, id)
                .getResultList();
    }

    public List<String> getAllInLokacija(String lokacija) {
        return em.createNamedQuery("Trgovina.getAllInLokacija")
                .setParameter(1, lokacija)
                .getResultList();
    }

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
    public void updateTrgovina(Trgovina trgovina) {
        if (trgovina == null) return;
        var i = getById(trgovina.getId());
        if(i == null) return;
        em.merge(trgovina);
    }
}
