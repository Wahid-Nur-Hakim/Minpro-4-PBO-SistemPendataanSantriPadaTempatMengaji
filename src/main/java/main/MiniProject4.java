package main;

import config.Koneksi;
import controller.CrudUmmi;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import view.Menu;

public class MiniProject4 {

    public static void main(String[] args) {
        System.out.println("Menghubungkan ke database...");

        try (Connection conn = Koneksi.getConnection()) {
            System.out.println(
                    "STATUS: BERHASIL! Database siap digunakan."
            );

        } catch (SQLException e) {
            System.out.println("STATUS: GAGAL!");
            System.out.println(
                    "Program tidak dapat dijalankan karena koneksi "
                    + "database gagal."
            );
            System.out.println("Penyebab: " + e.getMessage());
            System.out.println(
                    "Periksa MySQL Laragon, URL, USER, PASS, "
                    + "nama database, dan port MySQL."
            );
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            Menu menu = new Menu(scanner);
            CrudUmmi crudUmmi = new CrudUmmi(scanner, menu);

            boolean berjalan = true;

            while (berjalan) {
                menu.tampilkanMenuUtama();

                int pilihan = menu.bacaPilihan("Pilih menu: ");

                switch (pilihan) {
                    case 1 -> crudUmmi.kelolaSantri();
                    case 2 -> crudUmmi.kelolaKelasMengaji();
                    case 3 -> crudUmmi.kelolaPengajar();
                    case 4 -> crudUmmi.cariPengguna();
                    case 5 -> {
                        menu.tampilkanPesan(
                                "Syukron Katsiron, Jazakumullah Khair"
                        );
                        berjalan = false;
                    }
                    default -> menu.tampilkanPesan(
                            "Pilihan tidak valid!"
                    );
                }
            }
        }
    }
}