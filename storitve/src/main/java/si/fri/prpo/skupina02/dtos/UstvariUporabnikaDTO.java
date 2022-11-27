package si.fri.prpo.skupina02.dtos;

import java.io.Serializable;

public class UstvariUporabnikaDTO implements Serializable {
    private String ime;
    private String priimek;
    private String uporabnisko_ime;
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
}
