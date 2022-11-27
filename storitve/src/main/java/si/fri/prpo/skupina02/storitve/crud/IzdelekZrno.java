package si.fri.prpo.skupina02.storitve.crud;

import si.fri.prpo.skupina02.entitete.Izdelek;

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
    public void updateKosarica(Izdelek izdelek) {
        if (izdelek == null) return;
        var i = getById(izdelek.getId());
        if(i == null) return;
        em.merge(izdelek);
    }
}
