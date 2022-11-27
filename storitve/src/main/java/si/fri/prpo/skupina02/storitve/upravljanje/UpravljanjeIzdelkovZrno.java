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
public class UpravljanjeIzdelkovZrno {
    private Logger log = Logger.getLogger(UpravljanjeIzdelkovZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Initialization of " + UpravljanjeIzdelkovZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destruction of " + UpravljanjeIzdelkovZrno.class.getSimpleName());
    }

    @Inject
    private TrgovinaZrno trgovinaZrno;
    @Inject
    private IzdelekZrno izdelekZrno;
    @Inject
    private IzdelekVTrgoviniZrno izdelekVTrgoviniZrno;

    @Transactional
    public Izdelek ustvariIzdelek(UstvariIzdelekDTO ustvariIzdelekDTO) {
        if (ustvariIzdelekDTO.getIme().isEmpty()) {
            log.warning("Izdelek ima prazno ime");
        }


        var izdelek = new Izdelek();
        izdelek.setIme(ustvariIzdelekDTO.getIme());
        izdelek.setKategorija(ustvariIzdelekDTO.getKategorija());
        izdelek.setIzdelekVTrgovinah(new ArrayList<IzdelekVTrgovini>());
        izdelek.setKosarice(new ArrayList<Kosarica>());
        izdelekZrno.addIzdelek(izdelek);
        return izdelek;
    }

    @Transactional
    public void odstraniTrgovino(OdstraniIzdelekDTO odstraniIzdelekDTO) {
        var t = trgovinaZrno.getById(odstraniIzdelekDTO.getId());

        if (t == null) {
            log.warning("Izdelek za zbrisati ne obstaja");
            return;
        }

        trgovinaZrno.deleteTrgovina(odstraniIzdelekDTO.getId());
    }

    @Transactional
    public IzdelekVTrgovini dodajIzdelekVTrgovino(DodajIzdelekVTrgovinoDTO dodajIzdelekVTrgovinoDTO) {
        if (dodajIzdelekVTrgovinoDTO.getCena() <= 0) {
            log.warning("Cena za izdelek ni pozitivna");
        }

        var t = trgovinaZrno.getById(dodajIzdelekVTrgovinoDTO.getTrgovinaId());

        if (t == null) {
            log.severe("Trgovina za dodat izdelek ne obstaja");
            return null;
        }

        var i = izdelekZrno.getById(dodajIzdelekVTrgovinoDTO.getIzdelekId());

        if (i == null) {
            log.severe("Izdelek v trgovini ne obstaja");
            return null;
        }

        var izdelekVTrgovini = new IzdelekVTrgovini();
        izdelekVTrgovini.setIzdelek(i);
        izdelekVTrgovini.setTrgovina(t);
        izdelekVTrgovini.setCena(dodajIzdelekVTrgovinoDTO.getCena());

        izdelekVTrgoviniZrno.addIzdelekVTrgovini(izdelekVTrgovini);
        return izdelekVTrgovini;
    }
}
