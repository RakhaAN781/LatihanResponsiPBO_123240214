package model;

import interfaces.Calculatable;

/**
 * Kelas abstrak sebagai base class untuk semua jalur kandidat magang PT. OOP.
 * Menerapkan prinsip Encapsulation (atribut private + getter/setter)
 * dan menjadi dasar Inheritance untuk AndroidDev dan WebDev.
 * Mengimplementasikan interface Calculatable.
 *
 * @author  Rakha Albany Nugraha
 * @nim     123240214
 * @kelas   IF-B
 */
public abstract class Kandidat implements Calculatable {

    // === Encapsulation: semua atribut private ===
    private int    id;
    private String nama;
    private String path;
    private double writing;
    private double coding;
    private double interview;
    private double score;
    private String status;

    /** Bobot setiap tes: rata-rata tiga tes */
    protected static final double BOBOT = 1.0 / 3.0;

    // ==================== Konstruktor ====================

    public Kandidat() {}

    /**
     * Konstruktor untuk data baru (id belum ada).
     */
    public Kandidat(String nama, String path, double writing, double coding, double interview) {
        this.nama      = nama;
        this.path      = path;
        this.writing   = writing;
        this.coding    = coding;
        this.interview = interview;
        this.score     = hitungNilaiAkhir();
        this.status    = hitungStatus();
    }

    /**
     * Konstruktor untuk data yang dimuat dari database.
     */
    public Kandidat(int id, String nama, String path,
                    double writing, double coding, double interview,
                    double score, String status) {
        this.id        = id;
        this.nama      = nama;
        this.path      = path;
        this.writing   = writing;
        this.coding    = coding;
        this.interview = interview;
        this.score     = score;
        this.status    = status;
    }

    // ==================== Getter & Setter ====================

    public int    getId()       { return id; }
    public void   setId(int id) { this.id = id; }

    public String getNama()          { return nama; }
    public void   setNama(String v)  { this.nama = v; }

    public String getPath()          { return path; }
    public void   setPath(String v)  { this.path = v; }

    public double getWriting()         { return writing; }
    public void   setWriting(double v) { this.writing = v; }

    public double getCoding()          { return coding; }
    public void   setCoding(double v)  { this.coding = v; }

    public double getInterview()           { return interview; }
    public void   setInterview(double v)   { this.interview = v; }

    public double getScore()           { return score; }
    public void   setScore(double v)   { this.score = v; }

    public String getStatus()          { return status; }
    public void   setStatus(String v)  { this.status = v; }

    // ==================== Calculatable Implementation ====================

    /**
     * Nilai akhir = rata-rata ketiga tes.
     */
    @Override
    public double hitungNilaiAkhir() {
        return (writing + coding + interview) * BOBOT;
    }

    /**
     * Status ditentukan berdasarkan nilai minimum dari subclass
     * (Template Method pattern).
     */
    @Override
    public String hitungStatus() {
        return hitungNilaiAkhir() >= getNilaiMinimum() ? "DITERIMA" : "TIDAK DITERIMA";
    }

    /**
     * Memperbarui score dan status berdasarkan nilai terkini.
     * Dipanggil sebelum operasi insert/update ke database.
     */
    public void refreshScoreStatus() {
        this.score  = hitungNilaiAkhir();
        this.status = hitungStatus();
    }

    @Override
    public String toString() {
        return String.format("Kandidat{id=%d, nama='%s', path='%s', score=%.2f, status='%s'}",
                id, nama, path, score, status);
    }
}
