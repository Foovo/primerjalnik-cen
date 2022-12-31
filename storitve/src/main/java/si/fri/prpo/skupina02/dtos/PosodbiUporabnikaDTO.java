package si.fri.prpo.skupina02.dtos;

import java.io.Serializable;

public class PosodbiUporabnikaDTO implements Serializable {
    Integer id;
    String ime;
    String priimek;

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
}
