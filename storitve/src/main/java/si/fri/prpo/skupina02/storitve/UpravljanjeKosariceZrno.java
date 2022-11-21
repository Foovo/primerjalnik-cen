package si.fri.prpo.skupina02.storitve;

import dtos.KosaricaDTO;
import si.fri.prpo.skupina02.entitete.Izdelek;
import si.fri.prpo.skupina02.entitete.Kosarica;
import si.fri.prpo.skupina02.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeKosariceZrno {
    private Logger log = Logger.getLogger(UpravljanjeKosariceZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Initialization of " + UpravljanjeKosariceZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destruction of " + UpravljanjeKosariceZrno.class.getSimpleName());
    }

    @Inject
    private UporabnikZrno uporabnikZrno;

    @Inject IzdelekZrno izdelekZrno;

    @Inject KosaricaZrno kosaricaZrno;

    @Transactional
    public Kosarica ustvariKosarico(KosaricaDTO kosaricaDTO) {
        Uporabnik uporabnik = uporabnikZrno.getById(kosaricaDTO.getUporabnikId());

        if(uporabnik == null) {
            log.info("Kosarice ni mogoče ustvariti. Uporabnik ne obstaja.");
            return  null;
        }

        List<Izdelek> izdelki = new ArrayList<>();
        List<Integer> izdelkiId = kosaricaDTO.getIzdelki();
        for(Integer i : izdelkiId) {
            Izdelek izdelek = izdelekZrno.getById(i);
            if(izdelek != null) {
                izdelki.add(izdelek);
            }
        }

        Kosarica kosarica = new Kosarica();

        kosarica.setUporabnik(uporabnik);
        uporabnik.getKosarice().add(kosarica);

        kosarica.setIzdelki(izdelki);
        for(Izdelek i : izdelki) {
            i.getKosarice().add(kosarica);
        }

        kosaricaZrno.addKosarica(kosarica);
        return kosarica;
    }
}
