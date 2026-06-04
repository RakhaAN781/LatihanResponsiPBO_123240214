package interfaces;

/**
 * Interface untuk menghitung nilai akhir dan status seleksi kandidat.
 * Diimplementasikan oleh setiap subclass jalur rekrutmen.
 *
 * @author  Rakha Albany Nugraha
 * @nim     123240214
 * @kelas   IF-B
 */
public interface Calculatable {
    double hitungNilaiAkhir();
    String hitungStatus();
    double getNilaiMinimum();
}
