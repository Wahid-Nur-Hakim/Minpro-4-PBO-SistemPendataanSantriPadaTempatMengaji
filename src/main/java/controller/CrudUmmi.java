package controller;

import model.dao.KelasMengajiDAO;
import model.dao.PengajarDAO;
import model.dao.SantriDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import model.KelasMengaji;
import model.Santri;
import model.Pengajar;
import view.Menu;

public class CrudUmmi {
    private final Menu menu;
    private final Scanner scanner;
    private final SantriDAO santriDAO;
    private final KelasMengajiDAO kelasDAO;
    private final PengajarDAO pengajarDAO;

    public CrudUmmi(Scanner scanner, Menu menu) {
        this.menu = menu;
        this.scanner = scanner;
        this.santriDAO = new SantriDAO();
        this.kelasDAO = new KelasMengajiDAO();
        this.pengajarDAO = new PengajarDAO();
    }

    public void kelolaSantri() {
        boolean kembali = false;
        while (!kembali) {
            menu.tampilkanMenuSantri();
            int pilihan = menu.bacaPilihan("Pilih Menu: ");

            switch (pilihan) {
                case 1 -> tambahSantri();
                case 2 -> tampilkanSantri();
                case 3 -> hapusSantri();
                case 4 -> updateKelasSantri();
                case 5 -> kembali = true;
                default -> menu.tampilkanPesan("Pilihan tidak valid!!!!!!!!!!!");
            }
        }
    }

    public void kelolaKelasMengaji() {
        boolean kembali = false;
        while (!kembali) {
            menu.tampilkanMenuKelasMengaji();
            int pilihan = menu.bacaPilihan("Pilih Menu: ");

            switch (pilihan) {
                case 1 -> tambahKelasMengaji();
                case 2 -> tampilkanKelasMengaji();
                case 3 -> updateKelasMengaji();
                case 4 -> hapusKelasMengaji();
                case 5 -> kembali = true;
                default -> menu.tampilkanPesan("Pilihan tidak valid!!!");
            }
        }
    }

    public void kelolaPengajar() {
        boolean kembali = false;
        while (!kembali) {
            menu.tampilkanMenuPengajar();
            int pilihan = menu.bacaPilihan("Pilih menu: ");

            switch (pilihan) {
                case 1 -> tambahPengajar();
                case 2 -> tampilkanPengajar();
                case 3 -> updatePengajar();
                case 4 -> hapusPengajar();
                case 5 -> kembali = true;
                default -> menu.tampilkanPesan("Pilihan tidak valid!!");
            }
        }
    }

    public void cariPengguna() {
        String kataKunci = menu.bacaTeks(
                "Masukkan nama, nomor telepon, atau kelas: ");

        if (kataKunci.isBlank()) {
            menu.tampilkanPesan("Kata kunci pencarian tidak boleh kosong.");
            return;
        }

        try {
            List<Santri> hasilSantri = santriDAO.search(kataKunci);
            List<Pengajar> hasilPengajar = pengajarDAO.search(kataKunci);

            boolean ditemukan = false;

            System.out.println("\n=== Hasil Pencarian Santri ===");
            if (hasilSantri.isEmpty()) {
                System.out.println("Tidak ada santri yang cocok.");
            } else {
                System.out.println(Santri.getHeader());
                System.out.println("-----------------------------------------------------------------------");

                for (int i = 0; i < hasilSantri.size(); i++) {
                    System.out.printf("%-4d | %s%n", i + 1, hasilSantri.get(i));
                }

                ditemukan = true;
            }

            System.out.println("\n=== Hasil Pencarian Pengajar ===");
            if (hasilPengajar.isEmpty()) {
                System.out.println("Tidak ada pengajar yang cocok.");
            } else {
                System.out.println(Pengajar.getHeader());
                System.out.println("----------------------------------------------------------------");

                for (int i = 0; i < hasilPengajar.size(); i++) {
                    System.out.printf("%-4d | %s%n", i + 1, hasilPengajar.get(i));
                }

                ditemukan = true;
            }

            if (!ditemukan) {
                menu.tampilkanPesan("\nData tidak ditemukan.");
            }

        } catch (SQLException e) {
            menu.tampilkanPesan("Gagal melakukan pencarian: " + e.getMessage());
        }
    }

    private void tambahSantri() {
        String nama;
        int umur;
        String noTelpWali;

        while (true) {
            nama = menu.bacaTeks("\nNama Santri: ");

            if (!nama.isBlank()) {
                break;
            }

            menu.tampilkanPesan("Nama tidak boleh kosong.");
        }

        while (true) {
            umur = menu.bacaPilihan("Umur (5-17 tahun): ");

            if (umur >= 5 && umur <= 17) {
                break;
            }

            menu.tampilkanPesan("Umur santri harus antara 5 sampai 17 tahun.");
        }

        while (true) {
            noTelpWali = menu.bacaTeks("No. Telepon Wali: ");

            if (noTelpWali.matches("\\d{10,15}")) {
                break;
            }

            menu.tampilkanPesan(
                    "Nomor telepon harus berupa 10-15 digit angka.");
        }

        while (true) {
            tampilkanKelasMengaji();
            int kodeKelas = menu.bacaPilihan("Pilih kode kelas: ");

            try {
                KelasMengaji kelasDipilih = kelasDAO.getByKode(kodeKelas);

                if (kelasDipilih == null) {
                    menu.tampilkanPesan("Kode kelas tidak ditemukan.");
                    continue;
                }

                int jumlahSantri = kelasDAO.countSantri(kodeKelas);

                if (jumlahSantri >= kelasDipilih.getKapasitas()) {
                    menu.tampilkanPesan("Kelas tersebut sudah penuh.");
                    continue;
                }

                Santri santriBaru = new Santri(
                        nama, umur, noTelpWali, kelasDipilih);

                if (santriDAO.insert(santriBaru)) {
                    menu.tampilkanPesan("Data santri berhasil ditambahkan.");
                } else {
                    menu.tampilkanPesan("Data santri gagal ditambahkan.");
                }

                return;

            } catch (SQLException e) {
                menu.tampilkanPesan("Gagal menambah data santri: "
                        + e.getMessage());
                return;
            }
        }
    }

    private void tampilkanSantri() {
        try {
            List<Santri> daftarSantri = santriDAO.getAll();

            if (daftarSantri.isEmpty()) {
                menu.tampilkanPesan("\nBelum ada data santri.");
                return;
            }

            System.out.println("\n=== Data Santri RQ Al-Hafizh ===");
            System.out.println(Santri.getHeader());
            System.out.println("-----------------------------------------------------------------------");

            for (int i = 0; i < daftarSantri.size(); i++) {
                System.out.printf("%-4d | %s%n", i + 1, daftarSantri.get(i));
            }

        } catch (SQLException e) {
            menu.tampilkanPesan("Gagal mengambil data santri dari database: "
                    + e.getMessage());
        }
    }

    private void hapusSantri() {
        try {
            List<Santri> daftarSantri = santriDAO.getAll();

            if (daftarSantri.isEmpty()) {
                menu.tampilkanPesan("\nBelum ada data santri.");
                return;
            }

            tampilkanSantri();

            int nomor;

            while (true) {
                nomor = menu.bacaPilihan(
                        "Masukkan nomor santri yang akan dihapus: ");

                if (nomor >= 1 && nomor <= daftarSantri.size()) {
                    break;
                }

                menu.tampilkanPesan("Nomor santri tidak valid.");
            }

            Santri santriDipilih = daftarSantri.get(nomor - 1);

            if (santriDAO.delete(santriDipilih.getId())) {
                menu.tampilkanPesan("Data santri berhasil dihapus.");
            } else {
                menu.tampilkanPesan("Data santri gagal dihapus.");
            }

        } catch (SQLException e) {
            menu.tampilkanPesan("Gagal menghapus data santri: "
                    + e.getMessage());
        }
    }

    private void updateKelasSantri() {
        try {
            List<Santri> daftarSantri = santriDAO.getAll();

            if (daftarSantri.isEmpty()) {
                menu.tampilkanPesan("\nBelum ada data santri.");
                return;
            }

            tampilkanSantri();

            int nomorSantri;

            while (true) {
                nomorSantri = menu.bacaPilihan("Masukkan nomor santri: ");

                if (nomorSantri >= 1 && nomorSantri <= daftarSantri.size()) {
                    break;
                }

                menu.tampilkanPesan("Nomor santri tidak valid.");
            }

            Santri santriDipilih = daftarSantri.get(nomorSantri - 1);

            while (true) {
                tampilkanKelasMengaji();

                int kodeKelasBaru = menu.bacaPilihan(
                        "Pilih kode kelas baru: ");

                KelasMengaji kelasBaru = kelasDAO.getByKode(kodeKelasBaru);

                if (kelasBaru == null) {
                    menu.tampilkanPesan("Kode kelas tidak ditemukan.");
                    continue;
                }

                if (santriDipilih.getKelasMengaji().getKodeKelas()
                        == kodeKelasBaru) {

                    menu.tampilkanPesan(
                            "Santri sudah berada di kelas tersebut.");
                    return;
                }

                int jumlahSantri = kelasDAO.countSantri(kodeKelasBaru);

                if (jumlahSantri >= kelasBaru.getKapasitas()) {
                    menu.tampilkanPesan("Kelas baru tersebut sudah penuh.");
                    continue;
                }

                if (santriDAO.updateKelas(
                        santriDipilih.getId(), kodeKelasBaru)) {

                    menu.tampilkanPesan("Kelas santri berhasil diupdate.");
                } else {
                    menu.tampilkanPesan("Kelas santri gagal diupdate.");
                }

                return;
            }

        } catch (SQLException e) {
            menu.tampilkanPesan("Gagal mengupdate kelas santri: "
                    + e.getMessage());
        }
    }

    private void tambahKelasMengaji() {
        String namaKelas;
        int kapasitas;

        while (true) {
            namaKelas = menu.bacaTeks("\nNama Kelas Mengaji: ");

            if (!namaKelas.isBlank()) {
                break;
            }

            menu.tampilkanPesan("Nama kelas tidak boleh kosong.");
        }

        while (true) {
            kapasitas = menu.bacaPilihan("Kapasitas (1-30): ");

            if (kapasitas >= 1 && kapasitas <= 30) {
                break;
            }

            menu.tampilkanPesan("Kapasitas kelas harus antara 1 sampai 30.");
        }

        try {
            Pengajar pengajarDipilih = pilihPengajar();

            if (pengajarDipilih == null) {
                return;
            }

            int kodeBaru = generateKodeKelas();

            KelasMengaji kelasBaru = new KelasMengaji(
                    kodeBaru, namaKelas, pengajarDipilih, kapasitas);

            if (kelasDAO.insert(kelasBaru)) {
                menu.tampilkanPesan("Kelas mengaji berhasil ditambahkan.");
            } else {
                menu.tampilkanPesan("Kelas mengaji gagal ditambahkan.");
            }

        } catch (SQLException e) {
            menu.tampilkanPesan("Gagal menambah kelas mengaji: "
                    + e.getMessage());
        }
    }

    private void tampilkanKelasMengaji() {
        try {
            List<KelasMengaji> daftarKelas = kelasDAO.getAll();

            if (daftarKelas.isEmpty()) {
                menu.tampilkanPesan("\nBelum ada data kelas mengaji.");
                return;
            }

            System.out.println("\n=== Data Kelas Mengaji RQ Al-Hafizh ===");
            System.out.println(KelasMengaji.getHeader());
            System.out.println("--------------------------------------------------------------");

            for (KelasMengaji kelas : daftarKelas) {
                System.out.println(kelas);
            }

        } catch (SQLException e) {
            menu.tampilkanPesan("Gagal mengambil data kelas dari database: "
                    + e.getMessage());
        }
    }

    private void updateKelasMengaji() {
        try {
            List<KelasMengaji> daftarKelas = kelasDAO.getAll();

            if (daftarKelas.isEmpty()) {
                menu.tampilkanPesan("\nBelum ada data kelas.");
                return;
            }

            tampilkanKelasMengaji();

            int kodeKelas;

            while (true) {
                kodeKelas = menu.bacaPilihan(
                        "Masukkan kode kelas yang akan diupdate: ");

                KelasMengaji kelas = kelasDAO.getByKode(kodeKelas);

                if (kelas != null) {
                    break;
                }

                menu.tampilkanPesan("Kode kelas tidak ditemukan.");
            }

            KelasMengaji kelasDipilih = kelasDAO.getByKode(kodeKelas);

            String namaKelas;

            while (true) {
                namaKelas = menu.bacaTeks("Nama Kelas Baru: ");

                if (!namaKelas.isBlank()) {
                    break;
                }

                menu.tampilkanPesan("Nama kelas tidak boleh kosong.");
            }

            Pengajar pengajarBaru = pilihPengajar();

            if (pengajarBaru == null) {
                return;
            }

            int kapasitas;

            while (true) {
                kapasitas = menu.bacaPilihan("Kapasitas Baru (1-30): ");

                if (kapasitas < 1 || kapasitas > 30) {
                    menu.tampilkanPesan(
                            "Kapasitas kelas harus antara 1 sampai 30.");
                    continue;
                }

                int jumlahSantri = kelasDAO.countSantri(kodeKelas);

                if (kapasitas < jumlahSantri) {
                    menu.tampilkanPesan(
                            "Kapasitas tidak boleh lebih kecil dari "
                            + "jumlah santri saat ini.");
                    continue;
                }

                break;
            }

            kelasDipilih.setNamaKelas(namaKelas);
            kelasDipilih.setPengajar(pengajarBaru);
            kelasDipilih.setKapasitas(kapasitas);

            if (kelasDAO.update(kelasDipilih)) {
                menu.tampilkanPesan("Data kelas mengaji berhasil diupdate.");
            } else {
                menu.tampilkanPesan("Data kelas mengaji gagal diupdate.");
            }

        } catch (SQLException e) {
            menu.tampilkanPesan("Gagal mengupdate kelas mengaji: "
                    + e.getMessage());
        }
    }
    
    private void hapusKelasMengaji() {
        try {
            List<KelasMengaji> daftarKelas = kelasDAO.getAll();

            if (daftarKelas.isEmpty()) {
                menu.tampilkanPesan("\nBelum ada data kelas.");
                return;
            }

            tampilkanKelasMengaji();

            int kodeKelas;

            while (true) {
                kodeKelas = menu.bacaPilihan(
                        "Masukkan kode kelas yang akan dihapus: ");

                KelasMengaji kelas = kelasDAO.getByKode(kodeKelas);

                if (kelas != null) {
                    break;
                }

                menu.tampilkanPesan("Kode kelas tidak ditemukan.");
            }

            int jumlahSantri = kelasDAO.countSantri(kodeKelas);

            if (jumlahSantri > 0) {
                menu.tampilkanPesan(
                        "Kelas tidak dapat dihapus karena masih memiliki santri.");
                return;
            }

            if (kelasDAO.delete(kodeKelas)) {
                menu.tampilkanPesan("Kelas mengaji berhasil dihapus.");
            } else {
                menu.tampilkanPesan("Kelas mengaji gagal dihapus.");
            }

        } catch (SQLException e) {
            menu.tampilkanPesan("Gagal menghapus kelas mengaji: "
                    + e.getMessage());
        }
    }
    private void tambahPengajar() {
        String nama;
        String nomorTelepon;
        String mengajar;

        while (true) {
            nama = menu.bacaTeks("Nama Pengajar: ");

            if (!nama.isBlank()) {
                break;
            }

            menu.tampilkanPesan("Nama pengajar tidak boleh kosong.");
        }

        while (true) {
            nomorTelepon = menu.bacaTeks("Nomor Telepon: ");

            if (nomorTelepon.matches("\\d{10,15}")) {
                break;
            }

            menu.tampilkanPesan(
                    "Nomor telepon harus 10-15 digit angka.");
        }

        while (true) {
            mengajar = menu.bacaTeks("Kelas Mengajar: ");

            if (!mengajar.isBlank()) {
                break;
            }

            menu.tampilkanPesan("Kelas mengajar tidak boleh kosong.");
        }

        try {
            Pengajar pengajarBaru = new Pengajar(
                    nama, nomorTelepon, mengajar);

            if (pengajarDAO.insert(pengajarBaru)) {
                menu.tampilkanPesan("Data pengajar berhasil ditambahkan.");
            } else {
                menu.tampilkanPesan("Data pengajar gagal ditambahkan.");
            }

        } catch (SQLException e) {
            menu.tampilkanPesan("Gagal menambah data pengajar: "
                    + e.getMessage());
        }
    }

    private void tampilkanPengajar() {
        try {
            List<Pengajar> daftarPengajar = pengajarDAO.getAll();

            if (daftarPengajar.isEmpty()) {
                menu.tampilkanPesan("Belum ada data pengajar.");
                return;
            }

            System.out.println("\n=== Data Pengajar RQ Al-Hafizh ===");
            System.out.println(Pengajar.getHeader());
            System.out.println("----------------------------------------------------------------");

            for (int i = 0; i < daftarPengajar.size(); i++) {
                System.out.printf("%-4d | %s%n", i + 1, daftarPengajar.get(i));
            }

        } catch (SQLException e) {
            menu.tampilkanPesan("Gagal mengambil data pengajar dari database: "
                    + e.getMessage());
        }
    }

    private void updatePengajar() {
        try {
            List<Pengajar> daftarPengajar = pengajarDAO.getAll();

            if (daftarPengajar.isEmpty()) {
                menu.tampilkanPesan("Belum ada data pengajar.");
                return;
            }

            tampilkanPengajar();

            int nomor;

            while (true) {
                nomor = menu.bacaPilihan(
                        "Masukkan nomor pengajar yang akan diupdate: ");

                if (nomor >= 1 && nomor <= daftarPengajar.size()) {
                    break;
                }

                menu.tampilkanPesan("Nomor pengajar tidak valid.");
            }

            String mengajarBaru;

            while (true) {
                mengajarBaru = menu.bacaTeks(
                        "Masukkan kelas mengajar baru: ");

                if (!mengajarBaru.isBlank()) {
                    break;
                }

                menu.tampilkanPesan("Kelas mengajar tidak boleh kosong.");
            }

            Pengajar pengajarDipilih = daftarPengajar.get(nomor - 1);
            pengajarDipilih.setMengajar(mengajarBaru);

            if (pengajarDAO.updateMengajar(pengajarDipilih)) {
                menu.tampilkanPesan("Kelas mengajar berhasil diupdate.");
            } else {
                menu.tampilkanPesan("Data pengajar gagal diupdate.");
            }

        } catch (SQLException e) {
            menu.tampilkanPesan("Gagal mengupdate pengajar: "
                    + e.getMessage());
        }
    }

    private void hapusPengajar() {
        try {
            List<Pengajar> daftarPengajar = pengajarDAO.getAll();

            if (daftarPengajar.isEmpty()) {
                menu.tampilkanPesan("Belum ada data pengajar.");
                return;
            }

            tampilkanPengajar();

            int nomor;

            while (true) {
                nomor = menu.bacaPilihan(
                        "Masukkan nomor pengajar yang akan dihapus: ");

                if (nomor >= 1 && nomor <= daftarPengajar.size()) {
                    break;
                }

                menu.tampilkanPesan("Nomor pengajar tidak valid.");
            }

            Pengajar pengajarDipilih = daftarPengajar.get(nomor - 1);

            if (pengajarDAO.delete(pengajarDipilih.getId())) {
                menu.tampilkanPesan("Data pengajar berhasil dihapus.");
            } else {
                menu.tampilkanPesan("Data pengajar gagal dihapus.");
            }

        } catch (SQLException e) {
            menu.tampilkanPesan(
                    "Data pengajar tidak dapat dihapus karena masih "
                    + "digunakan oleh kelas atau terjadi kesalahan: "
                    + e.getMessage());
        }
    }
    
    private Pengajar pilihPengajar() throws SQLException {
        List<Pengajar> daftarPengajar = pengajarDAO.getAll();

        if (daftarPengajar.isEmpty()) {
            menu.tampilkanPesan(
                    "Belum ada pengajar. Tambahkan pengajar terlebih dahulu.");
            return null;
        }

        System.out.println("\n=== Pilih Pengajar ===");
        System.out.println(Pengajar.getHeader());
        System.out.println("----------------------------------------------------------------");

        for (int i = 0; i < daftarPengajar.size(); i++) {
            System.out.printf("%-4d | %s%n",
                    i + 1, daftarPengajar.get(i));
        }

        while (true) {
            int nomor = menu.bacaPilihan("Pilih nomor pengajar: ");

            if (nomor >= 1 && nomor <= daftarPengajar.size()) {
                return daftarPengajar.get(nomor - 1);
            }

            menu.tampilkanPesan("Nomor pengajar tidak valid.");
        }
    }
    
    private int generateKodeKelas() throws SQLException {
        List<KelasMengaji> daftarKelas = kelasDAO.getAll();
        int kodeTerbesar = 0;

        for (KelasMengaji kelas : daftarKelas) {
            if (kelas.getKodeKelas() > kodeTerbesar) {
                kodeTerbesar = kelas.getKodeKelas();
            }
        }

        return kodeTerbesar + 1;
    }
}