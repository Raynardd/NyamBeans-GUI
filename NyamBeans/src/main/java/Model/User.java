/*
* Nama Program : NyamBeans
* Nama         : Vincentius Raynard Thedy, Nena Haryadi Puspanegara, Alfon Daren Witanto
* NPM          : 140810240028, 140810240034, 140810240052
* Tanggal buat : 28 November - 4 Desember 2025
* Deskripsi    : User.java (class model)
*/

package Model;

import Auth.DatabaseConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int idUser;
    private String nama;
    private String username;
    private String password;
    private String role; // "Admin" atau "User"
    private String noTelp;
    private String email;
    private String alamat;

    public User() {}

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = hashPassword(password); }
    public void setEncryptedPassword(String password) { this.password = password; } // Untuk load dari DB

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) { sb.append(String.format("%02x", b)); }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) { return null; }
    }

    // CRUD (untuk admin)

    // CREATE: nambah user baru (versi admin)
    public boolean tambahUser() {
        // query disesuaiin dengan kolom yang ada di database
        String sql = "INSERT INTO users (nama, username, password, role, no_telp, email, alamat) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, this.nama);
            pst.setString(2, this.username);
            pst.setString(3, this.password); // Password sudah di-hash
            pst.setString(4, this.role);
            pst.setString(5, this.noTelp);
            pst.setString(6, this.email);
            pst.setString(7, this.alamat);
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ: ambil semua user
    public static List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                User u = new User();
                u.setIdUser(rs.getInt("id_user"));
                u.setNama(rs.getString("nama"));
                u.setUsername(rs.getString("username"));
                // Password tidak perlu ditampilkan
                u.setRole(rs.getString("role"));
                u.setNoTelp(rs.getString("no_telp"));
                u.setEmail(rs.getString("email"));
                u.setAlamat(rs.getString("alamat"));
                listUser.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return listUser;
    }

    // UPDATE: ubah data user
    public boolean updateUser() {
        // cek password udah diubah atau belom
        boolean isPasswordChanged = (this.password != null && !this.password.isEmpty());
        
        String sql = "UPDATE users SET nama=?, username=?, role=?, no_telp=?, email=?, alamat=?";
        if (isPasswordChanged) {
            sql += ", password=?"; // tambah update password jika ada
        }
        sql += " WHERE id_user=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, this.nama);
            pst.setString(2, this.username);
            pst.setString(3, this.role);
            pst.setString(4, this.noTelp);
            pst.setString(5, this.email);
            pst.setString(6, this.alamat);
            
            if (isPasswordChanged) {
                pst.setString(7, this.password); // Password baru (hashed)
                pst.setInt(8, this.idUser);
            } else {
                pst.setInt(7, this.idUser);
            }
            
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE: hapus user
    public boolean deleteUser() {
        String sql = "DELETE FROM users WHERE id_user=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, this.idUser);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
    
    // SEARCH: cari user
    public static List<User> cariUser(String keyword) {
        List<User> listUser = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE nama LIKE ? OR username LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, "%" + keyword + "%");
            pst.setString(2, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                User u = new User();
                u.setIdUser(rs.getInt("id_user"));
                u.setNama(rs.getString("nama"));
                u.setUsername(rs.getString("username"));
                u.setRole(rs.getString("role"));
                u.setNoTelp(rs.getString("no_telp"));
                u.setEmail(rs.getString("email"));
                u.setAlamat(rs.getString("alamat"));
                listUser.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return listUser;
    }

    // LOGIN (tetap dipertahankan untuk fitur login)
    public User login(String username, String rawPassword) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, hashPassword(rawPassword));
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getInt("id_user"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setNama(rs.getString("nama"));
                user.setNoTelp(rs.getString("no_telp"));
                user.setEmail(rs.getString("email"));
                user.setAlamat(rs.getString("alamat"));
                return user;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    
    public int registerUser() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        // query insert khusus pelanggan (Role default 'User', NoTelp & Alamat default '-')
        String sql = "INSERT INTO users (username, password, email, role, nama, no_telp, alamat) VALUES (?, ?, ?, 'User', ?, '-', '-')";
        
        PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, this.username);
        pst.setString(2, this.password); // password sudah di-hash di setter
        pst.setString(3, this.email);
        pst.setString(4, this.username); // default nama disamakan username
        
        int affectedRows = pst.executeUpdate();
        
        if (affectedRows > 0) {
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // mengembalikan ID User baru
            }
        }
        return 0; 
    }
    
    public static boolean resetPassword(String username, String email, String newPassword) {
        // hash password baru
        String hashedPassword = hashPassword(newPassword);

        // query langsung update (jika username & email cocok, password akan berubah)
        String sql = "UPDATE users SET password = ? WHERE username = ? AND email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, hashedPassword);
            pst.setString(2, username);
            pst.setString(3, email);

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0; 

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}