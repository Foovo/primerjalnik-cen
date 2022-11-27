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

    @Inject
    private IzdelekZrno izdelekZrno;

    @Inject
    private KosaricaZrno kosaricaZrno;

    @Transactional
    public Kosarica ustvariKosarico(UstvariKosaricoDTO ustvariKosaricoDTO) {
        Uporabnik uporabnik = uporabnikZrno.getById(ustvariKosaricoDTO.getUporabnikId());

        if(uporabnik == null) {
            log.severe("Kosarice ni mogoče ustvariti. Uporabnik ne obstaja.");
            return  null;
        }


        Kosarica kosarica = new Kosarica();

        kosarica.setUporabnik(uporabnik);
        kosarica.setIzdelki(new ArrayList<Izdelek>());

        kosaricaZrno.addKosarica(kosarica);
        return kosarica;
    }

    @Transactional
    public void odstraniKosarico(OdstraniKosaricoDTO odstraniKosaricoDTO) {
        Kosarica kosarica = kosaricaZrno.getById(odstraniKosaricoDTO.getId());

        if(kosarica == null) {
            log.warning("Ne najdem kosarice upsi ¯\\_(ツ)_/¯");
            return;
        }

        Uporabnik u = kosarica.getUporabnik();
        u.getKosarice().remove(kosarica);

        List<Izdelek> izdelki = kosarica.getIzdelki();
        for(var i : izdelki) {
            i.getKosarice().remove(kosarica);
        }

        kosaricaZrno.deleteKosarica(kosarica.getId());
    }

    @Transactional
    public void dodajIzdelekVKosarico(DodajIzdelekVKosaricoDTO dodajIzdelekVKosaricoDTO) {
        Kosarica kosarica = kosaricaZrno.getById(dodajIzdelekVKosaricoDTO.getKosaricaId());

        if(kosarica == null) {
            log.severe("Ne najdem kosarice upsi ¯\\_(ツ)_/¯");
            return;
        }

        Izdelek izdelek = izdelekZrno.getById(dodajIzdelekVKosaricoDTO.getIzdelekId());

        if(izdelek == null) {
            log.severe("Ne najdem izdelka upsi ¯\\_(ツ)_/¯");
            return;
        }

        var izdelki = kosarica.getIzdelki();

        for (var i : izdelki) {
            if (Objects.equals(i.getId(), izdelek.getId())) {
                log.warning("Izdelek je ze v kosarici");
                return;
            }
        }

        var novi_izdelki = new ArrayList<Izdelek>(izdelki);
        novi_izdelki.add(izdelek);

        kosarica.setIzdelki(novi_izdelki);
        kosaricaZrno.updateKosarica(kosarica);
    }

    @Transactional
    public void odstraniIzdelekIzKosarice(OdstraniIzdelekIzKosariceDTO odstraniIzdelekIzKosariceDTO) {
        Kosarica kosarica = kosaricaZrno.getById(odstraniIzdelekIzKosariceDTO.getKosaricaId());
        if(kosarica == null) {
            log.info("Ne najdem kosarice upsi ¯\\_(ツ)_/¯");
            return;
        }

        Izdelek izdelek = izdelekZrno.getById(odstraniIzdelekIzKosariceDTO.getIzdelekId());
        if(izdelek == null) {
            log.info("Ne najdem izdelka.");
            return;
        }
        kosarica.getIzdelki().remove(izdelek);
        kosaricaZrno.updateKosarica(kosarica);
    }
}
