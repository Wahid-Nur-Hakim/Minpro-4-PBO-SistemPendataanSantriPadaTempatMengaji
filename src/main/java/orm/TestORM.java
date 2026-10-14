package orm;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestORM {

    public static void main(String[] args) {
        System.setProperty("org.jboss.logging.provider", "jdk");

        Logger loggerHibernate = Logger.getLogger("org.hibernate");
        loggerHibernate.setLevel(Level.SEVERE);

        Logger loggerUtama = Logger.getLogger("");

        for (Handler handler : loggerUtama.getHandlers()) {
            handler.setLevel(Level.SEVERE);
        } 
        
        Configuration konfigurasi = new Configuration();
        
        konfigurasi.setProperty(
                "hibernate.connection.driver_class",
                "com.mysql.cj.jdbc.Driver"
        );

        konfigurasi.setProperty(
                "hibernate.connection.url",
                "jdbc:mysql://localhost:3306/db_rq_alhafizh"
        );

        konfigurasi.setProperty(
                "hibernate.connection.username",
                "root"
        );

        konfigurasi.setProperty(
                "hibernate.connection.password",
                ""
        );

        konfigurasi.setProperty(
                "hibernate.hbm2ddl.auto",
                "validate"
        );

        konfigurasi.setProperty(
                "hibernate.show_sql",
                "false"
        );

        konfigurasi.setProperty(
                "hibernate.format_sql",
                "false"
        );

        konfigurasi.addAnnotatedClass(PengajarEntity.class);

        try (SessionFactory factory = konfigurasi.buildSessionFactory();
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Hibernate siap digunakan untuk tabel pengajar.");

            boolean berjalan = true;

            while (berjalan) {
                System.out.println("\n=== MENU UJI ORM PENGAJAR ===");
                System.out.println("1. Tambah Pengajar");
                System.out.println("2. Lihat Pengajar");
                System.out.println("3. Update Kelas Mengajar");
                System.out.println("4. Hapus Pengajar");
                System.out.println("5. Keluar");

                int pilihan = bacaAngka(scanner, "Pilih menu: ");

                switch (pilihan) {
                    case 1 -> tambah(factory, scanner);
                    case 2 -> tampilkan(factory);
                    case 3 -> update(factory, scanner);
                    case 4 -> hapus(factory, scanner);
                    case 5 -> {
                        System.out.println("Pengujian ORM selesai.");
                        berjalan = false;
                    }
                    default -> System.out.println(
                            "Pilihan menu tidak valid."
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("\nORM gagal disiapkan: " + e.getMessage());
            System.out.println("Periksa koneksi MySQL, dependency Hibernate, " + "dan kesesuaian struktur tabel pengajar.");
        }
    }

    private static void tambah(
            SessionFactory factory,
            Scanner scanner) {

        try {
            String nama = bacaTeks(scanner, "Nama Pengajar: ");
            String telepon = bacaTeks(scanner, "Nomor Telepon: ");
            String mengajar = bacaTeks(scanner, "Kelas Mengajar: ");

            PengajarEntity pengajar = new PengajarEntity(nama,telepon,mengajar);

            try (Session session = factory.openSession()) {
                Transaction transaksi = session.beginTransaction();

                try {
                    session.persist(pengajar);
                    transaksi.commit();

                    System.out.println("Pengajar berhasil disimpan melalui ORM.");
                    System.out.println("ID pengajar baru: " + pengajar.getId());

                } catch (Exception e) {
                    rollback(transaksi);
                    System.out.println("Gagal menambah pengajar: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println("Data tidak dapat diproses: " + e.getMessage());
        }
    }

    private static void tampilkan(SessionFactory factory) {
        try (Session session = factory.openSession()) {
            Transaction transaksi = session.beginTransaction();

            try {
                List<PengajarEntity> daftarPengajar =
                        session.createQuery(
                                "from PengajarEntity order by id",
                                PengajarEntity.class
                        ).getResultList();

                transaksi.commit();

                if (daftarPengajar.isEmpty()) {
                    System.out.println("Belum ada data pengajar.");
                    return;
                }

                System.out.println("\n=== DATA PENGAJAR ORM ===");

                for (PengajarEntity pengajar : daftarPengajar) {
                    System.out.println(pengajar);
                }

            } catch (Exception e) {
                rollback(transaksi);
                System.out.println("Gagal membaca data pengajar: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Gagal membuka session: " + e.getMessage());
        }
    }

    private static void update(
            SessionFactory factory,
            Scanner scanner) {

        int id = bacaAngka(
                scanner,
                "Masukkan ID pengajar yang akan diupdate: "
        );

        String mengajarBaru = bacaTeks(
                scanner,
                "Masukkan kelas mengajar baru: "
        );

        try (Session session = factory.openSession()) {
            Transaction transaksi = session.beginTransaction();

            try {
                PengajarEntity pengajar = session.find(PengajarEntity.class,id);

                if (pengajar == null) {
                    transaksi.rollback();
                    System.out.println("ID pengajar tidak ditemukan.");
                    return;
                }

                pengajar.setMengajar(mengajarBaru);
                transaksi.commit();

                System.out.println("Kelas mengajar berhasil diubah melalui ORM.");

            } catch (Exception e) {
                rollback(transaksi);
                System.out.println("Gagal mengupdate pengajar: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Gagal membuka session: " + e.getMessage());
        }
    }

    private static void hapus(
            SessionFactory factory,
            Scanner scanner) {

        int id = bacaAngka(
                scanner,
                "Masukkan ID pengajar yang akan dihapus: "
        );

        String konfirmasi = bacaTeks(
                scanner,
                "Yakin menghapus pengajar ID " + id + "? (ya/tidak): "
        );

        if (!konfirmasi.equalsIgnoreCase("ya")) {
            System.out.println("Penghapusan dibatalkan.");
            return;
        }

        try (Session session = factory.openSession()) {
            Transaction transaksi = session.beginTransaction();

            try {
                PengajarEntity pengajar = session.find(
                        PengajarEntity.class,
                        id
                );

                if (pengajar == null) {
                    transaksi.rollback();
                    System.out.println("ID pengajar tidak ditemukan.");
                    return;
                }

                session.remove(pengajar);
                transaksi.commit();

                System.out.println("Pengajar berhasil dihapus melalui ORM.");

            } catch (Exception e) {
                rollback(transaksi);

                System.out.println("Penghapusan gagal. Pengajar mungkin masih " + "digunakan oleh kelas atau terjadi error database."
                );
                System.out.println("Penyebab: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Gagal membuka session: " + e.getMessage());
        }
    }

    private static int bacaAngka(
            Scanner scanner,
            String pesan) {

        while (true) {
            System.out.print(pesan);
            String input = scanner.nextLine().trim();

            try {
                int angka = Integer.parseInt(input);

                if (angka > 0) {
                    return angka;
                }

                System.out.println("Masukkan angka lebih besar dari 0.");

            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa bilangan bulat yang valid.");
            }
        }
    }

    private static String bacaTeks(
            Scanner scanner,
            String pesan) {

        while (true) {
            System.out.print(pesan);
            String teks = scanner.nextLine().trim();

            if (!teks.isBlank()) {
                return teks;
            }

            System.out.println("Input tidak boleh kosong.");
        }
    }

    private static void rollback(Transaction transaksi) {
        try {
            if (transaksi != null && transaksi.isActive()) {
                transaksi.rollback();
            }

        } catch (Exception e) {
            System.out.println("Rollback gagal: " + e.getMessage());
        }
    }
}