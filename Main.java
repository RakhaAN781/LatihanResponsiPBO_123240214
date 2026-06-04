// ============================================================
//  Identitas Mahasiswa
//  Kelas : IF-B
//  NIM   : 123240214
//  Nama  : Rakha Albany Nugraha
//  Mata Kuliah : Praktikum Pemrograman Berorientasi Objek
//  Judul       : Latihan Responsi — Sistem Rekrutmen PT. OOP (RekrutmenPBO_Rakha)
// ============================================================

import view.RecruitmentView;

import javax.swing.*;

/**
 * Entry point aplikasi Sistem Rekrutmen Magang PT. OOP.
 *
 * Konsep OOP yang diimplementasikan:
 *   - Interface    : KandidatOperations (CRUD), Calculatable (nilai & status)
 *   - Inheritance  : Kandidat (abstract) ← AndroidDev, WebDev
 *   - Encapsulation: semua atribut Kandidat bersifat private + getter/setter
 *
 * Arsitektur MVC:
 *   - Model      : Kandidat, AndroidDev, WebDev, KandidatFactory
 *   - View       : RecruitmentView (Java Swing)
 *   - Controller : KandidatController
 *
 * Koneksi Database:
 *   - JDBC ke MySQL database recruit_db
 *   - DatabaseConnection (singleton), KandidatDAO (CRUD)
 *
 * @author  Rakha Albany Nugraha
 * @nim     123240214
 * @kelas   IF-B
 */
public class Main {

    public static void main(String[] args) {
        // Jalankan GUI di Event Dispatch Thread — best practice Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Gunakan Look and Feel sistem operasi
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Tidak kritis — fallback ke default Swing L&F
                System.err.println("Tidak dapat mengatur Look and Feel: " + e.getMessage());
            }

            RecruitmentView frame = new RecruitmentView();
            frame.setVisible(true);
        });
    }
}
