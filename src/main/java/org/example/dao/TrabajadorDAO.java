package org.example.dao;

import org.example.entidades.Trabajador;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TrabajadorDAO {
    Trabajador save (Trabajador trabajador) throws SQLException;
    Trabajador findOne(int id) throws SQLException;
    List<Trabajador> findAll() throws SQLException;
    void update (Trabajador trabajador) throws SQLException;
    void delete (int id) throws SQLException;
}
