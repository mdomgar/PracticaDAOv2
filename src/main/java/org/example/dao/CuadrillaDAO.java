package org.example.dao;

import org.example.entidades.Cuadrilla;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CuadrillaDAO {
    Cuadrilla save(Cuadrilla cuadrilla) throws SQLException;
    Cuadrilla findOne(int id) throws SQLException;
    List<Cuadrilla> findAll() throws SQLException;
    void update (Cuadrilla cuadrilla) throws SQLException;
    void delete (int id) throws SQLException;
}
