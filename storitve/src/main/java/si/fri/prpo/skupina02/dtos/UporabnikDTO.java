package si.fri.prpo.skupina02.dtos;

import java.util.List;

public class UporabnikDTO {
    private Integer id;
    private String ime;
    private String priimek;
    private String uporabnisko_ime;
    private List<Integer> kosarice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getUporabnisko_ime() {
        return uporabnisko_ime;
    }

    public void setUporabnisko_ime(String uporabnisko_ime) {
        this.uporabnisko_ime = uporabnisko_ime;
    }

    public List<Integer> getKosarice() {
        return kosarice;
    }

    public void setKosarice(List<Integer> kosarice) {
        this.kosarice = kosarice;
    }
}
