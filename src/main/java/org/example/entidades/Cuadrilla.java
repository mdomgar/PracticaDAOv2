package org.example.entidades;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "cuadrilla")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "nombre", "supervisor_id", "idOlivares", "idTrabajadores"})
public class Cuadrilla {

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "nombre")
    private String nombre;

    @XmlElement(name = "supervisor_id")
    private int supervisor_id;

    @XmlElementWrapper(name = "idolivares")
    @XmlElement(name = "idOlivar")
    private List<Integer> idOlivares;

    @XmlElementWrapper(name = "idtrabajadores")
    @XmlElement(name = "idTrabajador")
    private List<Integer> idTrabajadores;

    public Cuadrilla() {
    }

    public Cuadrilla(int id, String nombre, int supervisor_id, List<Integer> idOlivares, List<Integer> idTrabajadores) {
        this.id = id;
        this.nombre = nombre;
        this.supervisor_id = supervisor_id;
        this.idOlivares = idOlivares;
        this.idTrabajadores = idTrabajadores;
    }

    public Cuadrilla(int id, String nombre, int supervisor_id) {
        this.id = id;
        this.nombre = nombre;
        this.supervisor_id = supervisor_id;
    }

    public Cuadrilla(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Cuadrilla(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(int supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    public List<Integer> getIdOlivares() {
        return idOlivares;
    }

    public void setIdOlivares(List<Integer> idOlivares) {
        this.idOlivares = idOlivares;
    }

    public List<Integer> getIdTrabajadores() {
        return idTrabajadores;
    }

    public void setIdTrabajadores(List<Integer> idTrabajadores) {
        this.idTrabajadores = idTrabajadores;
    }

    @Override
    public String toString() {
        return "Cuadrilla{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", supervisor_id=" + supervisor_id +
                ", idOlivares=" + idOlivares +
                ", idTrabajadores=" + idTrabajadores +
                '}';
    }
}
