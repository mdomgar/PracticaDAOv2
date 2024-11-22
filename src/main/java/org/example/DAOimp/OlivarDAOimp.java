package org.example.DAOimp;

import org.example.conexion.FactoriaConexion;
import org.example.dao.OlivarDAO;
import org.example.entidades.Cuadrilla;
import org.example.entidades.Olivar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OlivarDAOimp implements OlivarDAO {
    private static final String SAVE_QUERY = "INSERT INTO Olivar (ubicacion, hectareas, produccionAnual) VALUES (?, ?, ?)";
    private static final String FIND_ONE_QUERY = "Select * from Olivar WHERE id=?";
    private static final String FIND_ALL_QUERY = "Select * from Olivar";
    private static final String UPDATE_QUERY = "UPDATE Olivar SET ubicacion = ?, hectareas = ?, produccionAnual WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Olivar WHERE id = ?";

    private Connection connection;

    public OlivarDAOimp() {
        this.connection = FactoriaConexion.getConnection();
    }

    public List<Integer> getOlivaresIdByCuadrilla(int id) throws SQLException {
        List<Integer> olivaresIds = new ArrayList<>();
        String sql = "Select olivar_id from Cuadrilla_Olivar where cuadrilla_id= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                olivaresIds.add(resultSet.getInt("olivar_id"));
            }
        }
        return olivaresIds;
    }

    @Override
    public Olivar save(Olivar olivar) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {

            statement.setString(1, olivar.getUbicacion());
            statement.setDouble(2, olivar.getHectareas());
            statement.setDouble(3, olivar.getProduccionAnual());
            statement.executeUpdate();

        }

        return olivar;
    }

    @Override
    public Olivar findOne(int id) throws SQLException {
        Olivar olivar = null;
        CuadrillaDAOimp cuadrillaDAOimp = new CuadrillaDAOimp();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ONE_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                olivar = new Olivar(
                        resultSet.getInt("id"),
                        resultSet.getString("ubicacion"),
                        resultSet.getDouble("hectareas"),
                        resultSet.getDouble("produccionAnual"),
                        cuadrillaDAOimp.getIdCuadrillasBySupervisorId(  resultSet.getInt("id"))
                );
            }
            return olivar;
        }
    }


    @Override
    public List<Olivar> findAll() throws SQLException {
        List<Olivar> olivares = new ArrayList<>();
        CuadrillaDAOimp cuadrillaDAOimp = new CuadrillaDAOimp();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                olivares.add(new Olivar(
                        resultSet.getInt("id"),
                        resultSet.getString("ubicacion"),
                        resultSet.getDouble("hectareas"),
                        resultSet.getDouble("produccionAnual"),
                        cuadrillaDAOimp.getIdCuadrillasBySupervisorId(  resultSet.getInt("id"))
                ));
            }
        }
        return olivares;
    }

    @Override
    public void update(Olivar olivar) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, olivar.getUbicacion());
            statement.setDouble(2, olivar.getHectareas());
            statement.setDouble(3, olivar.getProduccionAnual());
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
        }

    }

    @Override
    public void setOlivarCuadrilla(int cuadrilla_id, int olivar_id) throws SQLException {
        String query = "INSERT INTO Cuadrilla_Olivar (cuadrilla_id, olivar_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cuadrilla_id);
            statement.setInt(2, olivar_id);
            statement.executeUpdate();
        }
    }
}
