package si.fri.prpo.skupina02.storitve;

import org.hibernate.Hibernate;
import si.fri.prpo.skupina02.entitete.Uporabnik;

import javax.enterprise.context.*;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UporabnikZrno {
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
}
