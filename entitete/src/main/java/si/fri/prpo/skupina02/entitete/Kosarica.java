package si.fri.prpo.skupina02.entitete;

import javax.persistence.*;
import java.util.List;

@Entity
public class Kosarica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Uporabnik uporabnik;

    @ManyToMany
    @JoinTable(name = "izdelki_v_kosarici")
    private List<Izdelek> izdelki;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public List<Izdelek> getIzdelki() {
        return izdelki;
    }

    public void setIzdelki(List<Izdelek> izdelki) {
        this.izdelki = izdelki;
    }

    @Override
    public String toString() {
        return "Kosarica{" +
                "id=" + id +
                ", uporabnik=" + uporabnik +
                ", izdelki=" + izdelki +
                '}';
    }
}
