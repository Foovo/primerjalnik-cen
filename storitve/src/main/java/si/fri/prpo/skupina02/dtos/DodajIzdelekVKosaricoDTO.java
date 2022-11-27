package si.fri.prpo.skupina02.dtos;

public class DodajIzdelekVKosaricoDTO {

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
