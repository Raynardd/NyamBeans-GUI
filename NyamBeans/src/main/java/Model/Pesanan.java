/*
* Nama Program : NyamBeans
* Nama         : Vincentius Raynard Thedy, Nena Haryadi Puspanegara, Alfon Daren Witanto
* NPM          : 140810240028, 140810240034, 140810240052
* Tanggal buat : 28 November - 4 Desember 2025
* Deskripsi    : Pesanan.java (class model)
*/

package Model;

import Auth.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pesanan {
    private int idUser;
    private int idPesanan;
    private Date tglAcara;
    private String namaPemesan;
    private String lokasiAcara;
    private int totalHarga;
    private String status;
    private String catatan;      
    private String metodeBayar;  
    
    public Pesanan() {}

    public void setIdUser(int idUser) { this.idUser = idUser; }
    public int getIdPesanan() { return idPesanan; }
    public void setIdPesanan(int idPesanan) { this.idPesanan = idPesanan; }
    public Date getTglAcara() { return tglAcara; }
    public void setTglAcara(Date tglAcara) { this.tglAcara = tglAcara; }
    public String getNamaPemesan() { return namaPemesan; }
    public void setNamaPemesan(String namaPemesan) { this.namaPemesan = namaPemesan; }
    public String getLokasiAcara() { return lokasiAcara; }
    public void setLokasiAcara(String lokasiAcara) { this.lokasiAcara = lokasiAcara; }
    public int getTotalHarga() { return totalHarga; }
    public void setTotalHarga(int totalHarga) { this.totalHarga = totalHarga; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
    public String getMetodeBayar() { return metodeBayar; }
    public void setMetodeBayar(String metodeBayar) { this.metodeBayar = metodeBayar; }


    // simpan transaksi lengkap
    public boolean simpanPesanan(List<DetailPesanan> details) {
        Connection conn = null;
        PreparedStatement pstPesanan = null;
        PreparedStatement pstDetail = null;
        ResultSet rs = null;
        
        String sqlPesanan = "INSERT INTO pesanan (id_user, tgl_acara, lokasi_acara, catatan, status_pesanan, total_porsi, metode_bayar, total_harga, dp_dibayar, sisa_bayar) " +
                            "VALUES (?, ?, ?, ?, 'Menunggu DP', ?, ?, ?, ?, ?)";
        
        String sqlDetail = "INSERT INTO detail_pesanan (id_pesanan, id_menu, jumlah_porsi, harga_satuan, subtotal) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); 

            pstPesanan = conn.prepareStatement(sqlPesanan, Statement.RETURN_GENERATED_KEYS);
            pstPesanan.setInt(1, this.idUser);
            pstPesanan.setDate(2, new java.sql.Date(this.tglAcara.getTime()));
            pstPesanan.setString(3, this.lokasiAcara);
            pstPesanan.setString(4, this.catatan); 
            
            int totalPorsi = 0;
            for(DetailPesanan d : details) totalPorsi += d.getJumlahPorsi();
            
            pstPesanan.setInt(5, totalPorsi);
            pstPesanan.setString(6, this.metodeBayar);
            pstPesanan.setInt(7, this.totalHarga);
            
            int dp = this.totalHarga / 2;
            int sisa = this.totalHarga - dp;
            pstPesanan.setInt(8, dp);
            pstPesanan.setInt(9, sisa);
            
            int affected = pstPesanan.executeUpdate();
            if (affected == 0) throw new SQLException("Gagal menyimpan pesanan.");

            rs = pstPesanan.getGeneratedKeys();
            int newIdPesanan = 0;
            if (rs.next()) newIdPesanan = rs.getInt(1);

            pstDetail = conn.prepareStatement(sqlDetail);
            for (DetailPesanan detail : details) {
                pstDetail.setInt(1, newIdPesanan);
                pstDetail.setInt(2, detail.getIdMenu());
                pstDetail.setInt(3, detail.getJumlahPorsi());
                pstDetail.setInt(4, detail.getHargaSatuan());
                pstDetail.setInt(5, detail.getSubtotal());
                pstDetail.addBatch();
            }
            pstDetail.executeBatch();
            
            conn.commit(); 
            return true;

        } catch (SQLException e) {
            e.printStackTrace(); 
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            return false;
        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (SQLException ex) {}
        }
    }

    // ambil semua pesanan
    public static List<Pesanan> getAllPesanan() {
        List<Pesanan> list = new ArrayList<>();
        String sql = "SELECT p.id_pesanan, p.tgl_acara, u.nama, p.lokasi_acara, p.total_harga, p.status_pesanan " +
                     "FROM pesanan p JOIN users u ON p.id_user = u.id_user ORDER BY p.id_pesanan DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pesanan p = new Pesanan();
                p.setIdPesanan(rs.getInt("id_pesanan"));
                p.setTglAcara(rs.getDate("tgl_acara"));
                p.setNamaPemesan(rs.getString("nama"));
                p.setLokasiAcara(rs.getString("lokasi_acara"));
                p.setTotalHarga(rs.getInt("total_harga"));
                p.setStatus(rs.getString("status_pesanan"));
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // cari pesanan
    public static List<Pesanan> cariPesanan(String keyword) {
        List<Pesanan> list = new ArrayList<>();
        String sql = "SELECT p.id_pesanan, p.tgl_acara, u.nama, p.lokasi_acara, p.total_harga, p.status_pesanan " +
                     "FROM pesanan p JOIN users u ON p.id_user = u.id_user " +
                     "WHERE u.nama LIKE ? ORDER BY p.id_pesanan DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Pesanan p = new Pesanan();
                p.setIdPesanan(rs.getInt("id_pesanan"));
                p.setTglAcara(rs.getDate("tgl_acara"));
                p.setNamaPemesan(rs.getString("nama"));
                p.setLokasiAcara(rs.getString("lokasi_acara"));
                p.setTotalHarga(rs.getInt("total_harga"));
                p.setStatus(rs.getString("status_pesanan"));
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // update status pesanan
    public boolean updateStatus() {
        String sql = "UPDATE pesanan SET status_pesanan=? WHERE id_pesanan=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, this.status);
            pst.setInt(2, this.idPesanan);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
    
    // laporan keuangan
    public static List<Pesanan> getLaporan(java.util.Date dari, java.util.Date sampai) {
        List<Pesanan> list = new ArrayList<>();
        String sql = "SELECT p.id_pesanan, p.tgl_pesan, u.nama, p.status_pesanan, p.metode_bayar, p.total_harga " +
                        "FROM pesanan p JOIN users u ON p.id_user = u.id_user " +
                        "WHERE DATE(p.tgl_pesan) BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDate(1, new java.sql.Date(dari.getTime()));
            pst.setDate(2, new java.sql.Date(sampai.getTime()));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Pesanan p = new Pesanan();
                p.setIdPesanan(rs.getInt("id_pesanan"));
                p.setTglAcara(rs.getDate("tgl_pesan")); 
                p.setNamaPemesan(rs.getString("nama"));
                p.setStatus(rs.getString("status_pesanan"));
                p.setMetodeBayar(rs.getString("metode_bayar")); // Sekarang pakai setter yang benar
                p.setTotalHarga(rs.getInt("total_harga"));
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    // riwayat user
    public static List<Pesanan> getRiwayatUser(int userId) {
        List<Pesanan> list = new ArrayList<>();
        String sql = "SELECT * FROM pesanan WHERE id_user = ? ORDER BY id_pesanan DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                Pesanan p = new Pesanan();
                p.setIdPesanan(rs.getInt("id_pesanan"));
                p.setTglAcara(rs.getDate("tgl_acara"));
                p.setTotalHarga(rs.getInt("total_harga"));
                p.setStatus(rs.getString("status_pesanan"));
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    // ambil satu pesanan berdasarkan ID (buat ditampilin di JDialog)
    public static Pesanan getPesananById(int id) {
        String sql = "SELECT p.*, u.nama FROM pesanan p JOIN users u ON p.id_user = u.id_user WHERE p.id_pesanan = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Pesanan p = new Pesanan();
                p.setIdPesanan(rs.getInt("id_pesanan"));
                p.setTglAcara(rs.getDate("tgl_acara"));
                p.setNamaPemesan(rs.getString("nama"));
                p.setLokasiAcara(rs.getString("lokasi_acara"));
                p.setCatatan(rs.getString("catatan"));
                p.setStatus(rs.getString("status_pesanan"));
                p.setTotalHarga(rs.getInt("total_harga"));
                p.setMetodeBayar(rs.getString("metode_bayar")); 
                return p;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateStatusPembayaran(int dp, int sisa, String statusBaru) {
        String sql = "UPDATE pesanan SET dp_dibayar=?, sisa_bayar=?, status_pesanan=? WHERE id_pesanan=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, dp);
            pst.setInt(2, sisa);
            pst.setString(3, statusBaru);
            pst.setInt(4, this.idPesanan);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}