package si.fri.prpo.skupina02.storitve;

import org.hibernate.Hibernate;
import si.fri.prpo.skupina02.entitete.Uporabnik;

import javax.enterprise.context.*;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UporabnikZrno {
    @PersistenceContext(name = "primerjalnik-cen-jpa")
    private EntityManager em;

    @Transactional
    public List<Uporabnik> getAll() {
        List<Uporabnik> list = em.createNamedQuery("Uporabnik.getAll").getResultList();
        for (var u : list) {
            Hibernate.initialize(u.getKosarice());
        }

        return  list;
    }
}
