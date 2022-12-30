package si.fri.prpo.skupina02.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Kosarica.getAll", query = "SELECT k FROM Kosarica k"),
                @NamedQuery(name = "Kosarica.getKosaricaById", query = "SELECT k FROM Kosarica k WHERE k.id = ?1"),
                @NamedQuery(name = "Kosarica.getKosaricaByUporabnik", query = "SELECT k FROM Kosarica k WHERE k.uporabnik = ?1"),
                @NamedQuery(name = "Kosarica.getIzdelkiByUporabnik", query = "SELECT k.izdelki FROM Kosarica k WHERE k.uporabnik = ?1")
        })
public class Kosarica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Uporabnik uporabnik;

    @ManyToMany(fetch = FetchType.EAGER)
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
