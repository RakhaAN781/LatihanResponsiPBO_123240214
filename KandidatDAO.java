package database;

import model.Kandidat;
import model.KandidatFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) untuk tabel kandidat pada database recruit_db.
 * Menangani semua operasi CRUD menggunakan JDBC dengan PreparedStatement.
 *
 * @author  Rakha Albany Nugraha
 * @nim     123240214
 * @kelas   IF-B
 */
public class KandidatDAO {

    // ======================= CREATE =======================

    /**
     * Menyimpan kandidat baru ke database.
     *
     * @param k objek Kandidat yang akan disimpan
     * @return true jika berhasil
     */
    public boolean insert(Kandidat k) {
        String sql = "INSERT INTO kandidat (nama, path, writing, coding, interview, score, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, k.getNama());
            ps.setString(2, k.getPath());
            ps.setDouble(3, k.getWriting());
            ps.setDouble(4, k.getCoding());
            ps.setDouble(5, k.getInterview());
            ps.setDouble(6, k.getScore());
            ps.setString(7, k.getStatus());

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) k.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("[KandidatDAO] Error insert: " + e.getMessage());
        }
        return false;
    }

    // ======================= READ =======================

    /**
     * Mengambil semua data kandidat dari database.
     *
     * @return List berisi seluruh Kandidat
     */
    public List<Kandidat> findAll() {
        List<Kandidat> list = new ArrayList<>();
        String sql = "SELECT * FROM kandidat ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st   = conn.createStatement();
             ResultSet rs   = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(KandidatFactory.createFromDB(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("path"),
                        rs.getDouble("writing"),
                        rs.getDouble("coding"),
                        rs.getDouble("interview"),
                        rs.getDouble("score"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[KandidatDAO] Error findAll: " + e.getMessage());
        }
        return list;
    }

    /**
     * Mengambil satu kandidat berdasarkan ID.
     *
     * @param id ID kandidat
     * @return Kandidat jika ditemukan, null jika tidak
     */
    public Kandidat findById(int id) {
        String sql = "SELECT * FROM kandidat WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return KandidatFactory.createFromDB(
                            rs.getInt("id"),
                            rs.getString("nama"),
                            rs.getString("path"),
                            rs.getDouble("writing"),
                            rs.getDouble("coding"),
                            rs.getDouble("interview"),
                            rs.getDouble("score"),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("[KandidatDAO] Error findById: " + e.getMessage());
        }
        return null;
    }

    // ======================= UPDATE =======================

    /**
     * Memperbarui data kandidat di database.
     *
     * @param k objek Kandidat dengan data terbaru
     * @return true jika berhasil
     */
    public boolean update(Kandidat k) {
        String sql = "UPDATE kandidat SET nama=?, path=?, writing=?, coding=?, interview=?, score=?, status=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, k.getNama());
            ps.setString(2, k.getPath());
            ps.setDouble(3, k.getWriting());
            ps.setDouble(4, k.getCoding());
            ps.setDouble(5, k.getInterview());
            ps.setDouble(6, k.getScore());
            ps.setString(7, k.getStatus());
            ps.setInt(8, k.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[KandidatDAO] Error update: " + e.getMessage());
        }
        return false;
    }

    // ======================= DELETE =======================

    /**
     * Menghapus kandidat dari database berdasarkan ID.
     *
     * @param id ID kandidat yang dihapus
     * @return true jika berhasil
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM kandidat WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[KandidatDAO] Error delete: " + e.getMessage());
        }
        return false;
    }
}
