package si.fri.prpo.skupina02.dtos;

import java.io.Serializable;

public class UstvariTrgovinoDTO implements Serializable {
    private String ime;
    private String lokacija;
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }
}
