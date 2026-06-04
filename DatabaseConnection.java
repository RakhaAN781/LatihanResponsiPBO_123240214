package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Kelas untuk mengelola koneksi ke database recruit_db menggunakan JDBC.
 * Menggunakan Singleton pattern agar hanya ada satu koneksi aktif.
 *
 * @author  Rakha Albany Nugraha
 * @nim     123240214
 * @kelas   IF-B
 */
public class DatabaseConnection {

    private static final String URL      = "jdbc:mysql://localhost:3306/recruit_db?useSSL=false&serverTimezone=UTC";
    private static final String USER     = "root";
    private static final String PASSWORD = "";   // Sesuaikan dengan password MySQL Anda

    private static Connection connection = null;

    // Konstruktor private — tidak boleh diinstansiasi
    private DatabaseConnection() {}

    /**
     * Mengembalikan koneksi aktif ke database.
     * Membuat koneksi baru jika belum ada atau sudah tertutup.
     *
     * @return objek Connection
     * @throws SQLException jika koneksi gagal
     */
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "Driver MySQL tidak ditemukan. " +
                "Pastikan mysql-connector-j sudah ditambahkan ke classpath.", e);
        }
        return connection;
    }

    /**
     * Menutup koneksi database secara aman.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.err.println("[DatabaseConnection] Gagal menutup koneksi: " + e.getMessage());
        }
    }
}
