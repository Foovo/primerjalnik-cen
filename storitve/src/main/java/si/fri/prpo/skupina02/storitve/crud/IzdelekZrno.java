package si.fri.prpo.skupina02.storitve.crud;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina02.entitete.Izdelek;
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
    public List<Izdelek> getAll() {
        return em.createNamedQuery("Izdelek.getAll")
                .getResultList();
    }

    @Transactional
    public List<Izdelek> get(QueryParameters query) {
        return JPAUtils.queryEntities(em, Izdelek.class, query);
    }

    @Transactional
    public long getCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Izdelek.class, query);
    }

    @Transactional
    public List<Izdelek> getAllFromKategorija(Izdelek.Kategorija k) {
        return em.createNamedQuery("Izdelek.getAllFromKategorija")
                .setParameter(1, k)
                .getResultList();
    }

    @Transactional
    public List<Izdelek> getAllFromIme(String ime) {
        return em.createNamedQuery("Izdelek.getAllFromIme")
                .setParameter(1, ime)
                .getResultList();
    }

    @Transactional
    public Izdelek getById(int id) {
        return em.find(Izdelek.class, id);
    }

    @Transactional
    public boolean deleteIzdelek(int id) {
        Izdelek izdelek = getById(id);

        if(izdelek != null) {
            em.remove(izdelek);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addIzdelek(Izdelek izdelek) {
        if(izdelek != null) {
            em.persist(izdelek);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateIzdelek(Izdelek izdelek) {
        if (izdelek == null) return false;
        var i = getById(izdelek.getId());
        if(i == null) return false;

        em.merge(izdelek);
        return true;
    }
}
