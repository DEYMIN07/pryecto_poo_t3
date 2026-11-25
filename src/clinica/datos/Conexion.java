package clinica.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Conexión con SQL Server. Es el único lugar donde se escriben el servidor, la base, el usuario y la clave. */
public class Conexion {
    // CAMBIAR AQUÍ si el servidor, la base, el usuario o la clave son otros.
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=clinica;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "usuarioClinica";
    private static final String CLAVE = "Cl1nica#2026";

    /** Último error de conexión (para mostrarlo en pantalla). */
    private static String ultimoError = "";

    /** Abre la conexión con SQL Server. Si falla, devuelve null y guarda el motivo en ultimoError. */
    public Connection conectarSqlServer() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");   // 1. carga el driver (mssql-jdbc)
            conn = DriverManager.getConnection(URL, USUARIO, CLAVE);          // 2. abre la conexión
            ultimoError = "";
        } catch (ClassNotFoundException e) {
            ultimoError = "No se encontró el driver de SQL Server (mssql-jdbc). Agregue el .jar a la carpeta lib y a las librerías del proyecto.";
        } catch (SQLException e) {
            ultimoError = e.getMessage();
        }
        return conn;
    }

    /** Abre la conexión para un DAO; si no se puede, lanza el error que se muestra en pantalla. */
    public Connection abrir() {
        Connection conn = conectarSqlServer();
        if (conn == null) {
            throw new IllegalArgumentException("Error: no hay conexión con la base de datos (" + ultimoError + ")");
        }
        return conn;
    }

    /** Deshace los cambios de una transacción que falló. */
    public static void deshacer(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException e) {
            ultimoError = e.getMessage();
        }
    }

    /** Cierra la conexión. */
    public static void cerrar(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            ultimoError = e.getMessage();
        }
    }

    /** Devuelve el motivo del último error de conexión. */
    public static String getUltimoError() { return ultimoError; }
}
