package si.fri.prpo.skupina02.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "IzdelekVTrgovini.getAll", query = "SELECT i FROM IzdelekVTrgovini i"),
                @NamedQuery(name = "IzdelekVTrgovini.getAllWhereCenaLessThan", query = "SELECT i FROM IzdelekVTrgovini i WHERE i.cena < ?1"),
                @NamedQuery(name = "IzdelekVTrgovini.getAllFromTrgovina", query = "SELECT i FROM IzdelekVTrgovini i WHERE i.trgovina = ?1"),
                @NamedQuery(name = "IzdelekVTrgovini.getCenaFromTrgovina", query = "SELECT i.cena FROM IzdelekVTrgovini i WHERE i.izdelek = ?1 and i.trgovina = ?2")
        })
public class IzdelekVTrgovini {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double cena;

    @ManyToOne
    @JoinColumn(name = "izdelek_id")
    private Izdelek izdelek;

    @ManyToOne
    @JoinColumn(name = "trgovina_id")
    private Trgovina trgovina;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Izdelek getIzdelek() {
        return izdelek;
    }

    public void setIzdelek(Izdelek izdelek) {
        this.izdelek = izdelek;
    }

    public Trgovina getTrgovina() {
        return trgovina;
    }

    public void setTrgovina(Trgovina trgovina) {
        this.trgovina = trgovina;
    }

    @Override
    public String toString() {
        return "IzdelekVTrgovini{" +
                "id=" + id +
                ", cena=" + cena +
                ", izdelek=" + izdelek +
                ", trgovina=" + trgovina +
                '}';
    }
}
