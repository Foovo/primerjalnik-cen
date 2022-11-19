package si.fri.prpo.skupina02.storitve;

import org.hibernate.Hibernate;
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
public class UporabnikZrno {

    private Logger log = Logger.getLogger(UporabnikZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Initialization of " + UporabnikZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destruction of " + UporabnikZrno.class.getSimpleName());
    }

    @PersistenceContext(name = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Uporabnik> getAll() {
        Query q = em.createNamedQuery("Uporabnik.getAll");
        return q.getResultList();
    }

    public List<Uporabnik> getIzdelkiCriteriaAPI() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Uporabnik> q = cb.createQuery(Uporabnik.class);
        Root<Uporabnik> root = q.from(Uporabnik.class);
        q.select(root);

        Query query = em.createQuery(q);
        return query.getResultList();
    }

    @Transactional
    public Uporabnik getById(int id) {
        return em.find(Uporabnik.class, id);
    }

    @Transactional
    public void deleteUporabnik(int id) {
        Uporabnik uporabnik = getById(id);

        if(uporabnik != null) {
            em.remove(uporabnik);
        }
    }

    @Transactional
    public void addUporabnik(Uporabnik uporabnik) {
        if(uporabnik != null) {
            em.persist(uporabnik);
        }
    }

    @Transactional
    public void updateUporabnik(Uporabnik uporabnik, int id) { //objekt z id posodobi z uporabnikom
        Uporabnik u = getById(id);
        if(u != null && uporabnik != null) {
            uporabnik.setId(id);
            em.merge(u);
        }
    }
}
