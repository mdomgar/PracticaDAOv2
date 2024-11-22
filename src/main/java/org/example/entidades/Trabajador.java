package org.example.entidades;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "trabajador")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "nombre", "edad", "puesto", "salario", "idCuadrilla"})
public class Trabajador {

    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "nombre")
    private String nombre;

    @XmlElement(name = "edad")
    private int edad;

    @XmlElement(name = "puesto")
    private String puesto;

    @XmlElement(name = "salario")
    private double salario;

    @XmlElement(name = "idCuadrilla")
    private int idCuadrilla;


    public Trabajador() {
    }

    public Trabajador(int id, String nombre, int edad, String puesto, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.puesto = puesto;
        this.salario = salario;
    }

    public Trabajador(String nombre, int edad, String puesto, double salario) {
        this.nombre = nombre;
        this.edad = edad;
        this.puesto = puesto;
        this.salario = salario;
    }

    public Trabajador(int id, String nombre, int edad, String puesto, double salario, int idCuadrilla) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.puesto = puesto;
        this.salario = salario;
        this.idCuadrilla = idCuadrilla;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getIdCuadrilla() {
        return idCuadrilla;
    }

    public void setIdCuadrilla(int idCuadrilla) {
        this.idCuadrilla = idCuadrilla;
    }

    @Override
    public String toString() {
        return "Trabajador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", puesto='" + puesto + '\'' +
                ", salario=" + salario +
                ", idCuadrilla=" + idCuadrilla +
                '}';
    }
}
