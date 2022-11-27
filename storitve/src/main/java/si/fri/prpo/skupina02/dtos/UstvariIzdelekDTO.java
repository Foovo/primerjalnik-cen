package si.fri.prpo.skupina02.dtos;

import si.fri.prpo.skupina02.entitete.Izdelek;

import java.io.Serializable;

public class UstvariIzdelekDTO implements Serializable {
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Izdelek.Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Izdelek.Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    private String ime;
    private Izdelek.Kategorija kategorija;
}
