package si.fri.prpo.skupina02.dtos;

import java.io.Serializable;

public class DodajIzdelekVTrgovinoDTO implements Serializable {
    Integer trgovinaId;
    Integer izdelekId;
    Double cena;

    public Integer getTrgovinaId() {
        return trgovinaId;
    }

    public void setTrgovinaId(Integer trgovinaId) {
        this.trgovinaId = trgovinaId;
    }

    public Integer getIzdelekId() {
        return izdelekId;
    }

    public void setIzdelekId(Integer izdelekId) {
        this.izdelekId = izdelekId;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }
}
