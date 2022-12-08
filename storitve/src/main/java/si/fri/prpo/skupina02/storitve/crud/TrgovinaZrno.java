package si.fri.prpo.skupina02.storitve.crud;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina02.entitete.IzdelekVTrgovini;
import si.fri.prpo.skupina02.entitete.Trgovina;
import si.fri.prpo.skupina02.entitete.Uporabnik;

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

    public List<Trgovina> get(QueryParameters query) {
        return JPAUtils.queryEntities(em, Trgovina.class, query);
    }

    @Transactional
    public long getCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Trgovina.class, query);
    }

    @Transactional
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
    public boolean deleteTrgovina(int id) {
        Trgovina trgovina = getById(id);

        if(trgovina != null) {
            em.remove(trgovina);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addTrgovina(Trgovina trgovina) {
        if(trgovina != null) {
            em.persist(trgovina);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateTrgovina(Trgovina trgovina) {
        if (trgovina == null) return false;
        var i = getById(trgovina.getId());
        if(i == null) return false;

        em.merge(trgovina);
        return true;
    }
}
