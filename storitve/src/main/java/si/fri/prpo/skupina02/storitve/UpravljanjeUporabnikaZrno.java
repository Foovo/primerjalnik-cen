package si.fri.prpo.skupina02.storitve;

import si.fri.prpo.skupina02.dtos.KosaricaDTO;
import si.fri.prpo.skupina02.dtos.UporabnikDTO;
import si.fri.prpo.skupina02.entitete.Izdelek;
import si.fri.prpo.skupina02.entitete.Kosarica;
import si.fri.prpo.skupina02.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
    public Uporabnik ustvariUporabnika(UporabnikDTO uporabnikDTO) {
        if (uporabnikDTO.getIme().isEmpty()) {
            log.warning("Uporabnik ima prazno ime");
        }
        if (uporabnikDTO.getPriimek().isEmpty()) {
            log.warning("Uporabnik ima prazen priimek");
        }
        if (uporabnikZrno.getByUporabniskoIme(uporabnikDTO.getUporabnisko_ime()) != null) {
            log.severe("Uporabnik z tem uporabniskim imenom ze obstaja");
            return null;
        }
        if (uporabnikDTO.getKosarice().size() != 0) {
            log.severe("Nov uporabnik ne mora imeti kosaric");
            return null;
        }

        Uporabnik uporabnik = new Uporabnik();

        uporabnik.setIme(uporabnikDTO.getIme());
        uporabnik.setPriimek(uporabnikDTO.getPriimek());
        uporabnik.setUporabnisko_ime(uporabnikDTO.getUporabnisko_ime());
        uporabnik.setKosarice(new ArrayList<Kosarica>());

        uporabnikZrno.addUporabnik(uporabnik);
        return uporabnik;
    }

    @Transactional
    public void odstraniUporabnika(UporabnikDTO uporabnikDTO) {
        var u = uporabnikZrno.getById(uporabnikDTO.getId());

        if (u == null) {
            log.warning("Uporabnik za zbrisati ne obstaja");
            return;
        }

        uporabnikZrno.deleteUporabnik(uporabnikDTO.getId());
    }

    @Transactional
    public void pocistiKosarice(UporabnikDTO u) {
        if (u == null) {
            log.warning("Uporabnik za zbrisati kosarice ne obstaja");
            return;
        }

        for (Integer k : u.getKosarice()) {
            kosaricaZrno.deleteKosarica(k);
        }
    }
}
