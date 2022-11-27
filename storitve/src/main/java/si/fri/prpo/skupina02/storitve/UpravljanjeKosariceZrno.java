package si.fri.prpo.skupina02.storitve;

import dtos.IzdelekDTO;
import si.fri.prpo.skupina02.dtos.KosaricaDTO;
import si.fri.prpo.skupina02.entitete.Izdelek;
import si.fri.prpo.skupina02.entitete.Kosarica;
import si.fri.prpo.skupina02.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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

    @Inject
    private IzdelekZrno izdelekZrno;

    @Inject
    private KosaricaZrno kosaricaZrno;

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

    @Transactional
    public void odstraniKosarico(KosaricaDTO kosaricaDTO) {
        Kosarica kosarica = kosaricaZrno.getById(kosaricaDTO.getId());

        if(kosarica == null) {
            log.info("Ne najdem kosarice upsi ¯\\_(ツ)_/¯");
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
    public void odstraniIzdelekIzKosarice(KosaricaDTO kosaricaDTO, IzdelekDTO izdelekDTO) {
        Kosarica kosarica = kosaricaZrno.getById(kosaricaDTO.getId());
        if(kosarica == null) {
            log.info("Ne najdem kosarice upsi ¯\\_(ツ)_/¯");
            return;
        }

        Izdelek izdelek = izdelekZrno.getById(izdelekDTO.getId());
        if(izdelek == null) {
            log.info("Ne najdem izdelka.");
            return;
        }
        kosarica.getIzdelki().remove(izdelek);
    }

    @Transactional
    public void odstraniKosarico(Integer id) {
        var k = kosaricaZrno.getById(id);

        if (k == null) {
            log.warning("Kosarica za zbrisati ne obstaja");
            return;
        }

        kosaricaZrno.deleteKosarica(id);
    }

    @Transactional
    public List<Izdelek> vrniIzdelkeZaUporabniskoIme(String uime) {
        Uporabnik u = uporabnikZrno.getByUporabniskoIme(uime);

        if (u == null) {
            log.warning("Uporabnisko ime za kosarice ne obstaja");
            return null;
        }

        return kosaricaZrno.getIzdelkiInKosaricaByUporabniki(u);
    }
}
