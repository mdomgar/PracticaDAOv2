package org.example.DAOimp;

import org.example.conexion.FactoriaConexion;
import org.example.dao.CuadrillaDAO;
import org.example.entidades.Almazara;
import org.example.entidades.Cuadrilla;
import org.example.entidades.Trabajador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuadrillaDAOimp implements CuadrillaDAO {
    private static final String SAVE_QUERY = "INSERT INTO Cuadrilla (nombre) VALUES (?)";
    private static final String FIND_ONE_QUERY = "Select * from Cuadrilla WHERE id=?";
    private static final String FIND_ALL_QUERY = "Select * from Cuadrilla";
    private static final String UPDATE_QUERY = "UPDATE Cuadrilla SET nombre = ?, supervisor_id = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Cuadrilla WHERE id = ?";
    private Connection connection;


    public CuadrillaDAOimp() {
        connection = FactoriaConexion.getConnection();
    }

    @Override
    public Cuadrilla save(Cuadrilla cuadrilla) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {

            statement.setString(1, cuadrilla.getNombre());
            statement.executeUpdate();
        }

        return cuadrilla;
    }


    public void setSupervisorIdToCuadrilla(Cuadrilla cuadrilla, Trabajador supervisor) throws SQLException {
        String query = "UPDATE Cuadrilla SET supervisor_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, supervisor.getId());
            statement.setInt(2, cuadrilla.getId());
            statement.executeUpdate();
        }
    }

    public List<Integer> getListIdOlivaresByCuadrillaId(int id) throws SQLException {
        List<Integer> listIdOlivar = new ArrayList<>();
        String sql = "Select olivar_id from Cuadrilla_Olivar where cuadrilla_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listIdOlivar.add(resultSet.getInt("olivar_id"));
            }
        }
        return listIdOlivar;
    }

    public List<Cuadrilla> getCuadrillasByOlivarId(int id) throws SQLException {
        List<Cuadrilla> listCuadrilla = new ArrayList<>();
        String sql = "Select c.id, c.nombre, c.supervisor_id from Cuadrilla c INNER JOIN Cuadrilla_Olivar co on c.id=co.cuadrilla_id where co.olivar_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listCuadrilla.add(new Cuadrilla(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("supervisor_id")
                ));
            }
        }
        return listCuadrilla;
    }

    public List<Integer> getListIdTrabajadorByCuadrillaId(int id) throws SQLException {
        List<Integer> listIdTrabajador = new ArrayList<>();
        String sql = "Select trabajador_id from Cuadrilla_Trabajador where cuadrilla_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listIdTrabajador.add(resultSet.getInt("trabajador_id"));
            }
        }
        return listIdTrabajador;
    }

    public List<Cuadrilla> getCuadrillasBySupervisorId(int id) throws SQLException {
        List<Cuadrilla> listCuadrillas = new ArrayList<>();
        String sql = "Select * from Cuadrilla where supervisor_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                listCuadrillas.add(new Cuadrilla(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("supervisor_id")
                ));
            }
        }
        return listCuadrillas;
    }

    public List<Integer> getIdCuadrillasBySupervisorId(int id) throws SQLException {
        List<Integer> listIdCuadrillas = new ArrayList<>();
        String sql = "Select id from Cuadrilla where supervisor_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                listIdCuadrillas.add(resultSet.getInt("id"));
            }
        }
        return listIdCuadrillas;
    }

    @Override
    public Cuadrilla findOne(int id) throws SQLException {
        Cuadrilla cuadrilla = null;
        CuadrillaDAOimp cuadrillaDAOimp = new CuadrillaDAOimp();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ONE_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cuadrilla = new Cuadrilla(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("supervisor_id"),
                        cuadrillaDAOimp.getListIdOlivaresByCuadrillaId(resultSet.getInt("id")),
                        cuadrillaDAOimp.getListIdTrabajadorByCuadrillaId(resultSet.getInt("id"))
                );
            }

            return cuadrilla;
        }


    }

    @Override
    public List<Cuadrilla> findAll() throws SQLException {
        List<Cuadrilla> cuadrillas = new ArrayList<>();
        CuadrillaDAOimp cuadrillaDAOimp = new CuadrillaDAOimp();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cuadrillas.add(new Cuadrilla(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("supervisor_id"),
                        cuadrillaDAOimp.getListIdOlivaresByCuadrillaId(resultSet.getInt("id")),
                        cuadrillaDAOimp.getListIdTrabajadorByCuadrillaId(resultSet.getInt("id"))
                ));
            }
        }
        return cuadrillas;
    }

    @Override
    public void update(Cuadrilla cuadrilla) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, cuadrilla.getId());
            statement.setString(2, cuadrilla.getNombre());
            statement.setInt(3, cuadrilla.getSupervisor_id());
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
        }
    }
}
