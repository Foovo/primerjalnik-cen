package si.fri.prpo.skupina02.dtos;

import java.io.Serializable;

public class DodajIzdelekVKosaricoDTO implements Serializable {

    public Integer getIzdelekId() {
        return izdelekId;
    }

    public void setIzdelekId(Integer izdelekId) {
        this.izdelekId = izdelekId;
    }

    public Integer getKosaricaId() {
        return kosaricaId;
    }

    public void setKosaricaId(Integer kosaricaId) {
        this.kosaricaId = kosaricaId;
    }

    Integer izdelekId, kosaricaId;
}
