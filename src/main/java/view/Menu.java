package view;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void tampilkanMenuUtama() {
        System.out.println();
        System.out.println("ASSALAMU'ALAIKUM WARAHMATULLAHI WABARAKATUH");
        System.out.println("========= RUMAH QUR'AN AL-HAFIZH ==========");
        System.out.println("1. Kelola Data Santri");
        System.out.println("2. Kelola Data Kelas Mengaji");
        System.out.println("3. Kelola Data Pengajar");
        System.out.println("4. Cari Data");
        System.out.println("5. Keluar");
    }

    public void tampilkanMenuSantri() {
        System.out.println();
        System.out.println("==== Menu Santri ====");
        System.out.println("1. Tambah Santri");
        System.out.println("2. Lihat Data Santri");
        System.out.println("3. Hapus Santri");
        System.out.println("4. Update Kelas Santri");
        System.out.println("5. Kembali ke Menu Utama");
    }

    public void tampilkanMenuKelasMengaji() {
        System.out.println();
        System.out.println("==== Menu Kelas Mengaji ====");
        System.out.println("1. Tambah Kelas Mengaji");
        System.out.println("2. Lihat Data Kelas Mengaji");
        System.out.println("3. Update Kelas Mengaji");
        System.out.println("4. Hapus Kelas Mengaji");
        System.out.println("5. Kembali ke Menu Utama");
    }

    public void tampilkanMenuPengajar() {
        System.out.println();
        System.out.println("==== Menu Pengajar ====");
        System.out.println("1. Tambah Pengajar");
        System.out.println("2. Lihat Data Pengajar");
        System.out.println("3. Update Kelas Pengajar");
        System.out.println("4. Hapus Pengajar");
        System.out.println("5. Kembali ke Menu Utama");
    }

    public int bacaPilihan(String pesan) {
        while (true) {
            System.out.print(pesan);
            if (scanner.hasNextInt()) {
                int pilihan = scanner.nextInt();
                scanner.nextLine();
                return pilihan;
            }
            System.out.println("Input harus berupa angka....");
            scanner.nextLine();
        }
    }

    public String bacaTeks(String pesan) {
        System.out.print(pesan);
        return scanner.nextLine().trim();
    }

    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }
}