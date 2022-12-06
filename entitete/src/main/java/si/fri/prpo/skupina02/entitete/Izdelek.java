package si.fri.prpo.skupina02.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.*;


@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Izdelek.getAll", query = "SELECT i FROM Izdelek i"),
                @NamedQuery(name = "Izdelek.getAllFromKategorija", query = "SELECT i FROM Izdelek i WHERE i.kategorija = ?1"),
                @NamedQuery(name = "Izdelek.getAllFromIme", query = "SELECT i FROM Izdelek i WHERE i.ime = ?1"),
                @NamedQuery(name = "Izdelek.getById", query = "SELECT i FROM Izdelek i WHERE i.id = ?1")
        })
public class Izdelek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    private Kategorija kategorija;

    @JsonbTransient
    @OneToMany(mappedBy = "izdelek")
    private List<IzdelekVTrgovini> v_trgovinah;

    @JsonbTransient
    @ManyToMany(mappedBy = "izdelki")
    private List<Kosarica> kosarice;

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

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public List<IzdelekVTrgovini> getIzdelekVVsehtrgovinah() {
        return v_trgovinah;
    }

    public void setIzdelekVTrgovinah(List<IzdelekVTrgovini> v_trgovinah) {
        this.v_trgovinah = v_trgovinah;
    }

    public List<Kosarica> getKosarice() {
        return kosarice;
    }

    public void setKosarice(List<Kosarica> kosarice) {
        this.kosarice = kosarice;
    }

    public enum Kategorija {
        Gospodinjstvo,
        Racunalnistvo,
        Telefonija,
        Oprema_dom,
        Vrt_in_orodje,
        Igrace,
        Drogerija,
        Kozmetika,
        Zdravje,
        Moda,
        Sport_in_prosti_cas,
        Igre_in_razvedrilo,
        Avto_moto
    }

    @Override
    public String toString() {
        return "Izdelek{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", kategorija=" + kategorija +
                ", v_trgovinah=" + v_trgovinah +
                ", kosarice=" + kosarice +
                '}';
    }
}
