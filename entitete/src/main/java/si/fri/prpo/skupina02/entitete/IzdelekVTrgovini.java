package si.fri.prpo.skupina02.entitete;

import javax.persistence.*;

@Entity
public class IzdelekVTrgovini {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double cena;

    @ManyToOne
    private Izdelek izdelek;

    @ManyToOne
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
