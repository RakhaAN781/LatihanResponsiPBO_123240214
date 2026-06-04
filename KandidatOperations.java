package interfaces;

import model.Kandidat;
import java.util.List;

/**
 * Interface yang mendefinisikan operasi CRUD untuk data Kandidat.
 * Diimplementasikan oleh KandidatController.
 *
 * @author  Rakha Albany Nugraha
 * @nim     123240214
 * @kelas   IF-B
 */
public interface KandidatOperations {
    boolean tambahKandidat(Kandidat k);
    boolean updateKandidat(Kandidat k);
    boolean hapusKandidat(int id);
    List<Kandidat> getAllKandidat();
    Kandidat getKandidatById(int id);
}
