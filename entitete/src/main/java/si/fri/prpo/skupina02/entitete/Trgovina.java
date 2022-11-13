package si.fri.prpo.skupina02.entitete;

import javax.persistence.*;
import java.util.List;

@Entity
public class Trgovina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    private String lokacija;

    @OneToMany(mappedBy = "trgovina")
    private List<IzdelekVTrgovini> izdelki_v_trgovini;

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

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public List<IzdelekVTrgovini> getIzdelki_v_trgovini() {
        return izdelki_v_trgovini;
    }

    public void setIzdelki_v_trgovini(List<IzdelekVTrgovini> izdelki_v_trgovini) {
        this.izdelki_v_trgovini = izdelki_v_trgovini;
    }

    @Override
    public String toString() {
        return "Trgovina{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", lokacija='" + lokacija + '\'' +
                ", izdelki_v_trgovini=" + izdelki_v_trgovini +
                '}';
    }
}
