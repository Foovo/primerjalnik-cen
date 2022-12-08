package si.fri.prpo.skupina02.storitve.crud;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina02.entitete.Izdelek;
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
    public List<IzdelekVTrgoviniZrno> getAll() {
        return em.createNamedQuery("IzdelekVTrgovini.getAll")
                .getResultList();
    }

    @Transactional
    public List<IzdelekVTrgoviniZrno> getAllWhereCenaLessThan(double cena) {
        return em.createNamedQuery("IzdelekVTrgovini.getAllWhereCenaLessThan")
                .setParameter(1, cena)
                .getResultList();
    }

    @Transactional
    public List<IzdelekVTrgoviniZrno> getAllFromTrgovina(Trgovina t) {
        return em.createNamedQuery("IzdelekVTrgovini.getAllFromTrgovina")
                .setParameter(1, t)
                .getResultList();
    }

    @Transactional
    public List<IzdelekVTrgoviniZrno> getCenaFromTrgovina(Izdelek i, Trgovina t) {
        return em.createNamedQuery("IzdelekVTrgovini.getCenaFromTrgovina")
                .setParameter(1, i)
                .setParameter(2, t)
                .getResultList();
    }

    @Transactional
    public List<IzdelekVTrgovini> get(QueryParameters query) {
        return JPAUtils.queryEntities(em, IzdelekVTrgovini.class, query);
    }

    @Transactional
    public long getCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, IzdelekVTrgovini.class, query);
    }

    @Transactional
    public IzdelekVTrgovini getById(int id) {
        return em.find(IzdelekVTrgovini.class, id);
    }

    @Transactional
    public boolean deleteIzdelekVTrgovini(int id) {
        IzdelekVTrgovini izdelek = getById(id);

        if(izdelek != null) {
            em.remove(izdelek);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addIzdelekVTrgovini(IzdelekVTrgovini izdelek) {
        if(izdelek != null) {
            em.persist(izdelek);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateIzdelekVTrgovini(IzdelekVTrgovini izdelek) {
        if (izdelek == null) return false;
        var i = getById(izdelek.getId());
        if(i == null) return false;

        em.merge(izdelek);
        return true;
    }
}
