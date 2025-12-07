/*
* Nama Program : NyamBeans
* Nama         : Vincentius Raynard Thedy, Nena Haryadi Puspanegara, Alfon Daren Witanto
* NPM          : 140810240028, 140810240034, 140810240052
* Tanggal buat : 28 November - 4 Desember 2025
* Deskripsi    : AdminJFrame.java (JFrame class)
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
        javax.swing.table.DefaultTableModel modelUser = new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "ID", "Nama Lengkap", "Username", "Role", "No. Telp", "Alamat" }
        ) {
            boolean[] canEdit = new boolean [] { false, false, false, false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        dataUserTable.setModel(modelUser);

        dataUserTable.getColumnModel().getColumn(0).setMinWidth(0);
        dataUserTable.getColumnModel().getColumn(0).setMaxWidth(0);
        dataUserTable.getColumnModel().getColumn(0).setWidth(0);

        jenisMenuComboBox.removeAllItems();
        jenisMenuComboBox.addItem("Paket Nasi Ekonomis");
        jenisMenuComboBox.addItem("Paket Nasi Standar");
        jenisMenuComboBox.addItem("Paket Nasi Premium");
        jenisMenuComboBox.addItem("Paket Snack Ekonomis");
        jenisMenuComboBox.addItem("Paket Snack Standar");
        jenisMenuComboBox.addItem("Paket Snack Premium");
        jenisMenuComboBox.addItem("Tambahan");

        loadDataMenu();
        loadDataUser();     
        loadDataPesanan();
        initLaporanKeuangan();
    }
    
    private void loadDataMenu() {
        DefaultTableModel model = (DefaultTableModel) dataMenuTable.getModel();
        model.setRowCount(0);
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        String tglAkhir = sdf.format(cal.getTime()); //tanggal akhir bulan ini
        sampaiTanggalField.setText(tglAkhir);
        cal.set(Calendar.DAY_OF_MONTH, 1); // tanggal awal bulan ini
        String tglAwal = sdf.format(cal.getTime());
        dariTanggalField.setText(tglAwal);

        tampilkanLaporan(tglAwal, tglAkhir);
    }
    
    private void tampilkanLaporan(String tglAwalStr, String tglAkhirStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date tglAwal = sdf.parse(tglAwalStr);
            Date tglAkhir = sdf.parse(tglAkhirStr);

            List<Pesanan> laporan = Pesanan.getLaporan(tglAwal, tglAkhir);
            DefaultTableModel model = (DefaultTableModel) tabelDataKeuangan.getModel();
            model.setRowCount(0);

            long totalPendapatan = 0;

            for (Pesanan p : laporan) {
                model.addRow(new Object[]{
                    p.getTglAcara(), // tanggal
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
            JOptionPane.showMessageDialog(this, 
            "Format tanggal salah! Gunakan format tahun-bulan-tanggal (contoh: 2025-11-30).", 
            "Format Tanggal Error", 
            JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void clearMenuForm() {
        idMenuTarget = 0;
        namaMenuField.setText("");
        jenisMenuComboBox.setSelectedIndex(0);
        hargaPerPorsiField.setText("");
        deskripsiMenuTextArea.setText("");
        
        simpanMenu.setText("Simpan"); 
        namaMenuField.requestFocus();
    }
    
    // Method untuk user
    private void loadDataUser() {
        DefaultTableModel model = (DefaultTableModel) dataUserTable.getModel();
        model.setRowCount(0);
        
        List<User> users = User.getAllUsers();
        for (User u : users) {
            model.addRow(new Object[]{
                u.getIdUser(),
                u.getNama(),
                u.getUsername(),
                u.getRole(),
                u.getNoTelp(),
                u.getAlamat() 
            });
        }
    }

    private void clearUserForm() {
        idUserTarget = 0;
        namaLengkapField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        noTelpField.setText(""); 
        emailField.setText("");
        alamatTextArea.setText("");  
        rolePenggunaComboBox.setSelectedIndex(0);
        
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

        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        namaLengkapLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        roleLabel = new javax.swing.JLabel();
        noTelpLabel = new javax.swing.JLabel();
        alamatLabel = new javax.swing.JLabel();
        namaLengkapField = new javax.swing.JTextField();
        usernameField = new javax.swing.JTextField();
        noTelpField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        alamatTextArea = new javax.swing.JTextArea();
        passwordField = new javax.swing.JPasswordField();
        resetBtn = new javax.swing.JButton();
        simpanBtn = new javax.swing.JButton();
        rolePenggunaComboBox = new javax.swing.JComboBox<>();
        inputUserLabel = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        showPassCheck = new javax.swing.JCheckBox();
        copyright5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cariField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        dataUserTable = new javax.swing.JTable();
        cariButton = new javax.swing.JButton();
        dataUserLabel = new javax.swing.JLabel();
        copyright4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        inputMenuLabel = new javax.swing.JLabel();
        namaMenuLabel = new javax.swing.JLabel();
        namaMenuField = new javax.swing.JTextField();
        jenisMenuComboBox = new javax.swing.JComboBox<>();
        hargaPerPorsiField = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        deskripsiMenuTextArea = new javax.swing.JTextArea();
        hapusMenuBtn = new javax.swing.JButton();
        editMenuBtn = new javax.swing.JButton();
        resetMenuBtn = new javax.swing.JButton();
        simpanMenu = new javax.swing.JButton();
        jenisMenuLabel = new javax.swing.JLabel();
        hargaPerPorsiLabel = new javax.swing.JLabel();
        deskripsiLabel = new javax.swing.JLabel();
        copyright3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        cariMenuField = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        dataMenuTable = new javax.swing.JTable();
        cariMenuButton = new javax.swing.JButton();
        dataMenuLabel = new javax.swing.JLabel();
        copyright2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        daftarPesananLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelPesanan = new javax.swing.JTable();
        cariPesananField = new javax.swing.JTextField();
        cariPesananBtn = new javax.swing.JButton();
        lihatDetailPesBtn = new javax.swing.JButton();
        updateStatusbtn = new javax.swing.JButton();
        copyright1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        laporanKeuanganLabel = new javax.swing.JLabel();
        dariTglLabel = new javax.swing.JLabel();
        sampaiTglLabel = new javax.swing.JLabel();
        dariTanggalField = new javax.swing.JTextField();
        sampaiTanggalField = new javax.swing.JTextField();
        tampilkanDataBtn = new javax.swing.JButton();
        hasilLaporanLabel = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelDataKeuangan = new javax.swing.JTable();
        totalPendapatanLabel = new javax.swing.JLabel();
        totalPendapatanField = new javax.swing.JTextField();
        copyright = new javax.swing.JLabel();

        jButton2.setText("jButton2");

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

        namaLengkapLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        namaLengkapLabel.setForeground(new java.awt.Color(76, 61, 25));
        namaLengkapLabel.setText("Nama Lengkap :");

        usernameLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        usernameLabel.setForeground(new java.awt.Color(76, 61, 25));
        usernameLabel.setText("Username         :");

        passwordLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(76, 61, 25));
        passwordLabel.setText("Password          :");

        roleLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        roleLabel.setForeground(new java.awt.Color(76, 61, 25));
        roleLabel.setText("Role                  :");

        noTelpLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        noTelpLabel.setForeground(new java.awt.Color(76, 61, 25));
        noTelpLabel.setText("No. Telepon      :");

        alamatLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        alamatLabel.setForeground(new java.awt.Color(76, 61, 25));
        alamatLabel.setText("Alamat              :");

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

        noTelpField.setBackground(new java.awt.Color(229, 215, 196));
        noTelpField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        noTelpField.setForeground(new java.awt.Color(76, 61, 25));
        noTelpField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noTelpFieldActionPerformed(evt);
            }
        });

        alamatTextArea.setBackground(new java.awt.Color(229, 215, 196));
        alamatTextArea.setColumns(20);
        alamatTextArea.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        alamatTextArea.setForeground(new java.awt.Color(76, 61, 25));
        alamatTextArea.setRows(5);
        jScrollPane1.setViewportView(alamatTextArea);

        passwordField.setBackground(new java.awt.Color(229, 215, 196));
        passwordField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        passwordField.setForeground(new java.awt.Color(76, 61, 25));
        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
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

        rolePenggunaComboBox.setBackground(new java.awt.Color(229, 215, 196));
        rolePenggunaComboBox.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        rolePenggunaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "Admin", " " }));
        rolePenggunaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rolePenggunaComboBoxActionPerformed(evt);
            }
        });

        inputUserLabel.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        inputUserLabel.setForeground(new java.awt.Color(76, 61, 25));
        inputUserLabel.setText("Tambah Akun");

        emailField.setBackground(new java.awt.Color(229, 215, 196));
        emailField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        emailField.setForeground(new java.awt.Color(76, 61, 25));
        emailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailFieldActionPerformed(evt);
            }
        });

        emailLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(76, 61, 25));
        emailLabel.setText("Email                :");

        showPassCheck.setText("Lihat");
        showPassCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showPassCheckActionPerformed(evt);
            }
        });

        copyright5.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        copyright5.setForeground(new java.awt.Color(76, 61, 25));
        copyright5.setText("Copyright : Raynard - 240028 / Nena - 240034 / Darren - 240052");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(emailLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(noTelpLabel)
                        .addGap(12, 12, 12)
                        .addComponent(noTelpField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameLabel)
                            .addComponent(passwordLabel)
                            .addComponent(roleLabel))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(usernameField, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                                    .addComponent(passwordField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showPassCheck))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rolePenggunaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(inputUserLabel)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(namaLengkapLabel)
                        .addGap(14, 14, 14)
                        .addComponent(namaLengkapField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(resetBtn)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(228, 228, 228)
                                .addComponent(simpanBtn)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(alamatLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(copyright5))
                .addGap(167, 167, 167))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(inputUserLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(namaLengkapLabel)
                    .addComponent(namaLengkapField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showPassCheck))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rolePenggunaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(noTelpLabel)
                    .addComponent(noTelpField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel)
                    .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alamatLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpanBtn)
                    .addComponent(resetBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(copyright5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jTabbedPane1.addTab("Tambah Akun", jPanel2);

        jPanel3.setBackground(new java.awt.Color(162, 154, 124));

        cariField.setBackground(new java.awt.Color(229, 215, 196));
        cariField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        cariField.setForeground(new java.awt.Color(76, 61, 25));

        dataUserTable.setBackground(new java.awt.Color(229, 215, 196));
        dataUserTable.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        dataUserTable.setForeground(new java.awt.Color(76, 61, 25));
        dataUserTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nama", "Username", "Role", "No. Telp", "Alamat"
            }
        ));
        dataUserTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dataUserTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(dataUserTable);

        cariButton.setBackground(new java.awt.Color(53, 64, 36));
        cariButton.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        cariButton.setForeground(new java.awt.Color(229, 215, 196));
        cariButton.setText("Cari");
        cariButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariButtonActionPerformed(evt);
            }
        });

        dataUserLabel.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        dataUserLabel.setForeground(new java.awt.Color(76, 61, 25));
        dataUserLabel.setText("Data User");

        copyright4.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        copyright4.setForeground(new java.awt.Color(76, 61, 25));
        copyright4.setText("Copyright : Raynard - 240028 / Nena - 240034 / Darren - 240052");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(copyright4)
                    .addComponent(dataUserLabel)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(cariField, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cariButton))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(dataUserLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cariField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cariButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addComponent(copyright4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jTabbedPane1.addTab("Data User", jPanel3);

        jPanel4.setBackground(new java.awt.Color(162, 154, 124));

        inputMenuLabel.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        inputMenuLabel.setForeground(new java.awt.Color(76, 61, 25));
        inputMenuLabel.setText("Input Menu");

        namaMenuLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        namaMenuLabel.setForeground(new java.awt.Color(76, 61, 25));
        namaMenuLabel.setText("Nama Menu    :");

        namaMenuField.setBackground(new java.awt.Color(229, 215, 196));
        namaMenuField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        namaMenuField.setForeground(new java.awt.Color(76, 61, 25));

        jenisMenuComboBox.setBackground(new java.awt.Color(229, 215, 196));
        jenisMenuComboBox.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jenisMenuComboBox.setForeground(new java.awt.Color(76, 61, 25));
        jenisMenuComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jenisMenuComboBoxActionPerformed(evt);
            }
        });

        hargaPerPorsiField.setBackground(new java.awt.Color(229, 215, 196));
        hargaPerPorsiField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        hargaPerPorsiField.setForeground(new java.awt.Color(76, 61, 25));

        deskripsiMenuTextArea.setBackground(new java.awt.Color(229, 215, 196));
        deskripsiMenuTextArea.setColumns(20);
        deskripsiMenuTextArea.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        deskripsiMenuTextArea.setForeground(new java.awt.Color(76, 61, 25));
        deskripsiMenuTextArea.setRows(5);
        jScrollPane3.setViewportView(deskripsiMenuTextArea);

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

        jenisMenuLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        jenisMenuLabel.setForeground(new java.awt.Color(76, 61, 25));
        jenisMenuLabel.setText("Jenis Menu      :");

        hargaPerPorsiLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        hargaPerPorsiLabel.setForeground(new java.awt.Color(76, 61, 25));
        hargaPerPorsiLabel.setText("Harga/porsi    :      Rp. ");

        deskripsiLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        deskripsiLabel.setForeground(new java.awt.Color(76, 61, 25));
        deskripsiLabel.setText("Deskripsi        :");

        copyright3.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        copyright3.setForeground(new java.awt.Color(76, 61, 25));
        copyright3.setText("Copyright : Raynard - 240028 / Nena - 240034 / Darren - 240052");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(simpanMenu)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(copyright3)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inputMenuLabel)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(deskripsiLabel)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(namaMenuLabel)
                                .addGap(18, 18, 18)
                                .addComponent(namaMenuField, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jenisMenuLabel)
                                .addGap(18, 18, 18)
                                .addComponent(jenisMenuComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(hargaPerPorsiLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hargaPerPorsiField, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hapusMenuBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editMenuBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resetMenuBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(81, 81, 81))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(inputMenuLabel)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(namaMenuLabel)
                            .addComponent(namaMenuField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jenisMenuLabel)
                            .addComponent(jenisMenuComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hargaPerPorsiLabel)
                            .addComponent(hargaPerPorsiField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(hapusMenuBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editMenuBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resetMenuBtn)
                    .addComponent(deskripsiLabel)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(simpanMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(copyright3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        jTabbedPane1.addTab("Input Menu", jPanel4);

        jPanel5.setBackground(new java.awt.Color(162, 154, 124));

        cariMenuField.setBackground(new java.awt.Color(229, 215, 196));
        cariMenuField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N

        dataMenuTable.setBackground(new java.awt.Color(229, 215, 196));
        dataMenuTable.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        dataMenuTable.setForeground(new java.awt.Color(76, 61, 25));
        dataMenuTable.setModel(new javax.swing.table.DefaultTableModel(
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
        dataMenuTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dataMenuTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(dataMenuTable);

        cariMenuButton.setBackground(new java.awt.Color(53, 64, 36));
        cariMenuButton.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        cariMenuButton.setForeground(new java.awt.Color(229, 215, 196));
        cariMenuButton.setText("Cari");

        dataMenuLabel.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        dataMenuLabel.setForeground(new java.awt.Color(76, 61, 25));
        dataMenuLabel.setText("Data Menu");

        copyright2.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        copyright2.setForeground(new java.awt.Color(76, 61, 25));
        copyright2.setText("Copyright : Raynard - 240028 / Nena - 240034 / Darren - 240052");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(copyright2)
                    .addComponent(dataMenuLabel)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(cariMenuField, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cariMenuButton))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(dataMenuLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cariMenuField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cariMenuButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(copyright2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Data Menu", jPanel5);

        jPanel6.setBackground(new java.awt.Color(162, 154, 124));

        daftarPesananLabel.setBackground(new java.awt.Color(53, 64, 36));
        daftarPesananLabel.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        daftarPesananLabel.setForeground(new java.awt.Color(76, 61, 25));
        daftarPesananLabel.setText("Daftar Pesanan");

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
                "ID", "Tanggal Acara", "Pemesan", "Lokasi", "Total (Rp)", "Status"
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

        copyright1.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        copyright1.setForeground(new java.awt.Color(76, 61, 25));
        copyright1.setText("Copyright : Raynard - 240028 / Nena - 240034 / Darren - 240052");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 74, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(copyright1)
                    .addComponent(daftarPesananLabel)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(cariPesananField)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cariPesananBtn)
                            .addGap(151, 151, 151))
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lihatDetailPesBtn)
                        .addGap(116, 116, 116)
                        .addComponent(updateStatusbtn)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(daftarPesananLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cariPesananField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cariPesananBtn))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lihatDetailPesBtn)
                    .addComponent(updateStatusbtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(copyright1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jTabbedPane1.addTab("Daftar Pesanan", jPanel6);

        jPanel7.setBackground(new java.awt.Color(162, 154, 124));

        laporanKeuanganLabel.setFont(new java.awt.Font("Constantia", 1, 24)); // NOI18N
        laporanKeuanganLabel.setForeground(new java.awt.Color(76, 61, 25));
        laporanKeuanganLabel.setText("Laporan Keuangan");

        dariTglLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        dariTglLabel.setForeground(new java.awt.Color(76, 61, 25));
        dariTglLabel.setText("Dari Tanggal     :");

        sampaiTglLabel.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        sampaiTglLabel.setForeground(new java.awt.Color(76, 61, 25));
        sampaiTglLabel.setText("Sampai Tanggal :");

        dariTanggalField.setBackground(new java.awt.Color(229, 215, 196));
        dariTanggalField.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        dariTanggalField.setForeground(new java.awt.Color(76, 61, 25));

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

        hasilLaporanLabel.setFont(new java.awt.Font("Constantia", 0, 18)); // NOI18N
        hasilLaporanLabel.setForeground(new java.awt.Color(76, 61, 25));
        hasilLaporanLabel.setText("Hasil Laporan");

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
        tabelDataKeuangan.setEnabled(false);
        jScrollPane6.setViewportView(tabelDataKeuangan);

        totalPendapatanLabel.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N
        totalPendapatanLabel.setForeground(new java.awt.Color(76, 61, 25));
        totalPendapatanLabel.setText("Total Pendapatan :  Rp.");

        totalPendapatanField.setEditable(false);
        totalPendapatanField.setBackground(new java.awt.Color(162, 154, 124));
        totalPendapatanField.setFont(new java.awt.Font("Constantia", 0, 14)); // NOI18N
        totalPendapatanField.setForeground(new java.awt.Color(76, 61, 25));

        copyright.setFont(new java.awt.Font("Constantia", 0, 12)); // NOI18N
        copyright.setForeground(new java.awt.Color(76, 61, 25));
        copyright.setText("Copyright : Raynard - 240028 / Nena - 240034 / Darren - 240052");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(sampaiTglLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(sampaiTanggalField, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addComponent(dariTglLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dariTanggalField, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(laporanKeuanganLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tampilkanDataBtn))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hasilLaporanLabel)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(totalPendapatanLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(totalPendapatanField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(copyright))))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(laporanKeuanganLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dariTglLabel)
                    .addComponent(dariTanggalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sampaiTglLabel)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tampilkanDataBtn)
                        .addComponent(sampaiTanggalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hasilLaporanLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalPendapatanLabel)
                    .addComponent(totalPendapatanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(copyright)
                .addContainerGap(10, Short.MAX_VALUE))
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
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE))
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void namaLengkapFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        usernameField.requestFocus();
    }

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        passwordField.requestFocus();
    }

    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        noTelpField.requestFocus();
    }
    private void noTelpFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        emailField.requestFocus();
    }

    private void simpanBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String nama = namaLengkapField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = rolePenggunaComboBox.getSelectedItem().toString();
        String noTelp = noTelpField.getText().trim();
        String email = emailField.getText().trim();
        String alamat = alamatTextArea.getText();
        
        if (!noTelp.matches("\\d+")) { // Regex: \d+  hanya digit angka
            JOptionPane.showMessageDialog(this, "No. Telepon harus berupa angka!");
            return;
        }
        
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            JOptionPane.showMessageDialog(this, "Format Email tidak valid!");
            return;
        }
        
        if (password.length() < 6 || password.length() > 8) {
            JOptionPane.showMessageDialog(this, 
                "Password harus 6 sampai 8 karakter!", 
                "Password Tidak Valid", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (nama.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama, Username, Password, dan Email wajib diisi!");
            return;
        }

        User u = new User();
        u.setNama(nama);
        u.setUsername(username);
        u.setPassword(password);
        u.setRole(role);
        u.setNoTelp(noTelp);
        u.setEmail(email);
        u.setAlamat(alamat);

        if (u.tambahUser()) {
            JOptionPane.showMessageDialog(this, "User Berhasil Ditambahkan!");
            clearUserForm();
            loadDataUser();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambah user.");
        }
    }

    private void simpanMenuActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String nama = namaMenuField.getText();
            String jenis = jenisMenuComboBox.getSelectedItem().toString();
            int harga = Integer.parseInt(hargaPerPorsiField.getText());
            String desk = deskripsiMenuTextArea.getText();

            Menu menuBaru = new Menu();
            menuBaru.setNamaMenu(nama);
            menuBaru.setJenisMenu(jenis);
            menuBaru.setHargaPerPorsi(harga);
            menuBaru.setDeskripsi(desk);

            if(menuBaru.tambahMenu()) {
                JOptionPane.showMessageDialog(this, "Menu Berhasil Disimpan!");
                loadDataMenu();
                namaMenuField.setText("");
                hargaPerPorsiField.setText("");
                deskripsiMenuTextArea.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan menu.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void dataMenuTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataMenuTableMouseClicked
        int baris = dataMenuTable.rowAtPoint(evt.getPoint());// Ambil baris yang diklik
        
        if (baris >= 0) {
            // ambil id dari kolom ke-0
            idMenuTarget = Integer.parseInt(dataMenuTable.getValueAt(baris, 0).toString());
            namaMenuField.setText(dataMenuTable.getValueAt(baris, 1).toString());
            
            jenisMenuComboBox.setSelectedItem(dataMenuTable.getValueAt(baris, 2).toString());
            hargaPerPorsiField.setText(dataMenuTable.getValueAt(baris, 3).toString());
            deskripsiMenuTextArea.setText(dataMenuTable.getValueAt(baris, 4).toString());
            jTabbedPane1.setSelectedIndex(2); // pindah otomatis
        }
    }

    private void editMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (idMenuTarget == 0) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel 'Data Menu' terlebih dahulu!");
            return;
        }

        try {
            Model.Menu m = new Model.Menu();
            m.setIdMenu(idMenuTarget); 
            m.setNamaMenu(namaMenuField.getText());
            m.setJenisMenu(jenisMenuComboBox.getSelectedItem().toString());
            m.setHargaPerPorsi(Integer.parseInt(hargaPerPorsiField.getText()));
            m.setDeskripsi(deskripsiMenuTextArea.getText());

            if (m.updateMenu()) {
                JOptionPane.showMessageDialog(this, "Data Berhasil Diubah!");
                loadDataMenu();
                clearMenuForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengubah data.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus angka!");
        }
    }

    private void hapusMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {
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
    }

    private void resetMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {
        clearMenuForm();
    }

    private void dataUserTableMouseClicked(java.awt.event.MouseEvent evt) {
        // Kita pakai double click
        if (evt.getClickCount() == 2) {
            int baris = dataUserTable.rowAtPoint(evt.getPoint());
            if (baris >= 0) {
                int idUser = Integer.parseInt(dataUserTable.getValueAt(baris, 0).toString());
                new DetailUserJDialog(this, true, idUser).setVisible(true);
                loadDataUser();
            }
        }
    }

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {
        clearUserForm();
    }

    private void cariButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String keyword = cariField.getText();
        DefaultTableModel model = (DefaultTableModel) dataUserTable.getModel();
        model.setRowCount(0);
        
        List<User> users = User.cariUser(keyword);
        for (User u : users) {
            model.addRow(new Object[]{
                u.getIdUser(),
                u.getNama(),
                u.getUsername(),
                u.getRole(),
                u.getNoTelp(),
                u.getAlamat()
            });
        }
    }

    private void tabelPesananMouseClicked(java.awt.event.MouseEvent evt) {
        int baris = tabelPesanan.rowAtPoint(evt.getPoint());
        if (baris >= 0) {
            idPesananTarget = Integer.parseInt(tabelPesanan.getValueAt(baris, 0).toString());
        }
    }

    private void updateStatusbtnActionPerformed(java.awt.event.ActionEvent evt) {
        if (idPesananTarget == 0) {
            JOptionPane.showMessageDialog(this, "Pilih Pelanggan dari tabel dulu!");
            return;
        }

        new UpdateStatusBayarJDialog(this, true, idPesananTarget).setVisible(true); // panggil JDialog Update
        loadDataPesanan();
    }

    private void cariPesananBtnActionPerformed(java.awt.event.ActionEvent evt) {
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
    }

    private void tampilkanDataBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String tglAwal = dariTanggalField.getText();
        String tglAkhir = sampaiTanggalField.getText();

        tampilkanLaporan(tglAwal, tglAkhir);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin logout?", 
                "Konfirmasi Logout", 
                javax.swing.JOptionPane.YES_NO_OPTION);
        
        if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
            this.dispose(); 
            new Auth.LoginJFrame().setVisible(true); // kembali ke halaman Login
        }
    }

    private void jenisMenuComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void lihatDetailPesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihatDetailPesBtnActionPerformed
        if (idPesananTarget == 0) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan dari tabel dulu!");
            return;
        }
        
        new DetailPesananJDialog(this, true, idPesananTarget).setVisible(true);// panggil Jdialog detail
    }

    private void rolePenggunaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void emailFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        alamatTextArea.requestFocus();
    }

    private void showPassCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showPassCheckActionPerformed
        // TODO add your handling code here:
        if (showPassCheck.isSelected()) {
            passwordField.setEchoChar((char) 0); 
        } else {
            passwordField.setEchoChar('*'); 
        }
    }

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

        java.awt.EventQueue.invokeLater(() -> new AdminJFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alamatLabel;
    private javax.swing.JTextArea alamatTextArea;
    private javax.swing.JButton cariButton;
    private javax.swing.JTextField cariField;
    private javax.swing.JButton cariMenuButton;
    private javax.swing.JTextField cariMenuField;
    private javax.swing.JButton cariPesananBtn;
    private javax.swing.JTextField cariPesananField;
    private javax.swing.JLabel copyright;
    private javax.swing.JLabel copyright1;
    private javax.swing.JLabel copyright2;
    private javax.swing.JLabel copyright3;
    private javax.swing.JLabel copyright4;
    private javax.swing.JLabel copyright5;
    private javax.swing.JLabel daftarPesananLabel;
    private javax.swing.JTextField dariTanggalField;
    private javax.swing.JLabel dariTglLabel;
    private javax.swing.JLabel dataMenuLabel;
    private javax.swing.JTable dataMenuTable;
    private javax.swing.JLabel dataUserLabel;
    private javax.swing.JTable dataUserTable;
    private javax.swing.JLabel deskripsiLabel;
    private javax.swing.JTextArea deskripsiMenuTextArea;
    private javax.swing.JButton editMenuBtn;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JButton hapusMenuBtn;
    private javax.swing.JTextField hargaPerPorsiField;
    private javax.swing.JLabel hargaPerPorsiLabel;
    private javax.swing.JLabel hasilLaporanLabel;
    private javax.swing.JLabel inputMenuLabel;
    private javax.swing.JLabel inputUserLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JComboBox<String> jenisMenuComboBox;
    private javax.swing.JLabel jenisMenuLabel;
    private javax.swing.JLabel laporanKeuanganLabel;
    private javax.swing.JButton lihatDetailPesBtn;
    private javax.swing.JTextField namaLengkapField;
    private javax.swing.JLabel namaLengkapLabel;
    private javax.swing.JTextField namaMenuField;
    private javax.swing.JLabel namaMenuLabel;
    private javax.swing.JTextField noTelpField;
    private javax.swing.JLabel noTelpLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JButton resetBtn;
    private javax.swing.JButton resetMenuBtn;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JComboBox<String> rolePenggunaComboBox;
    private javax.swing.JTextField sampaiTanggalField;
    private javax.swing.JLabel sampaiTglLabel;
    private javax.swing.JCheckBox showPassCheck;
    private javax.swing.JButton simpanBtn;
    private javax.swing.JButton simpanMenu;
    private javax.swing.JTable tabelDataKeuangan;
    private javax.swing.JTable tabelPesanan;
    private javax.swing.JButton tampilkanDataBtn;
    private javax.swing.JTextField totalPendapatanField;
    private javax.swing.JLabel totalPendapatanLabel;
    private javax.swing.JButton updateStatusbtn;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables
}
