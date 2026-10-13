package config;

import java.sql.Connection;
import java.sql.SQLException;

public class TestKoneksi {

    public static void main(String[] args) {
        System.out.println("Mengecek koneksi ke database...");

        try (Connection conn = Koneksi.getConnection()) {
            System.out.println(
                    "STATUS: BERHASIL! Database siap digunakan."
            );

        } catch (SQLException e) {
            System.out.println("STATUS: GAGAL!");
            System.out.println("Penyebab: " + e.getMessage());
            System.out.println( "Periksa URL, USER, PASS, nama database, " + "dan pastikan service MySQL Laragon menyala."
            );
        }
    }
}