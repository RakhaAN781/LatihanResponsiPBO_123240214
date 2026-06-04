package model;

/**
 * Subclass untuk jalur Web Developer.
 * Menerapkan prinsip Inheritance dari kelas abstrak Kandidat.
 * Nilai minimum kelulusan: 85.
 *
 * @author  Rakha Albany Nugraha
 * @nim     123240214
 * @kelas   IF-B
 */
public class WebDev extends Kandidat {

    private static final double NILAI_MINIMUM = 85.0;

    public WebDev() {
        super();
    }

    public WebDev(String nama, double writing, double coding, double interview) {
        super(nama, "Web Dev", writing, coding, interview);
    }

    public WebDev(int id, String nama, double writing, double coding,
                  double interview, double score, String status) {
        super(id, nama, "Web Dev", writing, coding, interview, score, status);
    }

    @Override
    public double getNilaiMinimum() {
        return NILAI_MINIMUM;
    }

    @Override
    public String toString() {
        return "[Web Dev] " + super.toString();
    }
}
