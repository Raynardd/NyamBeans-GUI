/*
* Nama Program : NyamBeans
* Nama         : Vincentius Raynard Thedy, Nena Haryadi Puspanegara, Alfon Daren Witanto
* NPM          : 140810240028, 140810240034, 140810240052
* Tanggal buat : 28 November - 4 Desember 2025
* Deskripsi    : DatabaseConnection.java (class koneksi db)
*/

package Auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    
    private static Connection connection;

    public static Connection getConnection() {
        // Cek apakah koneksi belum ada atau sudah tertutup
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    // Konfigurasi Database
                    String url = "jdbc:mysql://localhost:3306/nyambeans_db";
                    String user = "root";
                    String password = "mysql";
                    
                    // 1. Memuat Driver MySQL (Penting agar error 'package does not exist' hilang saat runtime)
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    
                    // 2. Membuat Koneksi
                    connection = DriverManager.getConnection(url, user, password);
                    System.out.println("Koneksi ke Database Berhasil!");
                    
                } catch (ClassNotFoundException ex) {
                    System.err.println("Driver MySQL tidak ditemukan. Pastikan dependency sudah ditambahkan di pom.xml");
                    Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    System.err.println("Gagal terhubung ke Database. Pastikan XAMPP/MySQL sudah jalan.");
                    Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connection;
    }
}