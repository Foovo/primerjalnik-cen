package si.fri.prpo.skupina02.dtos;

import javax.json.bind.annotation.JsonbTransient;
import java.io.Serializable;

public class UstvariKosaricoDTO implements Serializable {
    private Integer uporabnik_id;

    public Integer getUporabnik_id() {
        return uporabnik_id;
    }

    public void setUporabnik_id(Integer uporabnik_id) {
        this.uporabnik_id = uporabnik_id;
    }

}
