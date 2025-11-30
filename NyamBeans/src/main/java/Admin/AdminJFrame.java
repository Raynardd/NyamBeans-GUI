/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Admin;

import Model.User;
import Model.Menu;
import Model.Pesanan;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author nenap
 */
public class AdminJFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminJFrame.class.getName());

    /**
     * Creates new form AdminJFrame
     */
    private int idMenuTarget = 0;
    private int idUserTarget = 0;
    private int idPesananTarget = 0;
    
    public AdminJFrame() {
        initComponents();
        
        // Isi pilihan jenis menu (sesuai ENUM di database)
        jenisMenu.removeAllItems();
        jenisMenu.addItem("Paket Nasi Ekonomis");
        jenisMenu.addItem("Paket Nasi Standar");
        jenisMenu.addItem("Paket Nasi Premium");
        jenisMenu.addItem("Paket Snack Ekonomis");
        jenisMenu.addItem("Paket Snack Standar");
        jenisMenu.addItem("Paket Snack Premium");
        jenisMenu.addItem("Tambahan");

        // Tampilkan data menu ke tabel (Nanti kita buat methodnya)
        loadDataMenu();
        loadDataUser();
        loadDataPesanan();
        initLaporanKeuangan();
    }
    
    private void loadDataMenu() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0); // Bersihkan tabel lama

        List<Menu> menus = Menu.getAllMenu();
        for (Menu m : menus) {
            model.addRow(new Object[]{
                m.getIdMenu(),
                m.getNamaMenu(),
                m.getJenisMenu(),
                m.getHargaPerPorsi(),
                m.getDeskripsi()
            });
        }
    }
    
    private void loadDataPesanan() {
        DefaultTableModel model = (DefaultTableModel) tabelPesanan.getModel();
        model.setRowCount(0);
        
        List<Pesanan> list = Pesanan.getAllPesanan();
        for (Pesanan p : list) {
            model.addRow(new Object[]{
                p.getIdPesanan(),
                p.getTglAcara(),
                p.getNamaPemesan(),
                p.getLokasiAcara(),
                p.getTotalHarga(),
                p.getStatus()
            });
        }
    }
    
    // Method untuk mengisi tanggal default (Bulan ini) dan load data otomatis
    private void initLaporanKeuangan() {
        // 1. Atur Tanggal Otomatis
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        // Tanggal Akhir = Hari ini
        String tglAkhir = sdf.format(cal.getTime());
        sampaiTanggalField.setText(tglAkhir);

        // Tanggal Awal = Tanggal 1 bulan ini
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String tglAwal = sdf.format(cal.getTime());
        dariTanggalFirld.setText(tglAwal);

        // 2. Panggil logika tampilkan data (Sama seperti tombol ditekan)
        tampilkanLaporan(tglAwal, tglAkhir);
    }
    
    // Kita pisahkan logika load tabel laporan agar bisa dipanggil dari dua tempat
    private void tampilkanLaporan(String tglAwalStr, String tglAkhirStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tglAwal = sdf.parse(tglAwalStr);
            Date tglAkhir = sdf.parse(tglAkhirStr);

            // Panggil Model Pesanan
            List<Pesanan> laporan = Pesanan.getLaporan(tglAwal, tglAkhir);

            DefaultTableModel model = (DefaultTableModel) tabelDataKeuangan.getModel();
            model.setRowCount(0);

            long totalPendapatan = 0;

            for (Pesanan p : laporan) {
                model.addRow(new Object[]{
                    p.getTglAcara(), // Tanggal
                    p.getIdPesanan(),
                    p.getNamaPemesan(),
                    p.getStatus(),
                    p.getMetodeBayar(),
                    p.getTotalHarga()
                });
                totalPendapatan += p.getTotalHarga();
            }

            totalPendapatanField.setText("Rp " + totalPendapatan);

        } catch (ParseException e) {
            // Diamkan saja jika format salah saat init, atau log error
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }
    
    private void clearMenuForm() {
        idMenuTarget = 0; // Reset target ID
        namaMenuField.setText("");
        jenisMenu.setSelectedIndex(0);
        hargaPerPorsi.setText("");
        deskripsiMenu.setText("");
        
        // Ubah teks tombol Simpan kembali ke mode normal jika sebelumnya mode edit
        simpanMenu.setText("Simpan"); 
        namaMenuField.requestFocus();
    }
    
    // --- Method untuk User ---
    private void loadDataUser() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        List<User> users = User.getAllUsers();
        for (User u : users) {
            model.addRow(new Object[]{
                u.getIdUser(),
                u.getNama(),
                u.getUsername(),
                u.getRole(),
                u.getNoTelp(),
                u.getAlamat() // Jika di tabel ada kolom alamat, kalau tidak, bisa dihapus
            });
        }
    }

    private void clearUserForm() {
        idUserTarget = 0;
        namaLengkapField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        jTextField5.setText(""); // Field No Telepon
        jTextArea1.setText("");  // Field Alamat
        rolePengguna.setSelectedIndex(0);
        
        simpanBtn.setText("Simpan");
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        namaLengkapField = new javax.swing.JTextField();
        usernameField = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        passwordField = new javax.swing.JPasswordField();
        hapusBtn = new javax.swing.JButton();
        EditBtn = new javax.swing.JButton();
        resetBtn = new javax.swing.JButton();
        simpanBtn = new javax.swing.JButton();
        rolePengguna = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        namaMenuField = new javax.swing.JTextField();
        jenisMenu = new javax.swing.JComboBox<>();
        hargaPerPorsi = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        deskripsiMenu = new javax.swing.JTextArea();
        hapusMenuBtn = new javax.swing.JButton();
        editMenuBtn = new javax.swing.JButton();
        resetMenuBtn = new javax.swing.JButton();
        simpanMenu = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        cariMenuField = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton11 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelPesanan = new javax.swing.JTable();
        cariPesananField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cariPesananBtn = new javax.swing.JButton();
        lihatDetailPesBtn = new javax.swing.JButton();
        updateStatusbtn = new javax.swing.JButton();
        hasilCariPesanan = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        dariTanggalFirld = new javax.swing.JTextField();
        sampaiTanggalField = new javax.swing.JTextField();
        tampilkanDataBtn = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelDataKeuangan = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        totalPendapatanField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(712, 605));

        jPanel1.setBackground(new java.awt.Color(229, 215, 196));

        jLabel1.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(76, 61, 25));
        jLabel1.setText("Halo, Admin !");

        jButton1.setBackground(new java.awt.Color(53, 64, 36));
        jButton1.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(229, 215, 196));
        jButton1.setText("Logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(162, 154, 124));
        jTabbedPane1.setForeground(new java.awt.Color(76, 61, 25));

        jPanel2.setBackground(new java.awt.Color(162, 154, 124));

        jLabel2.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(76, 61, 25));
        jLabel2.setText("Nama Lengkap :");

        jLabel3.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(76, 61, 25));
        jLabel3.setText("Username         :");

        jLabel4.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(76, 61, 25));
        jLabel4.setText("Password          :");

        jLabel5.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(76, 61, 25));
        jLabel5.setText("Role                  :");

        jLabel6.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(76, 61, 25));
        jLabel6.setText("No. Telepon      :");

        jLabel7.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(76, 61, 25));
        jLabel7.setText("Alamat              :");

        namaLengkapField.setBackground(new java.awt.Color(229, 215, 196));
        namaLengkapField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        namaLengkapField.setForeground(new java.awt.Color(76, 61, 25));
        namaLengkapField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaLengkapFieldActionPerformed(evt);
            }
        });

        usernameField.setBackground(new java.awt.Color(229, 215, 196));
        usernameField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        usernameField.setForeground(new java.awt.Color(76, 61, 25));
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        jTextField5.setBackground(new java.awt.Color(229, 215, 196));
        jTextField5.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(76, 61, 25));
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jTextArea1.setBackground(new java.awt.Color(229, 215, 196));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(76, 61, 25));
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        passwordField.setBackground(new java.awt.Color(229, 215, 196));
        passwordField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        passwordField.setForeground(new java.awt.Color(76, 61, 25));
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        hapusBtn.setBackground(new java.awt.Color(134, 15, 15));
        hapusBtn.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        hapusBtn.setForeground(new java.awt.Color(229, 215, 196));
        hapusBtn.setText("Hapus");
        hapusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusBtnActionPerformed(evt);
            }
        });

        EditBtn.setBackground(new java.awt.Color(164, 171, 57));
        EditBtn.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        EditBtn.setForeground(new java.awt.Color(229, 215, 196));
        EditBtn.setText("Edit");
        EditBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditBtnActionPerformed(evt);
            }
        });

        resetBtn.setBackground(new java.awt.Color(96, 96, 96));
        resetBtn.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        resetBtn.setForeground(new java.awt.Color(229, 215, 196));
        resetBtn.setText("Reset");
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        simpanBtn.setBackground(new java.awt.Color(53, 64, 36));
        simpanBtn.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        simpanBtn.setForeground(new java.awt.Color(229, 215, 196));
        simpanBtn.setText("Simpan");
        simpanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanBtnActionPerformed(evt);
            }
        });

        rolePengguna.setBackground(new java.awt.Color(229, 215, 196));
        rolePengguna.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        rolePengguna.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "User" }));

        jLabel20.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(76, 61, 25));
        jLabel20.setText("Input User");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(simpanBtn, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                            .addGap(9, 9, 9)
                                            .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rolePengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(87, 87, 87)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(hapusBtn)
                                    .addComponent(EditBtn)
                                    .addComponent(resetBtn)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(namaLengkapField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel20))
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(namaLengkapField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(hapusBtn)
                                .addGap(16, 16, 16)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rolePengguna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(EditBtn))
                        .addGap(18, 18, 18)
                        .addComponent(resetBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(31, 31, 31)
                .addComponent(simpanBtn)
                .addContainerGap(208, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Input User", jPanel2);

        jPanel3.setBackground(new java.awt.Color(162, 154, 124));

        jTextField3.setBackground(new java.awt.Color(229, 215, 196));
        jTextField3.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(76, 61, 25));

        jTable1.setBackground(new java.awt.Color(229, 215, 196));
        jTable1.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jTable1.setForeground(new java.awt.Color(76, 61, 25));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Username", "Role", "no.Telp"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jButton6.setBackground(new java.awt.Color(53, 64, 36));
        jButton6.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(229, 215, 196));
        jButton6.setText("Cari");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(76, 61, 25));
        jLabel21.setText("Data User");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        jTabbedPane1.addTab("Data User", jPanel3);

        jPanel4.setBackground(new java.awt.Color(162, 154, 124));

        jLabel8.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(76, 61, 25));
        jLabel8.setText("Input Menu");

        jLabel9.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(76, 61, 25));
        jLabel9.setText("Nama Menu    :");

        namaMenuField.setBackground(new java.awt.Color(229, 215, 196));
        namaMenuField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        namaMenuField.setForeground(new java.awt.Color(76, 61, 25));

        jenisMenu.setBackground(new java.awt.Color(229, 215, 196));
        jenisMenu.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jenisMenu.setForeground(new java.awt.Color(76, 61, 25));
        jenisMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jenisMenuActionPerformed(evt);
            }
        });

        hargaPerPorsi.setBackground(new java.awt.Color(229, 215, 196));
        hargaPerPorsi.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        hargaPerPorsi.setForeground(new java.awt.Color(76, 61, 25));

        deskripsiMenu.setBackground(new java.awt.Color(229, 215, 196));
        deskripsiMenu.setColumns(20);
        deskripsiMenu.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        deskripsiMenu.setForeground(new java.awt.Color(76, 61, 25));
        deskripsiMenu.setRows(5);
        jScrollPane3.setViewportView(deskripsiMenu);

        hapusMenuBtn.setBackground(new java.awt.Color(134, 15, 15));
        hapusMenuBtn.setForeground(new java.awt.Color(229, 215, 196));
        hapusMenuBtn.setText("Hapus");
        hapusMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusMenuBtnActionPerformed(evt);
            }
        });

        editMenuBtn.setBackground(new java.awt.Color(164, 171, 57));
        editMenuBtn.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        editMenuBtn.setForeground(new java.awt.Color(229, 215, 196));
        editMenuBtn.setText("Edit");
        editMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuBtnActionPerformed(evt);
            }
        });

        resetMenuBtn.setBackground(new java.awt.Color(96, 96, 96));
        resetMenuBtn.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        resetMenuBtn.setForeground(new java.awt.Color(229, 215, 196));
        resetMenuBtn.setText("Reset");
        resetMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetMenuBtnActionPerformed(evt);
            }
        });

        simpanMenu.setBackground(new java.awt.Color(53, 64, 36));
        simpanMenu.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        simpanMenu.setForeground(new java.awt.Color(229, 215, 196));
        simpanMenu.setText("Simpan");
        simpanMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanMenuActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(76, 61, 25));
        jLabel22.setText("Jenis Menu      :");

        jLabel23.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(76, 61, 25));
        jLabel23.setText("Harga/porsi    :  Rp. ");

        jLabel24.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(76, 61, 25));
        jLabel24.setText("Deskripsi        :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(simpanMenu)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel8)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel24)
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane3))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(18, 18, 18)
                            .addComponent(namaMenuField, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel22)
                            .addGap(18, 18, 18)
                            .addComponent(jenisMenu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(hargaPerPorsi, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(94, 94, 94)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editMenuBtn)
                    .addComponent(hapusMenuBtn)
                    .addComponent(resetMenuBtn))
                .addContainerGap(139, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(namaMenuField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hapusMenuBtn))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jenisMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(editMenuBtn)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(hargaPerPorsi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(resetMenuBtn)))
                .addGap(18, 18, 18)
                .addComponent(simpanMenu)
                .addContainerGap(259, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Input Menu", jPanel4);

        jPanel5.setBackground(new java.awt.Color(162, 154, 124));

        cariMenuField.setBackground(new java.awt.Color(229, 215, 196));
        cariMenuField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N

        jTable2.setBackground(new java.awt.Color(229, 215, 196));
        jTable2.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jTable2.setForeground(new java.awt.Color(76, 61, 25));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama Menu", "Jenis Menu", "Harga", "Deskripsi"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable2);

        jButton11.setBackground(new java.awt.Color(53, 64, 36));
        jButton11.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(229, 215, 196));
        jButton11.setText("Cari");

        jLabel10.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(76, 61, 25));
        jLabel10.setText("Data Menu");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(cariMenuField, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton11))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cariMenuField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Data Menu", jPanel5);

        jPanel6.setBackground(new java.awt.Color(162, 154, 124));

        jLabel13.setBackground(new java.awt.Color(53, 64, 36));
        jLabel13.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(76, 61, 25));
        jLabel13.setText("Daftar Pesanan");

        tabelPesanan.setBackground(new java.awt.Color(229, 215, 196));
        tabelPesanan.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        tabelPesanan.setForeground(new java.awt.Color(76, 61, 25));
        tabelPesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tanggal Acara", "Pemesan", "Lokasi", "Total(Rp)", "Status"
            }
        ));
        tabelPesanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPesananMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabelPesanan);

        cariPesananField.setBackground(new java.awt.Color(229, 215, 196));
        cariPesananField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(76, 61, 25));
        jLabel14.setText("Nama Pemesan  :");

        cariPesananBtn.setBackground(new java.awt.Color(53, 64, 36));
        cariPesananBtn.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        cariPesananBtn.setForeground(new java.awt.Color(229, 215, 196));
        cariPesananBtn.setText("Cari");
        cariPesananBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariPesananBtnActionPerformed(evt);
            }
        });

        lihatDetailPesBtn.setBackground(new java.awt.Color(53, 64, 36));
        lihatDetailPesBtn.setForeground(new java.awt.Color(229, 215, 196));
        lihatDetailPesBtn.setText("Lihat Detail");
        lihatDetailPesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lihatDetailPesBtnActionPerformed(evt);
            }
        });

        updateStatusbtn.setBackground(new java.awt.Color(164, 171, 57));
        updateStatusbtn.setForeground(new java.awt.Color(229, 215, 196));
        updateStatusbtn.setText("Update Status");
        updateStatusbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateStatusbtnActionPerformed(evt);
            }
        });

        hasilCariPesanan.setBackground(new java.awt.Color(162, 154, 124));
        hasilCariPesanan.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        hasilCariPesanan.setForeground(new java.awt.Color(76, 61, 25));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(hasilCariPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                    .addComponent(cariPesananField)
                                    .addGap(157, 157, 157)
                                    .addComponent(cariPesananBtn))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(lihatDetailPesBtn)
                                        .addGap(160, 160, 160)
                                        .addComponent(updateStatusbtn)
                                        .addGap(135, 135, 135))
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cariPesananField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cariPesananBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(hasilCariPesanan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lihatDetailPesBtn)
                    .addComponent(updateStatusbtn))
                .addGap(0, 48, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Daftar Pesanan", jPanel6);

        jPanel7.setBackground(new java.awt.Color(162, 154, 124));

        jLabel15.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(76, 61, 25));
        jLabel15.setText("Laporan Keuangan");

        jLabel16.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(76, 61, 25));
        jLabel16.setText("Dari Tanggal     :");

        jLabel17.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(76, 61, 25));
        jLabel17.setText("Sampai Tanggal :");

        dariTanggalFirld.setBackground(new java.awt.Color(229, 215, 196));
        dariTanggalFirld.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        dariTanggalFirld.setForeground(new java.awt.Color(76, 61, 25));

        sampaiTanggalField.setBackground(new java.awt.Color(229, 215, 196));
        sampaiTanggalField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        sampaiTanggalField.setForeground(new java.awt.Color(76, 61, 25));

        tampilkanDataBtn.setBackground(new java.awt.Color(53, 64, 36));
        tampilkanDataBtn.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        tampilkanDataBtn.setForeground(new java.awt.Color(229, 215, 196));
        tampilkanDataBtn.setText("Tampilkan data");
        tampilkanDataBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampilkanDataBtnActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(76, 61, 25));
        jLabel18.setText("Hasil Laporan");

        tabelDataKeuangan.setBackground(new java.awt.Color(229, 215, 196));
        tabelDataKeuangan.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        tabelDataKeuangan.setForeground(new java.awt.Color(76, 61, 25));
        tabelDataKeuangan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Tgl Transaksi", "ID", "Pemesan", "Status", "Metode bayar", "Pendapatan"
            }
        ));
        jScrollPane6.setViewportView(tabelDataKeuangan);

        jLabel19.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(76, 61, 25));
        jLabel19.setText("Total Pendapatan :  Rp.");

        totalPendapatanField.setBackground(new java.awt.Color(162, 154, 124));
        totalPendapatanField.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N
        totalPendapatanField.setForeground(new java.awt.Color(76, 61, 25));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(sampaiTanggalField, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dariTanggalFirld)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tampilkanDataBtn))
                            .addComponent(jLabel15)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalPendapatanField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(dariTanggalFirld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tampilkanDataBtn)
                        .addComponent(sampaiTanggalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(totalPendapatanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Laporan Keuangan", jPanel7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jTabbedPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void namaLengkapFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaLengkapFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaLengkapFieldActionPerformed

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordFieldActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void simpanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanBtnActionPerformed
        String nama = namaLengkapField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = rolePengguna.getSelectedItem().toString();
        String noTelp = jTextField5.getText();
        String alamat = jTextArea1.getText();

        if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama, Username, dan Password wajib diisi!");
            return;
        }

        User u = new User();
        u.setNama(nama);
        u.setUsername(username);
        u.setPassword(password);
        u.setRole(role);
        u.setNoTelp(noTelp);
        u.setAlamat(alamat);

        if (u.tambahUser()) {
            JOptionPane.showMessageDialog(this, "User Berhasil Ditambahkan!");
            clearUserForm();
            loadDataUser();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambah user.");
        }
    }//GEN-LAST:event_simpanBtnActionPerformed

    private void simpanMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanMenuActionPerformed
        try {
            String nama = namaMenuField.getText();
            String jenis = jenisMenu.getSelectedItem().toString();
            int harga = Integer.parseInt(hargaPerPorsi.getText());
            String desk = deskripsiMenu.getText();

            Menu menuBaru = new Menu();
            menuBaru.setNamaMenu(nama);
            menuBaru.setJenisMenu(jenis);
            menuBaru.setHargaPerPorsi(harga);
            menuBaru.setDeskripsi(desk);

            if(menuBaru.tambahMenu()) {
                JOptionPane.showMessageDialog(this, "Menu Berhasil Disimpan!");
                loadDataMenu(); // Refresh tabel di tab sebelah
                // Kosongkan form (opsional: buat method clearMenuForm())
                namaMenuField.setText("");
                hargaPerPorsi.setText("");
                deskripsiMenu.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan menu.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_simpanMenuActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // Ambil baris yang diklik
        int baris = jTable2.rowAtPoint(evt.getPoint());
        
        if (baris >= 0) {
            // Ambil ID dari kolom ke-0 (Pastikan urutan kolom tabel Anda: ID, Nama, Jenis, Harga, Deskripsi)
            idMenuTarget = Integer.parseInt(jTable2.getValueAt(baris, 0).toString());
            
            // Isi Form Input dengan data dari tabel
            namaMenuField.setText(jTable2.getValueAt(baris, 1).toString());
            
            // Set ComboBox (pastikan string-nya sama persis dengan yang ada di item combobox)
            jenisMenu.setSelectedItem(jTable2.getValueAt(baris, 2).toString());
            
            hargaPerPorsi.setText(jTable2.getValueAt(baris, 3).toString());
            deskripsiMenu.setText(jTable2.getValueAt(baris, 4).toString());
            
            // Pindah otomatis ke Tab "Input Menu" (Tab index 2) agar user bisa langsung edit
            jTabbedPane1.setSelectedIndex(2);
            
            // Opsional: Ubah teks tombol Simpan jadi "Update" biar user sadar ini mode edit
            // Tapi karena Anda punya tombol "Edit" terpisah, kita pakai tombol Edit saja.
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void editMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuBtnActionPerformed
        if (idMenuTarget == 0) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel 'Data Menu' terlebih dahulu!");
            return;
        }

        try {
            // Siapkan objek Menu dengan data baru dari form
            Model.Menu m = new Model.Menu();
            m.setIdMenu(idMenuTarget); // PENTING: Set ID agar tahu mana yang di-update
            m.setNamaMenu(namaMenuField.getText());
            m.setJenisMenu(jenisMenu.getSelectedItem().toString());
            m.setHargaPerPorsi(Integer.parseInt(hargaPerPorsi.getText()));
            m.setDeskripsi(deskripsiMenu.getText());

            // Panggil method updateMenu dari Model
            if (m.updateMenu()) {
                JOptionPane.showMessageDialog(this, "Data Berhasil Diubah!");
                loadDataMenu(); // Refresh tabel
                clearMenuForm(); // Bersihkan form
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengubah data.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus angka!");
        }
    }//GEN-LAST:event_editMenuBtnActionPerformed

    private void hapusMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusMenuBtnActionPerformed
        if (idMenuTarget == 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus dari tabel!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this, 
                "Yakin ingin menghapus menu ini?", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            Model.Menu m = new Model.Menu();
            m.setIdMenu(idMenuTarget);
            
            if (m.deleteMenu()) {
                JOptionPane.showMessageDialog(this, "Data Berhasil Dihapus!");
                loadDataMenu();
                clearMenuForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data.");
            }
        }
    }//GEN-LAST:event_hapusMenuBtnActionPerformed

    private void resetMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetMenuBtnActionPerformed
        clearMenuForm();
    }//GEN-LAST:event_resetMenuBtnActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int baris = jTable1.rowAtPoint(evt.getPoint());
        if (baris >= 0) {
            idUserTarget = Integer.parseInt(jTable1.getValueAt(baris, 0).toString());
            
            // Isi Form
            namaLengkapField.setText(jTable1.getValueAt(baris, 1).toString());
            usernameField.setText(jTable1.getValueAt(baris, 2).toString());
            rolePengguna.setSelectedItem(jTable1.getValueAt(baris, 3).toString());
            jTextField5.setText(jTable1.getValueAt(baris, 4).toString()); // No Telp
            
            // Note: Password tidak kita load balik demi keamanan
            passwordField.setText(""); 
            
            // Pindah Tab otomatis
            jTabbedPane1.setSelectedIndex(0); // Tab Input User
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void EditBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditBtnActionPerformed
        if (idUserTarget == 0) {
            JOptionPane.showMessageDialog(this, "Pilih user dari tabel terlebih dahulu!");
            return;
        }

        User u = new User();
        u.setIdUser(idUserTarget);
        u.setNama(namaLengkapField.getText());
        u.setUsername(usernameField.getText());
        u.setRole(rolePengguna.getSelectedItem().toString());
        u.setNoTelp(jTextField5.getText());
        u.setAlamat(jTextArea1.getText());
        
        // Cek apakah password diisi (ingin diubah) atau kosong (tetap password lama)
        String pass = new String(passwordField.getPassword());
        if (!pass.isEmpty()) {
            u.setPassword(pass);
        }

        if (u.updateUser()) {
            JOptionPane.showMessageDialog(this, "Data User Berhasil Diubah!");
            clearUserForm();
            loadDataUser();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data.");
        }
    }//GEN-LAST:event_EditBtnActionPerformed

    private void hapusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusBtnActionPerformed
        if (idUserTarget == 0) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin dihapus!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Hapus user ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            User u = new User();
            u.setIdUser(idUserTarget);
            if (u.deleteUser()) {
                JOptionPane.showMessageDialog(this, "User Berhasil Dihapus!");
                clearUserForm();
                loadDataUser();
            }
        }
    }//GEN-LAST:event_hapusBtnActionPerformed

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        clearUserForm();
    }//GEN-LAST:event_resetBtnActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String keyword = jTextField3.getText();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        List<User> users = User.cariUser(keyword);
        for (User u : users) {
            model.addRow(new Object[]{
                u.getIdUser(),
                u.getNama(),
                u.getUsername(),
                u.getRole(),
                u.getNoTelp()
            });
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tabelPesananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPesananMouseClicked
        int baris = tabelPesanan.rowAtPoint(evt.getPoint());
        if (baris >= 0) {
            idPesananTarget = Integer.parseInt(tabelPesanan.getValueAt(baris, 0).toString());
        }
    }//GEN-LAST:event_tabelPesananMouseClicked

    private void updateStatusbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateStatusbtnActionPerformed
        if (idPesananTarget == 0) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan dari tabel dulu!");
            return;
        }
        
        // Panggil JDialog Update
        new UpdateStatusBayarJDialog(this, true, idPesananTarget).setVisible(true);
        
        // Setelah dialog tertutup, refresh tabel admin agar status berubah
        loadDataPesanan();
    }//GEN-LAST:event_updateStatusbtnActionPerformed

    private void cariPesananBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariPesananBtnActionPerformed
        String keyword = cariPesananField.getText();
        DefaultTableModel model = (DefaultTableModel) tabelPesanan.getModel();
        model.setRowCount(0);
        
        List<Pesanan> list = Pesanan.cariPesanan(keyword);
        for (Pesanan p : list) {
            model.addRow(new Object[]{
                p.getIdPesanan(),
                p.getTglAcara(),
                p.getNamaPemesan(),
                p.getLokasiAcara(),
                p.getTotalHarga(),
                p.getStatus()
            });
        }
    }//GEN-LAST:event_cariPesananBtnActionPerformed

    private void tampilkanDataBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tampilkanDataBtnActionPerformed
        String tglAwal = dariTanggalFirld.getText();
        String tglAkhir = sampaiTanggalField.getText();

        // Panggil method helper tadi
        tampilkanLaporan(tglAwal, tglAkhir);
    }//GEN-LAST:event_tampilkanDataBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin logout?", 
                "Konfirmasi Logout", 
                javax.swing.JOptionPane.YES_NO_OPTION);
        
        if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose(); // Tutup halaman Admin
            new Auth.LoginJFrame().setVisible(true); // Kembali ke halaman Login
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jenisMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jenisMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jenisMenuActionPerformed

    private void lihatDetailPesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihatDetailPesBtnActionPerformed
        if (idPesananTarget == 0) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan dari tabel dulu!");
            return;
        }
        
        // Panggil JDialog Detail
        new DetailPesananJDialog(this, true, idPesananTarget).setVisible(true);
    }//GEN-LAST:event_lihatDetailPesBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new AdminJFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EditBtn;
    private javax.swing.JTextField cariMenuField;
    private javax.swing.JButton cariPesananBtn;
    private javax.swing.JTextField cariPesananField;
    private javax.swing.JTextField dariTanggalFirld;
    private javax.swing.JTextArea deskripsiMenu;
    private javax.swing.JButton editMenuBtn;
    private javax.swing.JButton hapusBtn;
    private javax.swing.JButton hapusMenuBtn;
    private javax.swing.JTextField hargaPerPorsi;
    private javax.swing.JTextField hasilCariPesanan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JComboBox<String> jenisMenu;
    private javax.swing.JButton lihatDetailPesBtn;
    private javax.swing.JTextField namaLengkapField;
    private javax.swing.JTextField namaMenuField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton resetBtn;
    private javax.swing.JButton resetMenuBtn;
    private javax.swing.JComboBox<String> rolePengguna;
    private javax.swing.JTextField sampaiTanggalField;
    private javax.swing.JButton simpanBtn;
    private javax.swing.JButton simpanMenu;
    private javax.swing.JTable tabelDataKeuangan;
    private javax.swing.JTable tabelPesanan;
    private javax.swing.JButton tampilkanDataBtn;
    private javax.swing.JTextField totalPendapatanField;
    private javax.swing.JButton updateStatusbtn;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
