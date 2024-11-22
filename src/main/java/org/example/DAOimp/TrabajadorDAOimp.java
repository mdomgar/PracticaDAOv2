package org.example.DAOimp;

import org.example.conexion.FactoriaConexion;
import org.example.dao.TrabajadorDAO;
import org.example.entidades.Cuadrilla;
import org.example.entidades.Trabajador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorDAOimp implements TrabajadorDAO {
    private static final String SAVE_QUERY = "INSERT INTO Trabajador (nombre, edad, puesto, salario) VALUES (?, ?, ? ,?)";
    private static final String FIND_ONE_QUERY = "select t.id, t.nombre, t.edad, t.puesto, t.salario, ct.cuadrilla_id from Trabajador t inner join Cuadrilla_Trabajador ct on t.id = ct.trabajador_id WHERE t.id=?";
    private static final String FIND_ALL_QUERY = "select t.id, t.nombre, t.edad, t.puesto, t.salario, ct.cuadrilla_id from Trabajador t inner join Cuadrilla_Trabajador ct on t.id = ct.trabajador_id";
    private static final String UPDATE_QUERY = "UPDATE Trabajador SET nombre = ?, edad = ?, puesto = ?, salario = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM Trabajador WHERE id = ?";

    private Connection connection;

    public TrabajadorDAOimp() {
        this.connection = FactoriaConexion.getConnection();
    }

    @Override
    public Trabajador save(Trabajador trabajador) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {

            statement.setString(1, trabajador.getNombre());
            statement.setInt(2, trabajador.getEdad());
            statement.setString(3, trabajador.getPuesto());
            statement.setDouble(4, trabajador.getSalario());
            statement.executeUpdate();

        }
        return trabajador;
    }

    public List<Integer> getIdCuadrillaByTrabajadorId(int id) throws SQLException{
        List<Integer> listIdCuadrilla = new ArrayList<>();
        String sql = "Select cuadrilla_id from Cuadrilla_Trabajador where trabajador_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listIdCuadrilla.add(resultSet.getInt("cuadrilla_id"));
            }
        }
        return listIdCuadrilla;
    }

    public void setIdCuadrilla(int cuadrilla_id, int trabajador_id) throws SQLException{
        String query = "INSERT INTO Cuadrilla_Trabajador (cuadrilla_id, trabajador_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cuadrilla_id);
            statement.setInt(2, trabajador_id);
            statement.executeUpdate();
        }
    }

    @Override
    public Trabajador findOne(int id) throws SQLException {
        Trabajador trabajador = null;

        try (PreparedStatement statement = connection.prepareStatement(FIND_ONE_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                trabajador = new Trabajador(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("edad"),
                        resultSet.getString("puesto"),
                        resultSet.getDouble("salario"),
                        resultSet.getInt("cuadrilla_id")
                );
            }
            return trabajador;
        }
    }

    @Override
    public List<Trabajador> findAll() throws SQLException {
        List<Trabajador> trabajadores = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                trabajadores.add(new Trabajador(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getInt("edad"),
                        resultSet.getString("puesto"),
                        resultSet.getDouble("salario"),
                        resultSet.getInt("cuadrilla_id")
                ));
            }
            return trabajadores;
        }
    }

    @Override
    public void update(Trabajador trabajador) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, trabajador.getNombre());
            statement.setInt(2, trabajador.getEdad());
            statement.setString(3, trabajador.getPuesto());
            statement.setDouble(4, trabajador.getSalario());
            statement.setInt(5, trabajador.getId());
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