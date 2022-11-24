package dtos;

import si.fri.prpo.skupina02.entitete.Izdelek;
import si.fri.prpo.skupina02.entitete.IzdelekVTrgovini;
import si.fri.prpo.skupina02.entitete.Kosarica;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

public class IzdelekDTO implements Serializable {
    private Integer id;

    private String ime;

    private String kategorija;

    private List<Integer> v_trgovinah;

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

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public List<Integer> getV_trgovinah() {
        return v_trgovinah;
    }

    public void setV_trgovinah(List<Integer> v_trgovinah) {
        this.v_trgovinah = v_trgovinah;
    }

    public List<Integer> getKosarice() {
        return kosarice;
    }

    public void setKosarice(List<Integer> kosarice) {
        this.kosarice = kosarice;
    }
}
