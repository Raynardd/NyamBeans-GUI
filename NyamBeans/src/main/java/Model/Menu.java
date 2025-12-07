/*
* Nama Program : NyamBeans
* Nama         : Vincentius Raynard Thedy, Nena Haryadi Puspanegara, Alfon Daren Witanto
* NPM          : 140810240028, 140810240034, 140810240052
* Tanggal buat : 28 November - 4 Desember 2025
* Deskripsi    : Menu.java (class model)
*/

package Model;

import Auth.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private int idMenu;
    private String namaMenu;
    private String jenisMenu; // Enum di DB
    private int hargaPerPorsi;
    private String deskripsi;

    public Menu() {}

    public Menu(int idMenu, String namaMenu, String jenisMenu, int hargaPerPorsi, String deskripsi) {
        this.idMenu = idMenu;
        this.namaMenu = namaMenu;
        this.jenisMenu = jenisMenu;
        this.hargaPerPorsi = hargaPerPorsi;
        this.deskripsi = deskripsi;
    }

    public int getIdMenu() { return idMenu; }
    public void setIdMenu(int idMenu) { this.idMenu = idMenu; }

    public String getNamaMenu() { return namaMenu; }
    public void setNamaMenu(String namaMenu) { this.namaMenu = namaMenu; }

    public String getJenisMenu() { return jenisMenu; }
    public void setJenisMenu(String jenisMenu) { this.jenisMenu = jenisMenu; }

    public int getHargaPerPorsi() { return hargaPerPorsi; }
    public void setHargaPerPorsi(int hargaPerPorsi) { this.hargaPerPorsi = hargaPerPorsi; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }

    // CRUD

    // CREATE: tambah menu baru
    public boolean tambahMenu() {
        String sql = "INSERT INTO menu (nama_menu, jenis_menu, harga_per_porsi, deskripsi) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, this.namaMenu);
            pst.setString(2, this.jenisMenu);
            pst.setInt(3, this.hargaPerPorsi);
            pst.setString(4, this.deskripsi);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ: ambil semua menu
    public static List<Menu> getAllMenu() {
        List<Menu> listMenu = new ArrayList<>();
        String sql = "SELECT * FROM menu";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Menu m = new Menu();
                m.setIdMenu(rs.getInt("id_menu"));
                m.setNamaMenu(rs.getString("nama_menu"));
                m.setJenisMenu(rs.getString("jenis_menu"));
                m.setHargaPerPorsi(rs.getInt("harga_per_porsi"));
                m.setDeskripsi(rs.getString("deskripsi"));
                listMenu.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listMenu;
    }

    // UPDATE: ubah data menu
    public boolean updateMenu() {
        String sql = "UPDATE menu SET nama_menu=?, jenis_menu=?, harga_per_porsi=?, deskripsi=? WHERE id_menu=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, this.namaMenu);
            pst.setString(2, this.jenisMenu);
            pst.setInt(3, this.hargaPerPorsi);
            pst.setString(4, this.deskripsi);
            pst.setInt(5, this.idMenu);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE: hapus menu
    public boolean deleteMenu() {
        String sql = "DELETE FROM menu WHERE id_menu=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setInt(1, this.idMenu);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static Menu getMenuByName(String nama) {
        String sql = "SELECT * FROM menu WHERE nama_menu = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, nama);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Menu(
                    rs.getInt("id_menu"),
                    rs.getString("nama_menu"),
                    rs.getString("jenis_menu"),
                    rs.getInt("harga_per_porsi"),
                    rs.getString("deskripsi")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}