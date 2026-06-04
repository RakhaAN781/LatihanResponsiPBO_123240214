# PBO_LatResponsi_RecruitmentSystem
**Latihan Responsi Praktikum Pemrograman Berorientasi Objek**
Informatika UPN "Veteran" Yogyakarta

---

## Identitas Mahasiswa

| Field  | Nilai                  |
|--------|------------------------|
| Kelas  | IF-B                   |
| NIM    | 123240214              |
| Nama   | Rakha Albany Nugraha   |

---

## Deskripsi Aplikasi

Sistem Rekrutmen Magang untuk PT. OOP yang menjaring kandidat sebagai
**Android Developer** dan **Web Developer**. Kandidat mengikuti tiga jenis tes
(Writing, Coding, Interview) dengan rentang nilai 0–100. Nilai akhir dihitung
dari rata-rata ketiga tes. Batas minimum kelulusan adalah **85** — kandidat
dinyatakan **DITERIMA** jika nilai ≥ 85, dan **TIDAK DITERIMA** jika < 85.

---

## Konsep OOP yang Diimplementasikan

| Pillar          | Implementasi                                                              |
|-----------------|---------------------------------------------------------------------------|
| **Interface**   | `KandidatOperations` (CRUD), `Calculatable` (hitung nilai & status)       |
| **Inheritance** | `Kandidat` (abstract base) → `AndroidDev`, `WebDev`                       |
| **Encapsulation**| Semua atribut `Kandidat` bersifat `private`, diakses via getter/setter   |

---

## Arsitektur MVC

```
Model      → Kandidat, AndroidDev, WebDev, KandidatFactory
View       → RecruitmentView (Java Swing GUI)
Controller → KandidatController
```

---

## Struktur Proyek

```
PBO_LatResponsi_RecruitmentSystem/
├── sql/
│   └── recruit_db.sql              ← Jalankan di MySQL terlebih dahulu
├── src/
│   ├── Main.java                   ← Entry point (identitas ada di sini)
│   ├── interfaces/
│   │   ├── KandidatOperations.java ← Interface CRUD
│   │   └── Calculatable.java       ← Interface hitung nilai & status
│   ├── model/
│   │   ├── Kandidat.java           ← Abstract base class (Encapsulation)
│   │   ├── AndroidDev.java         ← Subclass Android Developer
│   │   ├── WebDev.java             ← Subclass Web Developer
│   │   └── KandidatFactory.java    ← Factory untuk instansiasi model
│   ├── database/
│   │   ├── DatabaseConnection.java ← Koneksi JDBC (Singleton)
│   │   └── KandidatDAO.java        ← Data Access Object (CRUD ke DB)
│   ├── controller/
│   │   └── KandidatController.java ← MVC Controller + validasi input
│   └── view/
│       └── RecruitmentView.java    ← MVC View (Swing GUI)
└── README.md
```

---

## Cara Setup & Menjalankan

### 1. Setup Database MySQL

```sql
-- Jalankan di MySQL Workbench atau terminal:
mysql -u root -p < sql/recruit_db.sql
```

### 2. Download Driver JDBC

Download `mysql-connector-j-8.x.x.jar` dari:
https://dev.mysql.com/downloads/connector/j/

Letakkan di folder root proyek (sejajar dengan `src/`).

### 3. Sesuaikan Password MySQL

Buka `src/database/DatabaseConnection.java`, ubah baris:
```java
private static final String PASSWORD = "";  // ← isi password MySQL Anda
```

### 4. Kompilasi

```bash
# Linux / macOS
javac -cp ".:mysql-connector-j-8.x.x.jar" -d out \
    src/interfaces/*.java \
    src/model/*.java \
    src/database/*.java \
    src/controller/*.java \
    src/view/*.java \
    src/Main.java

# Windows (ganti : dengan ;)
javac -cp ".;mysql-connector-j-8.x.x.jar" -d out ^
    src/interfaces/*.java ^
    src/model/*.java ^
    src/database/*.java ^
    src/controller/*.java ^
    src/view/*.java ^
    src/Main.java
```

### 5. Jalankan

```bash
# Linux / macOS
java -cp ".:out:mysql-connector-j-8.x.x.jar" Main

# Windows
java -cp ".;out;mysql-connector-j-8.x.x.jar" Main
```

---

## Cara Menggunakan Aplikasi

| Aksi       | Langkah                                                      |
|------------|--------------------------------------------------------------|
| **Add**    | Isi semua field (Name, Path, Writing, Coding, Interview) → klik **Add** |
| **Update** | Klik baris kandidat di tabel → edit field yang diinginkan → klik **Update** |
| **Delete** | Klik baris kandidat di tabel → klik **Delete** → konfirmasi dialog |
| **Clear**  | Klik **Clear** untuk mengosongkan semua field form           |

---

## Error Handling

- Input kosong → pesan peringatan (Warning dialog)
- Nilai di luar rentang 0–100 → pesan peringatan
- Input bukan angka → pesan peringatan
- Koneksi database gagal → pesan error detail
- Driver JDBC tidak ditemukan → pesan error dengan petunjuk instalasi
- Operasi database gagal → exception dengan pesan deskriptif

---

## Teknologi

- **Java** 11+
- **Java Swing** (javax.swing) — GUI
- **MySQL** 8.x — Database
- **JDBC** (mysql-connector-j) — Koneksi database
