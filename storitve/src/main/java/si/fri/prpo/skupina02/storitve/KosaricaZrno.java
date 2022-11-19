package si.fri.prpo.skupina02.storitve;

import org.hibernate.Hibernate;
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


    /*@Transactional
    public List<Uporabnik> getAll() {
        List<Uporabnik> list = em.createNamedQuery("Uporabnik.getAll").getResultList();
        for (var u : list) {
            Hibernate.initialize(u.getKosarice());
        }

        return  list;
    }*/

    public List<Kosarica> getByUporabnik(Uporabnik u) {

        // implementacija
        Query q = em.createNamedQuery("Kosarica.getKosaricaByUporabnik");
        q.setParameter(1, u);
        return q.getResultList();
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
    public void updateKosarica(Kosarica kosarica, int id) {
        Kosarica k = getById(id);
        if(k != null && kosarica != null) {
            kosarica.setId(id);
            em.merge(k);
        }
    }
}
