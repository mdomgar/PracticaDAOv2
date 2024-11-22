package org.example.dao;

import org.example.entidades.Produccion;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProduccionDAO {
    Produccion save(Produccion produccion) throws SQLException;
    Produccion findOne(int id) throws SQLException;
    List<Produccion> findAll() throws SQLException;
    void update (Produccion produccion) throws SQLException;
    void delete (int id) throws SQLException;
}
