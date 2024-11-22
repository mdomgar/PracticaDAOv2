package org.example.entidades;

import org.example.DAOimp.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "produccion")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "cuadrilla_id", "olivar_id", "almazara_id", "fecha", "cantidadRecolectada"})
public class Produccion {

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "cuadrilla_id")
    private int cuadrilla_id;

    @XmlElement(name = "olivar_id")
    private int olivar_id;

    @XmlElement(name = "almazara_id")
    private int almazara_id;

    @XmlElement(name = "fecha")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha;

    @XmlElement(name = "cantidadRecolectada")
    private double cantidadRecolectada;

    public Produccion() {
    }

    public Produccion(int id, int cuadrilla_id, int olivar_id, int almazara_id, LocalDate fecha, double cantidadRecolectada) {
        this.id = id;
        this.cuadrilla_id = cuadrilla_id;
        this.olivar_id = olivar_id;
        this.almazara_id = almazara_id;
        this.fecha = fecha;
        this.cantidadRecolectada = cantidadRecolectada;
    }

    public Produccion(int cuadrilla_id, int olivar_id, int almazara_id, LocalDate fecha, double cantidadRecolectada) {
        this.cuadrilla_id = cuadrilla_id;
        this.olivar_id = olivar_id;
        this.almazara_id = almazara_id;
        this.fecha = fecha;
        this.cantidadRecolectada = cantidadRecolectada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCuadrilla_id() {
        return cuadrilla_id;
    }

    public void setCuadrilla_id(int cuadrilla_id) {
        this.cuadrilla_id = cuadrilla_id;
    }

    public int getOlivar_id() {
        return olivar_id;
    }

    public void setOlivar_id(int olivar_id) {
        this.olivar_id = olivar_id;
    }

    public int getAlmazara_id() {
        return almazara_id;
    }

    public void setAlmazara_id(int almazara_id) {
        this.almazara_id = almazara_id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getCantidadRecolectada() {
        return cantidadRecolectada;
    }

    public void setCantidadRecolectada(double cantidadRecolectada) {
        this.cantidadRecolectada = cantidadRecolectada;
    }

    @Override
    public String toString() {
        return "Produccion{" +
                "id=" + id +
                ", cuadrilla_id=" + cuadrilla_id +
                ", olivar_id=" + olivar_id +
                ", almazara_id=" + almazara_id +
                ", fecha=" + fecha +
                ", cantidadRecolectada=" + cantidadRecolectada +
                '}';
    }
}
