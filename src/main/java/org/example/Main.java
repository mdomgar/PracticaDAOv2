package org.example;

import org.example.DAOimp.*;
import org.example.conexion.FactoriaConexion;
import org.example.dao.AlmazaraDAO;
import org.example.dao.ProduccionDAO;
import org.example.entidades.*;
import org.example.marshalling.MarshallingJSON;
import org.example.marshalling.MarshallingXML;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 10;

        //Declaro las implementaciones de las DAO para poder usarlas sin declararlas dentro del case una y otra vez
        CuadrillaDAOimp cuadrillaDAOimp = new CuadrillaDAOimp();
        TrabajadorDAOimp trabajadorDAOimp = new TrabajadorDAOimp();
        AlmazaraDAOimp almazaraDAOimp = new AlmazaraDAOimp();
        ProduccionDAOimp produccionDAOimp = new ProduccionDAOimp();
        OlivarDAOimp olivarDAOimp = new OlivarDAOimp();
        do {
            System.out.println("Escoja una de las opciones:" + "\n1.Mostrar los trabajadores de una determinada cuadrilla." + "\n2.Mostrar las cuadrillas que supervisa un determinado trabajador." + "\n3.Mostrar los olivares donde trabaja una determinada cuadrilla." + "\n4.Mostrar las cuadrillas que trabajan en un determinado olivar." + "\n5.Mostrar las almazaras donde lleva aceituna una determinada cuadrilla." + "\n6.Mostrar la producción en una fecha concreta, de una cuadrilla concreta en una almazara concreta." + "\n7.Mostrar la producción hasta una determinada fecha, de una determinada almazara." + "\n8.Mostrar la producción hasta una determinada fecha, de un determinado olivar (en todas las almazaras y de todas las cuadrillas que trabajan allí)." + "\n9.Mostrar la producción hasta una determinada fecha, de una cuadrilla determinada (en todas las almazaras y de todos los olivares en los que trabaja dicha cuadrilla)." + "\n10.Pasar los datos de la base de datos a XML." + "\n11.Unmarshalling XML." + "\n12.Marshalling JSON." + "\n13.Unmarshalling JSON." + "\n14.Create Trigger." + "\n15.Probar Trigger." + "\n0.Salir");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Introduce la id de la cuadrilla");
                    int id = sc.nextInt();
                    List<Integer> trabajadorList = cuadrillaDAOimp.getListIdTrabajadorByCuadrillaId(id);
                    List<Trabajador> trabajadoresCuadrilla = new ArrayList<>();
                    if (!trabajadorList.isEmpty()) {
                        for (Integer n : trabajadorList) {
                            trabajadoresCuadrilla.add(trabajadorDAOimp.findOne(n));
                        }
                        System.out.println(trabajadoresCuadrilla);
                    } else {
                        System.err.println("No se han encontrado cuadrillas con la id: " + id);
                    }

                    System.out.println();
                    break;
                case 2:
                    System.out.println("Introduce la id del supervisor");
                    id = sc.nextInt();
                    List<Cuadrilla> cuadrillas = cuadrillaDAOimp.getCuadrillasBySupervisorId(id);
                    if (!cuadrillas.isEmpty()) {
                        for (Cuadrilla cuadrilla : cuadrillas) {
                            System.out.println("Cuadrilla: " + cuadrilla.getId() + " " + cuadrilla.getNombre());
                        }
                    } else {
                        System.err.println("No se han encontrado supervisores con la id: " + id);
                    }
                    System.out.println();
                    break;
                case 3:
                    System.out.println("Introduce la id de una cuadrilla");
                    id = sc.nextInt();
                    List<Integer> olivaresIds = olivarDAOimp.getOlivaresIdByCuadrilla(id);
                    List<Olivar> olivares = new ArrayList<>();
                    if (!olivaresIds.isEmpty()) {
                        for (Integer n : olivaresIds) {
                            olivares.add(olivarDAOimp.findOne(n));
                        }
                        for (Olivar olivar : olivares) {
                            System.out.println("Olivar: " + olivar.getId() + " " + olivar.getUbicacion() + " " + olivar.getHectareas() + " " + olivar.getProduccionAnual());
                        }
                    } else {
                        System.err.println("No se han encontrado olivares donde trabaja la cuadrilla con id: " + id);
                    }
                    System.out.println();
                    break;
                case 4:
                    System.out.println("Introduce la id de un olivar");
                    id = sc.nextInt();

                    cuadrillas = cuadrillaDAOimp.getCuadrillasByOlivarId(id);
                    if (!cuadrillas.isEmpty()) {
                        for (Cuadrilla cuadrilla : cuadrillas) {
                            System.out.println("Cuadrilla: " + cuadrilla.getId() + " " + cuadrilla.getNombre() + " " + cuadrilla.getSupervisor_id());
                        }
                    } else {
                        System.err.println("No se han encontrado cuadrillas que trabajen el Olivar con id: " + id);
                    }
                    System.out.println();
                    break;
                case 5:
                    System.out.println("Introduce la id de una cuadrilla");
                    id = sc.nextInt();
                    List<Almazara> almazaras = produccionDAOimp.getAlmazaraByCuadrillaId(id);
                    if (!almazaras.isEmpty()) {
                        almazaras.forEach(System.out::println);
                    } else {
                        System.err.println("La cuadrilla con id: " + id + "no ha entregado aceituna en ninguna Almazara");
                    }
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Introduce la id de una Cuadrilla");
                    id = sc.nextInt();
                    System.out.println("Introduce la id de una Almazara");
                    int idAlmazara = sc.nextInt();
                    System.out.println("Introduce una fecha 'yyyy-MM-dd'");
                    int produccion = 0;
                    LocalDate fecha;
                    try {
                        fecha = LocalDate.parse(sc.next());
                        produccion = produccionDAOimp.getProduccionByFechaCuadrillaAlmazaraId(fecha, id, idAlmazara);
                        if (produccion != 0) {
                            System.out.println("Produccion: " + produccion);
                        } else {
                            System.err.println("No se ha encontrado Produciones con estos parametros");
                        }
                        System.out.println();
                    } catch (Exception e) {
                        System.err.println("El formato de fecha no es correcto");
                        break;
                    }
                    break;

                case 7:
                    System.out.println("Introduce la id de una Almazara");
                    idAlmazara = sc.nextInt();
                    System.out.println("Introduce una fecha 'yyyy-MM-dd'");
                    try {

                        fecha = LocalDate.parse(sc.next());
                        produccion = produccionDAOimp.getProduccionByAlmazaraIdTillFecha(idAlmazara, fecha);
                        if (produccion != 0) {
                            System.out.println("Produccion: " + produccion);
                        } else {
                            System.err.println("No se ha encontrado Produciones con estos parametros");
                        }
                        System.out.println();
                    } catch (Exception e) {
                        System.err.println("El formato de fecha no es correcto");
                        break;
                    }
                    break;
                case 8:
                    System.out.println("Introduce la id de un Olivar");
                    id = sc.nextInt();
                    System.out.println("Introduce una fecha 'yyyy-MM-dd'");
                    try {
                        fecha = LocalDate.parse(sc.next());
                        produccion = produccionDAOimp.getProduccionByOlivarIdTillFecha(id, fecha);
                        if (produccion != 0) {
                            System.out.println("Produccion: " + produccion);
                        } else {
                            System.err.println("No se ha encontrado Produciones con estos parametros");
                        }
                        System.out.println();
                    } catch (Exception e) {
                        System.err.println("El formato de fecha no es correcto");
                        break;
                    }
                    break;
                case 9:
                    System.out.println("Introduce la id de una cuadrilla");
                    id = sc.nextInt();
                    System.out.println("Introduce una fecha 'yyyy-MM-dd'");
                    try {
                        fecha = LocalDate.parse(sc.next());
                        produccion = produccionDAOimp.getProduccionByCuadrillaIdTillFecha(id, fecha);
                        if (produccion != 0) {
                            System.out.println("Produccion: " + produccion);
                        } else {
                            System.err.println("No se ha encontrado Produciones con estos parametros");
                        }
                        System.out.println();
                    } catch (Exception e) {
                        System.err.println("El formato de fecha no es correcto");
                        break;
                    }
                    break;
                case 10:
                    List<Almazara> almazarasList = almazaraDAOimp.findAll();
                    List<Cuadrilla> cuadrillaList = cuadrillaDAOimp.findAll();
                    List<Olivar> olivarList = olivarDAOimp.findAll();
                    List<Produccion> produccionList = produccionDAOimp.findAll();
                    List<Trabajador> trabajadoresList = trabajadorDAOimp.findAll();

                    campañaAceituna campañaAceituna = new campañaAceituna(almazarasList, cuadrillaList, olivarList, produccionList, trabajadoresList);

                    System.out.println("Introduce el nombre del archivo, debe acabar en .xml");
                    String xmlFilePath = sc.next();
                    MarshallingXML.marshall(campañaAceituna, xmlFilePath);
                    break;
                case 11:
                    System.out.println("Introduce el nombre del archivo, debe acabar en .xml");
                    xmlFilePath = sc.next();
                    campañaAceituna = MarshallingXML.unmarshall(xmlFilePath);
                    System.out.println(campañaAceituna);
                    break;

                case 12:
                    almazarasList = almazaraDAOimp.findAll();
                    cuadrillaList = cuadrillaDAOimp.findAll();
                    olivarList = olivarDAOimp.findAll();
                    produccionList = produccionDAOimp.findAll();
                    trabajadoresList = trabajadorDAOimp.findAll();

                    campañaAceituna = new campañaAceituna(almazarasList, cuadrillaList, olivarList, produccionList, trabajadoresList);

                    System.out.println("Introduce el nombre del archivo, debe acabar en .json");
                    String jsonPath = sc.next();
                    MarshallingJSON.marshall(campañaAceituna, jsonPath);
                    break;
                case 13:
                    System.out.println("Introduce el nombre del archivo, debe acabar en .json");
                    jsonPath = sc.next();
                    campañaAceituna = MarshallingJSON.unmarshall(jsonPath);
                    System.out.println(campañaAceituna);
                    break;
                case 14:
                    FactoriaConexion.createTrigger();
                    break;
                case 15:
                    Produccion produccionTrigger = new Produccion(1, 1, 1, LocalDate.of(2024, 11, 22), -200.30);
                    produccionDAOimp.save(produccionTrigger);
                    break;
            }
        } while (opcion != 0);

    }
}