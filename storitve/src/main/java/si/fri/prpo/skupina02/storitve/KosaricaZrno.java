package si.fri.prpo.skupina02.storitve;

import org.hibernate.Hibernate;
import si.fri.prpo.skupina02.entitete.Izdelek;
import si.fri.prpo.skupina02.entitete.Kosarica;
import si.fri.prpo.skupina02.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.*;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class KosaricaZrno {

    private Logger log = Logger.getLogger(KosaricaZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Initialization of " + KosaricaZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destruction of " + KosaricaZrno.class.getSimpleName());
    }

    @PersistenceContext(name = "primerjalnik-cen-jpa")
    private EntityManager em;


    @Transactional
    public List<Kosarica> getAll() {
        return em.createNamedQuery("Kosarica.getAll")
                .getResultList();
    }

    public List<Kosarica> getByUporabnik(Uporabnik u) {
        return em.createNamedQuery("Kosarica.getKosaricaByUporabnik")
            .setParameter(1, u)
            .getResultList();
    }

    public List<Izdelek> getIzdelkiInKosaricaByUporabniki(Uporabnik u) {
        return em.createNamedQuery("Kosarica.getIzdelkiByUporabnik")
                .setParameter(1, u)
                .getResultList();
    }

    public List<Uporabnik> getIzdelkiCriteriaAPI() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Kosarica> q = cb.createQuery(Kosarica.class);
        Root<Kosarica> root = q.from(Kosarica.class);
        q.select(root);

        Query query = em.createQuery(q);
        return query.getResultList();
    }

    @Transactional
    public Kosarica getById(int id) {
        return em.find(Kosarica.class, id);
    }

    @Transactional
    public void deleteKosarica(int id) {
        Kosarica kosarica = getById(id);

        if(kosarica != null) {
            em.remove(kosarica);
        }
    }

    @Transactional
    public void addKosarica(Kosarica kosarica) {
        if(kosarica != null) {
            em.persist(kosarica);
        }
    }

    @Transactional
    public void updateKosarica(Kosarica kosarica) {
        if (kosarica == null) return;
        var i = getById(kosarica.getId());
        if(i == null) return;
        em.merge(kosarica);
    }
}
