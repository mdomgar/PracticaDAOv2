package org.example.DAOimp;

import org.example.conexion.FactoriaConexion;
import org.example.dao.ProduccionDAO;
import org.example.entidades.Almazara;
import org.example.entidades.Cuadrilla;
import org.example.entidades.Produccion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProduccionDAOimp implements ProduccionDAO {
    private static final String SAVE_QUERY = "INSERT INTO Produccion (cuadrilla_id, olivar_id, almazara_id, fecha, cantidadRecolectada) VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_ONE_QUERY = "Select cuadrilla_id, olivar_id, almazara_id, fecha, cantidadRecolectada from Produccion WHERE id=?";
    private static final String FIND_ALL_QUERY = "Select * from Produccion";
    private static final String UPDATE_QUERY = "UPDATE Produccion SET cuadrilla_id = ?, olivar_id = ?, almazara_id = ?, fecha = ?, cantidadRecolectada = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Produccion WHERE id = ?";

    private Connection connection;

    public ProduccionDAOimp() {
        this.connection = FactoriaConexion.getConnection();
    }

    @Override
    public Produccion save(Produccion produccion) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {

            statement.setInt(1, produccion.getCuadrilla_id());
            statement.setInt(2, produccion.getOlivar_id());
            statement.setInt(3, produccion.getAlmazara_id());
            statement.setDate(4, Date.valueOf(produccion.getFecha()));
            statement.setDouble(5, produccion.getCantidadRecolectada());
            statement.executeUpdate();

        } catch (SQLException e){
            System.err.println(e);
        }
        return produccion;
    }

    @Override
    public Produccion findOne(int id) throws SQLException {
        Produccion produccion = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_ONE_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                produccion = new Produccion(
                        resultSet.getInt("cuadrilla_id"),
                        resultSet.getInt("olivar_id"),
                        resultSet.getInt("almazara_id"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getDouble("cantidadRecolectada")
                );
            }
        }
        return produccion;
    }

    @Override
    public List<Produccion> findAll() throws SQLException {
        List<Produccion> producciones = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                producciones.add(new Produccion(
                        resultSet.getInt("id"),
                        resultSet.getInt("cuadrilla_id"),
                        resultSet.getInt("olivar_id"),
                        resultSet.getInt("almazara_id"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getDouble("cantidadRecolectada")
                ));
            }
        }
        return producciones;
    }

    @Override
    public void update(Produccion produccion) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, produccion.getCuadrilla_id());
            statement.setInt(2, produccion.getOlivar_id());
            statement.setInt(3, produccion.getAlmazara_id());
            statement.setDate(4, Date.valueOf(produccion.getFecha()));
            statement.setDouble(5, produccion.getCantidadRecolectada());
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);
        }
    }

    public List<Almazara> getAlmazaraByCuadrillaId(int idCuadrilla) throws SQLException {
        List<Almazara> listAlmazara = new ArrayList<>();
        String sql = "Select DISTINCT a.id, a.nombre, a.ubicacion, a.capacidad from Almazara a INNER JOIN Produccion p on a.id = p.almazara_id where p.cuadrilla_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCuadrilla);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listAlmazara.add(new Almazara(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("ubicacion"),
                        resultSet.getDouble("capacidad")
                ));
            }
        }
        return listAlmazara;
    }

    public int getProduccionByFechaCuadrillaAlmazaraId(LocalDate date, int idCuadrilla, int idAlmazara) throws SQLException {
        int produccion = 0;
        String sql = "Select SUM(cantidadRecolectada) from Produccion where fecha= ?  and cuadrilla_id= ? and almazara_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(date));
            statement.setInt(2, idCuadrilla);
            statement.setInt(3, idAlmazara);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                produccion = resultSet.getInt(1);
            }
        }
        return produccion;
    }

    public int getProduccionByAlmazaraIdTillFecha(int idAlmazara, LocalDate fecha) throws SQLException {
        String sql = "select SUM(cantidadRecolectada) from Produccion where fecha<= ? and almazara_id = ?";
        int cantidad = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(fecha));
            statement.setInt(2, idAlmazara);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cantidad = resultSet.getInt(1);
            }
        }
        return cantidad;
    }

    public int getProduccionByOlivarIdTillFecha(int idOlivar, LocalDate fecha) throws SQLException {
        String sql = "select SUM(cantidadRecolectada) from Produccion where fecha<= ? and olivar_id = ?";
        int cantidad = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(fecha));
            statement.setInt(2, idOlivar);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cantidad = resultSet.getInt(1);
            }
        }
        return cantidad;
    }

    public int getProduccionByCuadrillaIdTillFecha(int idCuadrilla, LocalDate fecha) throws SQLException {
        String sql = "select SUM(cantidadRecolectada) from Produccion where fecha<= ? and cuadrilla_id = ?";
        int cantidad = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(fecha));
            statement.setInt(2, idCuadrilla);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cantidad = resultSet.getInt(1);
            }
        }
        return cantidad;
    }
}
