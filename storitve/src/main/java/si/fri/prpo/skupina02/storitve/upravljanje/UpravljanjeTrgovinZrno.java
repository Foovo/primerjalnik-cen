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
public class UpravljanjeTrgovinZrno {
    private Logger log = Logger.getLogger(UpravljanjeTrgovinZrno.class.getName());

    @PostConstruct
    private void init() {
        log.info("Initialization of " + UpravljanjeTrgovinZrno.class.getSimpleName());
    }

    @PreDestroy
    private void destroy() {
        log.info("Destruction of " + UpravljanjeTrgovinZrno.class.getSimpleName());
    }

    @Inject
    private TrgovinaZrno trgovinaZrno;

    @Transactional
    public Trgovina ustvariTrgovino(UstvariTrgovinoDTO ustvariTrgovinoDTO) {
        if (ustvariTrgovinoDTO.getIme().isEmpty()) {
            log.warning("Trgovina ima prazno ime");
        }
        if (ustvariTrgovinoDTO.getLokacija().isEmpty()) {
            log.warning("Trgovina ima prazen lokacijo");
        }

        var trgovina = new Trgovina();

        trgovina.setIme(ustvariTrgovinoDTO.getIme());
        trgovina.setLokacija(ustvariTrgovinoDTO.getLokacija());
        trgovina.setIzdelki_v_trgovini(new ArrayList<IzdelekVTrgovini>());

        trgovinaZrno.addTrgovina(trgovina);
        return trgovina;
    }

    @Transactional
    public void odstraniTrgovino(OdstraniTrgovinoDTO odstraniTrgovinoDTO) {
        var t = trgovinaZrno.getById(odstraniTrgovinoDTO.getId());

        if (t == null) {
            log.warning("Trgovina za zbrisati ne obstaja");
            return;
        }

        trgovinaZrno.deleteTrgovina(odstraniTrgovinoDTO.getId());
    }
}
