package view;

import controller.KandidatController;
import model.Kandidat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * View utama dalam pola MVC — tampilan GUI Sistem Rekrutmen PT. OOP.
 * Dibangun menggunakan Java Swing, menampilkan tabel kandidat di kiri
 * dan form input di kanan, sesuai dengan contoh output yang diberikan.
 *
 * @author  Rakha Albany Nugraha
 * @nim     123240214
 * @kelas   IF-B
 */
public class RecruitmentView extends JFrame {

    // ==================== Komponen GUI ====================
    private JTable             table;
    private DefaultTableModel  tableModel;

    private JTextField         txtNama;
    private JComboBox<String>  cmbPath;
    private JTextField         txtWriting;
    private JTextField         txtCoding;
    private JTextField         txtInterview;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;

    // ==================== Controller ====================
    private final KandidatController controller;

    /** ID kandidat yang sedang dipilih di tabel; -1 jika tidak ada */
    private int selectedId = -1;

    /** Cache list terakhir dimuat dari DB untuk referensi id per baris */
    private List<Kandidat> lastLoadedList;

    // ==================== Konstruktor ====================

    public RecruitmentView() {
        controller = new KandidatController();
        initComponents();
        loadTableData();
    }

    // ==================== Inisialisasi Komponen ====================

    private void initComponents() {
        setTitle("Sistem Rekrutmen Magang — PT. OOP | IF-B | 123240214 | Rakha Albany Nugraha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 460);
        setLocationRelativeTo(null);
        setResizable(true);

        // Panel utama
        JPanel mainPanel = new JPanel(new BorderLayout(8, 8));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // ----- Tabel (kiri) -----
        String[] columns = {"Name", "Path", "Writing", "Coding", "Interview", "Score", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(20);

        // Lebar kolom (sesuai gambar)
        int[] colWidths = {130, 100, 60, 60, 75, 65, 120};
        for (int i = 0; i < colWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(colWidths[i]);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // ----- Form (kanan) -----
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(0, 6, 0, 2));
        formPanel.setPreferredSize(new Dimension(195, 0));

        // Name
        formPanel.add(label("Name"));
        txtNama = textField();
        formPanel.add(txtNama);
        formPanel.add(gap(5));

        // Path (ComboBox)
        formPanel.add(label("Path"));
        cmbPath = new JComboBox<>(new String[]{"Android Dev", "Web Dev"});
        cmbPath.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        cmbPath.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(cmbPath);
        formPanel.add(gap(5));

        // Writing
        formPanel.add(label("Writing"));
        txtWriting = textField();
        formPanel.add(txtWriting);
        formPanel.add(gap(5));

        // Coding
        formPanel.add(label("Coding"));
        txtCoding = textField();
        formPanel.add(txtCoding);
        formPanel.add(gap(5));

        // Interview
        formPanel.add(label("Interview"));
        txtInterview = textField();
        formPanel.add(txtInterview);
        formPanel.add(gap(14));

        // Tombol CRUD
        btnAdd    = button("Add");
        btnUpdate = button("Update");
        btnDelete = button("Delete");
        btnClear  = button("Clear");

        for (JButton btn : new JButton[]{btnAdd, btnUpdate, btnDelete, btnClear}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));
            formPanel.add(btn);
            formPanel.add(gap(4));
        }

        mainPanel.add(formPanel, BorderLayout.EAST);

        // ----- Event Listeners -----
        btnAdd.addActionListener(e    -> handleAdd());
        btnUpdate.addActionListener(e -> handleUpdate());
        btnDelete.addActionListener(e -> handleDelete());
        btnClear.addActionListener(e  -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) handleTableSelection();
        });
    }

    // ==================== Helper Builder ====================

    private JLabel label(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField textField() {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        return tf;
    }

    private JButton button(String text) {
        return new JButton(text);
    }

    private Component gap(int height) {
        return Box.createVerticalStrut(height);
    }

    // ==================== Data Loading ====================

    private void loadTableData() {
        tableModel.setRowCount(0);
        lastLoadedList = controller.getAllKandidat();
        for (Kandidat k : lastLoadedList) {
            tableModel.addRow(new Object[]{
                    k.getNama(),
                    k.getPath(),
                    String.format("%.2f", k.getWriting()),
                    String.format("%.2f", k.getCoding()),
                    String.format("%.2f", k.getInterview()),
                    String.format("%.2f", k.getScore()),
                    k.getStatus()
            });
        }
    }

    // ==================== Event Handler: Add ====================

    private void handleAdd() {
        try {
            String nama      = txtNama.getText().trim();
            String path      = (String) cmbPath.getSelectedItem();
            double writing   = parseNilai(txtWriting.getText(),   "Writing");
            double coding    = parseNilai(txtCoding.getText(),    "Coding");
            double interview = parseNilai(txtInterview.getText(), "Interview");

            Kandidat k = controller.buatKandidat(nama, path, writing, coding, interview);
            loadTableData();
            clearForm();

            JOptionPane.showMessageDialog(this,
                    String.format("Kandidat '%s' berhasil ditambahkan!\nScore: %.2f  |  Status: %s",
                            k.getNama(), k.getScore(), k.getStatus()),
                    "Berhasil Ditambahkan", JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Input Tidak Valid", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ==================== Event Handler: Update ====================

    private void handleUpdate() {
        if (selectedId < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih kandidat yang ingin diperbarui terlebih dahulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String nama      = txtNama.getText().trim();
            String path      = (String) cmbPath.getSelectedItem();
            double writing   = parseNilai(txtWriting.getText(),   "Writing");
            double coding    = parseNilai(txtCoding.getText(),    "Coding");
            double interview = parseNilai(txtInterview.getText(), "Interview");

            Kandidat k = controller.updateKandidatData(selectedId, nama, path, writing, coding, interview);
            loadTableData();
            clearForm();

            JOptionPane.showMessageDialog(this,
                    String.format("Data kandidat '%s' berhasil diperbarui!\nScore: %.2f  |  Status: %s",
                            k.getNama(), k.getScore(), k.getStatus()),
                    "Berhasil Diperbarui", JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Input Tidak Valid", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ==================== Event Handler: Delete ====================

    private void handleDelete() {
        if (selectedId < 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih kandidat yang ingin dihapus terlebih dahulu.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus kandidat ini?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean ok = controller.hapusKandidat(selectedId);
                if (ok) {
                    loadTableData();
                    clearForm();
                    JOptionPane.showMessageDialog(this,
                            "Data kandidat berhasil dihapus.",
                            "Berhasil Dihapus", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Gagal menghapus data dari database.",
                            "Gagal", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                        "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ==================== Table Selection ====================

    private void handleTableSelection() {
        int row = table.getSelectedRow();
        if (row >= 0 && lastLoadedList != null && row < lastLoadedList.size()) {
            Kandidat k = lastLoadedList.get(row);
            selectedId = k.getId();
            txtNama.setText(k.getNama());
            cmbPath.setSelectedItem(k.getPath());
            txtWriting.setText(String.valueOf(k.getWriting()));
            txtCoding.setText(String.valueOf(k.getCoding()));
            txtInterview.setText(String.valueOf(k.getInterview()));
        }
    }

    // ==================== Clear Form ====================

    private void clearForm() {
        selectedId = -1;
        txtNama.setText("");
        cmbPath.setSelectedIndex(0);
        txtWriting.setText("");
        txtCoding.setText("");
        txtInterview.setText("");
        table.clearSelection();
    }

    // ==================== Input Parsing ====================

    private double parseNilai(String text, String namaField) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Nilai " + namaField + " tidak boleh kosong.");
        }
        try {
            double val = Double.parseDouble(text.trim());
            if (val < 0 || val > 100) {
                throw new IllegalArgumentException(
                    "Nilai " + namaField + " harus berada di rentang 0 – 100.");
            }
            return val;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Nilai " + namaField + " harus berupa angka.");
        }
    }
}
