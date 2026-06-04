package model;

/**
 * Subclass untuk jalur Android Developer.
 * Menerapkan prinsip Inheritance dari kelas abstrak Kandidat.
 * Nilai minimum kelulusan: 85.
 *
 * @author  Rakha Albany Nugraha
 * @nim     123240214
 * @kelas   IF-B
 */
public class AndroidDev extends Kandidat {

    private static final double NILAI_MINIMUM = 85.0;

    public AndroidDev() {
        super();
    }

    public AndroidDev(String nama, double writing, double coding, double interview) {
        super(nama, "Android Dev", writing, coding, interview);
    }

    public AndroidDev(int id, String nama, double writing, double coding,
                      double interview, double score, String status) {
        super(id, nama, "Android Dev", writing, coding, interview, score, status);
    }

    @Override
    public double getNilaiMinimum() {
        return NILAI_MINIMUM;
    }

    @Override
    public String toString() {
        return "[Android Dev] " + super.toString();
    }
}
