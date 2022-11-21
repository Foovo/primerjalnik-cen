package dtos;

import si.fri.prpo.skupina02.entitete.Izdelek;

import java.util.List;

public class KosaricaDTO {
    private Integer id;
    private Integer uporabnikId;
    private List<Integer> izdelki;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUporabnikId() {
        return uporabnikId;
    }

    public void setUporabnikId(Integer uporabnikId) {
        this.uporabnikId = uporabnikId;
    }

    public List<Integer> getIzdelki() {
        return izdelki;
    }

    public void setIzdelki(List<Integer> izdelki) {
        this.izdelki = izdelki;
    }
}
