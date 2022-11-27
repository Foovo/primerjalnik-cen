package si.fri.prpo.skupina02.dtos;

import java.io.Serializable;

public class OdstraniIzdelekIzKosariceDTO implements Serializable {

    Integer kosaricaId, izdelekId;

    public Integer getKosaricaId() {
        return kosaricaId;
    }

    public void setKosaricaId(Integer kosaricaId) {
        this.kosaricaId = kosaricaId;
    }

    public Integer getIzdelekId() {
        return izdelekId;
    }

    public void setIzdelekId(Integer izdelekId) {
        this.izdelekId = izdelekId;
    }
}
