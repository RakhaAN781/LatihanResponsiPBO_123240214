package model;

/**
 * Factory class untuk membuat instance Kandidat yang tepat berdasarkan jalur (path).
 * Memisahkan logika pembuatan objek dari controller dan DAO.
 *
 * @author  Rakha Albany Nugraha
 * @nim     123240214
 * @kelas   IF-B
 */
public class KandidatFactory {

    private KandidatFactory() {}

    /**
     * Membuat Kandidat baru (belum ada di database).
     */
    public static Kandidat create(String path, String nama,
                                  double writing, double coding, double interview) {
        switch (path) {
            case "Android Dev":
                return new AndroidDev(nama, writing, coding, interview);
            case "Web Dev":
                return new WebDev(nama, writing, coding, interview);
            default:
                throw new IllegalArgumentException("Jalur tidak dikenal: " + path);
        }
    }

    /**
     * Membuat Kandidat dari data yang sudah ada di database.
     */
    public static Kandidat createFromDB(int id, String nama, String path,
                                        double writing, double coding, double interview,
                                        double score, String status) {
        switch (path) {
            case "Android Dev":
                return new AndroidDev(id, nama, writing, coding, interview, score, status);
            case "Web Dev":
                return new WebDev(id, nama, writing, coding, interview, score, status);
            default:
                throw new IllegalArgumentException("Jalur tidak dikenal: " + path);
        }
    }
}
