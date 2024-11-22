package org.example.conexion;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class FactoriaConexion {
    private static Connection conn;

    public FactoriaConexion() {
        this.conn = FactoriaConexion.getConnection();
    }

    static {
        try (InputStream input = FactoriaConexion.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                System.out.println("No se encontro el archivo db.properties");
            }
            properties.load(input);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión a la base de datos exitosa.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void createTrigger() {
        try (Statement statement = conn.createStatement()) {

            String sql = "DROP TRIGGER IF EXISTS `evitar-producciones-negativas`";
            statement.execute(sql);

            sql = "CREATE TRIGGER `evitar_producciones_negativas` \n" + //
                    "BEFORE INSERT ON `Produccion`\n" + //
                    "FOR EACH ROW\n" +//
                    "BEGIN\n" + //
                    "IF NEW.cantidadRecolectada < 0 \n" + //
                    "THEN \n" + //
                    "SIGNAL sqlstate '45001' \n" + //
                    "SET message_text = 'La cantidadRecolectada no puede ser negativa.'; \n" + //
                    "END IF; \n" + //
                    "END";
            statement.execute(sql);

            System.out.println("Disparador creado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al crear el disparador. " + e.getMessage());

        }
    }
}
