package controller;

import database.KandidatDAO;
import interfaces.KandidatOperations;
import model.Kandidat;
import model.KandidatFactory;

import java.util.List;

/**
 * Controller dalam pola MVC.
 * Menghubungkan View (GUI) dengan Model dan DAO.
 * Mengimplementasikan interface KandidatOperations sebagai penerapan
 * pillar Interface pada OOP.
 *
 * @author  Rakha Albany Nugraha
 * @nim     123240214
 * @kelas   IF-B
 */
public class KandidatController implements KandidatOperations {

    private final KandidatDAO dao;

    public KandidatController() {
        this.dao = new KandidatDAO();
    }

    // ======================= Business Logic =======================

    /**
     * Membuat dan menyimpan kandidat baru ke database.
     * Validasi input dilakukan di sini sebelum meneruskan ke DAO.
     *
     * @return Kandidat yang sudah tersimpan (beserta id-nya)
     * @throws IllegalArgumentException jika input tidak valid
     * @throws RuntimeException         jika operasi database gagal
     */
    public Kandidat buatKandidat(String nama, String path,
                                 double writing, double coding, double interview) {
        validasiNama(nama);
        validasiNilai(writing,   "Writing");
        validasiNilai(coding,    "Coding");
        validasiNilai(interview, "Interview");

        Kandidat k = KandidatFactory.create(path, nama.trim(), writing, coding, interview);

        if (!dao.insert(k)) {
            throw new RuntimeException("Gagal menyimpan data ke database.");
        }
        return k;
    }

    /**
     * Memperbarui data kandidat yang sudah ada.
     *
     * @return Kandidat dengan data terbaru
     * @throws IllegalArgumentException jika input tidak valid
     * @throws RuntimeException         jika operasi database gagal
     */
    public Kandidat updateKandidatData(int id, String nama, String path,
                                       double writing, double coding, double interview) {
        validasiNama(nama);
        validasiNilai(writing,   "Writing");
        validasiNilai(coding,    "Coding");
        validasiNilai(interview, "Interview");

        Kandidat k = KandidatFactory.create(path, nama.trim(), writing, coding, interview);
        k.setId(id);
        k.refreshScoreStatus();

        if (!dao.update(k)) {
            throw new RuntimeException("Gagal memperbarui data di database.");
        }
        return k;
    }

    // ======================= Interface KandidatOperations =======================

    @Override
    public boolean tambahKandidat(Kandidat k) {
        k.refreshScoreStatus();
        return dao.insert(k);
    }

    @Override
    public boolean updateKandidat(Kandidat k) {
        k.refreshScoreStatus();
        return dao.update(k);
    }

    @Override
    public boolean hapusKandidat(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID tidak valid untuk penghapusan.");
        }
        return dao.delete(id);
    }

    @Override
    public List<Kandidat> getAllKandidat() {
        return dao.findAll();
    }

    @Override
    public Kandidat getKandidatById(int id) {
        return dao.findById(id);
    }

    // ======================= Validasi =======================

    private void validasiNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama kandidat tidak boleh kosong.");
        }
    }

    private void validasiNilai(double nilai, String namaField) {
        if (nilai < 0 || nilai > 100) {
            throw new IllegalArgumentException(
                "Nilai " + namaField + " harus berada di rentang 0 – 100.");
        }
    }
}
