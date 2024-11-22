package org.example.dao;

import org.example.entidades.Olivar;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OlivarDAO {
    Olivar save(Olivar olivar) throws SQLException;
    Olivar findOne(int id) throws SQLException;
    List<Olivar> findAll() throws SQLException;
    void update (Olivar olivar) throws SQLException;
    void delete (int id) throws SQLException;
    void setOlivarCuadrilla(int cuadrilla_id, int olivar_id) throws SQLException;
}
