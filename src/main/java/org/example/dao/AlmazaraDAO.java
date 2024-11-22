package org.example.dao;

import org.example.entidades.Almazara;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AlmazaraDAO {
    Almazara save(Almazara almazara) throws SQLException;
    Almazara findOne(int id) throws SQLException;
    List<Almazara> findAll() throws SQLException;
    void update (Almazara almazara) throws SQLException;
    void delete (int id) throws SQLException;
}
