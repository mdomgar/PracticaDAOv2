package org.example.entidades;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "campañaaceituna")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "almazaraList", "cuadrillaList", "olivarList", "produccionList", "trabajadorList"})
public class campañaAceituna {

    @XmlElementWrapper(name = "almazaraList")
    private List<Almazara> almazaraList;

    @XmlElementWrapper(name = "cuadrillaList")
    private List<Cuadrilla> cuadrillaList;

    @XmlElementWrapper(name = "olivarList")
    private List<Olivar> olivarList;

    @XmlElementWrapper(name = "produccionList")
    private List<Produccion> produccionList;

    @XmlElementWrapper(name = "trabajadorList")
    private List<Trabajador> trabajadorList;

    public campañaAceituna() {
    }

    public campañaAceituna(List<Almazara> almazaraList, List<Cuadrilla> cuadrillaList, List<Olivar> olivarList, List<Produccion> produccionList, List<Trabajador> trabajadorList) {
        this.almazaraList = almazaraList;
        this.cuadrillaList = cuadrillaList;
        this.olivarList = olivarList;
        this.produccionList = produccionList;
        this.trabajadorList = trabajadorList;
    }

    public List<Almazara> getAlmazaraList() {
        return almazaraList;
    }

    public List<Cuadrilla> getCuadrillaList() {
        return cuadrillaList;
    }

    public List<Olivar> getOlivarList() {
        return olivarList;
    }

    public List<Produccion> getProduccionList() {
        return produccionList;
    }

    public List<Trabajador> getTrabajadorList() {
        return trabajadorList;
    }

    @Override
    public String toString() {
        return "campañaAceituna{" +
                "almazaraList=" + almazaraList +
                ", cuadrillaList=" + cuadrillaList +
                ", olivarList=" + olivarList +
                ", produccionList=" + produccionList +
                ", trabajadorList=" + trabajadorList +
                '}';
    }
}
