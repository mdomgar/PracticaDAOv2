package org.example.DAOimp;

import org.example.conexion.FactoriaConexion;
import org.example.dao.AlmazaraDAO;
import org.example.entidades.Almazara;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlmazaraDAOimp implements AlmazaraDAO {
    private Connection connection;

    public AlmazaraDAOimp() {
        connection = FactoriaConexion.getConnection();
    }

    private static final String SAVE_QUERY = "INSERT INTO Almazara (nombre, ubicacion, capacidad) VALUES (?, ? , ?)";
    private static final String FIND_ONE_QUERY = "Select * from Almazara WHERE id = ?";
    private static final String FIND_ALL_QUERY = "Select * from Almazara";
    private static final String UPDATE_QUERY = "UPDATE Almazara SET nombre = ?, ubicacion = ?, capacidad = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Almazara WHERE id = ?";

    @Override
    public Almazara save(Almazara almazara) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {

            statement.setString(1, almazara.getNombre());
            statement.setString(2, almazara.getUbicacion());
            statement.setDouble(3, almazara.getCapacidad());
            statement.executeUpdate();

        }
        return almazara;
    }

    public org.example.entidades.Almazara findOne(int id) throws SQLException {
        Almazara almazara = null;
        try (PreparedStatement statement = connection.prepareStatement(FIND_ONE_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                almazara = new Almazara(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("ubicacion"),
                        resultSet.getDouble("capacidad")
                );
            }

        }
        return almazara;
    }


    @Override
    public List<Almazara> findAll() throws SQLException {
        List<Almazara> almazaras = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY)) {
            while (resultSet.next()) {
                almazaras.add(new Almazara(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("ubicacion"),
                        resultSet.getDouble("capacidad")
                ));
            }
        }
        return almazaras;
    }

    @Override
    public void update(Almazara almazara) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, almazara.getNombre());
            statement.setString(2, almazara.getUbicacion());
            statement.setDouble(3, almazara.getCapacidad());
            statement.setInt(4, almazara.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }

    }
}
