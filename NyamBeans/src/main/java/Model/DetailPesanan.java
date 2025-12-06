/*
* Nama Program : NyamBeans
* Nama         : Vincentius Raynard Thedy, Nena Haryadi Puspanegara, Alfon Daren Witanto
* NPM          : 140810240028, 140810240034, 140810240052
* Tanggal buat : 28 November - 4 Desember 2025
* Deskripsi    : DetailPesanan.java (class model)
*/

package Model;

import Auth.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetailPesanan {
    private int idDetail;
    private int idPesanan;
    private int idMenu;
    private String namaMenu; // Helper untuk tampilan
    private int jumlahPorsi;
    private int hargaSatuan;
    private int subtotal;

    public DetailPesanan() {}

    public DetailPesanan(int idMenu, String namaMenu, int jumlahPorsi, int hargaSatuan) {
        this.idMenu = idMenu;
        this.namaMenu = namaMenu;
        this.jumlahPorsi = jumlahPorsi;
        this.hargaSatuan = hargaSatuan;
        this.subtotal = jumlahPorsi * hargaSatuan;
    }

    // Getter Setter
    public int getIdDetail() { return idDetail; }
    public void setIdDetail(int idDetail) { this.idDetail = idDetail; }

    public int getIdPesanan() { return idPesanan; }
    public void setIdPesanan(int idPesanan) { this.idPesanan = idPesanan; }

    public int getIdMenu() { return idMenu; }
    public void setIdMenu(int idMenu) { this.idMenu = idMenu; }
    
    public String getNamaMenu() { return namaMenu; }
    public void setNamaMenu(String namaMenu) { this.namaMenu = namaMenu; }

    public int getJumlahPorsi() { return jumlahPorsi; }
    public void setJumlahPorsi(int jumlahPorsi) { this.jumlahPorsi = jumlahPorsi; }

    public int getHargaSatuan() { return hargaSatuan; }
    public void setHargaSatuan(int hargaSatuan) { this.hargaSatuan = hargaSatuan; }

    public int getSubtotal() { return subtotal; }
    public void setSubtotal(int subtotal) { this.subtotal = subtotal; }
    
    // Method Baru: Ambil Detail berdasarkan ID Pesanan
    public static List<DetailPesanan> getDetailByPesananId(int idPesanan) {
        List<DetailPesanan> list = new ArrayList<>();
        String sql = "SELECT dp.*, m.nama_menu FROM detail_pesanan dp " +
                     "JOIN menu m ON dp.id_menu = m.id_menu " +
                     "WHERE dp.id_pesanan = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, idPesanan);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                DetailPesanan d = new DetailPesanan();
                d.setNamaMenu(rs.getString("nama_menu"));
                d.setHargaSatuan(rs.getInt("harga_satuan"));
                d.setJumlahPorsi(rs.getInt("jumlah_porsi"));
                d.setSubtotal(rs.getInt("subtotal"));
                list.add(d);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}