package si.fri.prpo.skupina02.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "Uporabnik.getAll", query = "select u from Uporabnik u"),
        @NamedQuery(name = "Uporabnik.getByImePriimek", query = "SELECT u FROM Uporabnik u WHERE u.ime = ?1 AND u.priimek = ?2"),
        @NamedQuery(name = "Uporabnik.getByUporabniskoIme", query = "SELECT u FROM Uporabnik u WHERE u.uporabnisko_ime = ?1"),
        @NamedQuery(name = "Uporabnik.getById", query = "SELECT u FROM Uporabnik u WHERE u.id = ?1")
})
public class Uporabnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    private String priimek;

    private String uporabnisko_ime;

    @JsonbTransient
    @OneToMany(mappedBy = "uporabnik")
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

    public List<Kosarica> getKosarice() {
        return kosarice;
    }

    public void setKosarice(List<Kosarica> kosarice) {
        this.kosarice = kosarice;
    }

    @Override
    public String toString() {
        return "Uporabnik{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", priimek='" + priimek + '\'' +
                ", uporabnisko_ime='" + uporabnisko_ime + '\'' +
                '}';
    }
}
