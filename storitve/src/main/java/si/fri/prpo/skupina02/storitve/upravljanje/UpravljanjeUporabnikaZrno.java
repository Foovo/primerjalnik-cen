package si.fri.prpo.skupina02.storitve.upravljanje;

import si.fri.prpo.skupina02.dtos.*;
import si.fri.prpo.skupina02.entitete.*;
import si.fri.prpo.skupina02.storitve.crud.*;

import javax.annotation.*;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeUporabnikaZrno {
    private Logger log = Logger.getLogger(UpravljanjeUporabnikaZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Initialization of " + UpravljanjeUporabnikaZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destruction of " + UpravljanjeUporabnikaZrno.class.getSimpleName());
    }

    @Inject
    private UporabnikZrno uporabnikZrno;
    @Inject
    KosaricaZrno kosaricaZrno;

    @Transactional
    public Uporabnik ustvariUporabnika(UstvariUporabnikaDTO ustvariUporabnikaDTO) {
        if (ustvariUporabnikaDTO.getIme().isEmpty()) {
            log.warning("Uporabnik ima prazno ime");
        }
        if (ustvariUporabnikaDTO.getPriimek().isEmpty()) {
            log.warning("Uporabnik ima prazen priimek");
        }
        if (uporabnikZrno.getByUporabniskoIme(ustvariUporabnikaDTO.getUporabnisko_ime()) != null) {
            log.severe("Uporabnik z tem uporabniskim imenom ze obstaja");
            return null;
        }

        Uporabnik uporabnik = new Uporabnik();

        uporabnik.setIme(ustvariUporabnikaDTO.getIme());
        uporabnik.setPriimek(ustvariUporabnikaDTO.getPriimek());
        uporabnik.setUporabnisko_ime(ustvariUporabnikaDTO.getUporabnisko_ime());
        uporabnik.setKosarice(new ArrayList<Kosarica>());

        uporabnikZrno.addUporabnik(uporabnik);
        return uporabnik;
    }

    @Transactional
    public void odstraniUporabnika(OdstraniUporabnikaDTO odstraniUporabnikaDTO) {
        var u = uporabnikZrno.getById(odstraniUporabnikaDTO.getId());

        if (u == null) {
            log.warning("Uporabnik za zbrisati ne obstaja");
            return;
        }

        uporabnikZrno.deleteUporabnik(odstraniUporabnikaDTO.getId());
    }

    @Transactional
    public void odstraniKosarice(OdstraniKosariceUporabnikaDTO odstraniKosariceUporabnikaDTO) {
        Uporabnik u = uporabnikZrno.getById(odstraniKosariceUporabnikaDTO.getId());

        if (u == null) {
            log.warning("Uporabnik za zbrisati kosarice ne obstaja");
            return;
        }

        for (Kosarica k : u.getKosarice()) {
            kosaricaZrno.deleteKosarica(k.getId());
        }
    }
}
