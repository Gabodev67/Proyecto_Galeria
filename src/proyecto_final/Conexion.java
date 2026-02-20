
package proyecto_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/galeria_db";
    private static final String USER = "postgres"; // Por defecto es 'postgres'
    private static final String PASS = "1234";

    public static Connection conectar() {
        Connection con = null;
        try {
            // Cargar el driver (opcional en versiones modernas de JDBC, pero recomendado)
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión exitosa a PostgreSQL");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return con;
    }
}