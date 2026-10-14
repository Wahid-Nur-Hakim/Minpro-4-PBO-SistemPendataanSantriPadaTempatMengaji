package orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pengajar")
public class PengajarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nama", nullable = false, length = 100)
    private String nama;

    @Column(name = "nomor_telepon", nullable = false, length = 15)
    private String nomorTelepon;

    @Column(name = "mengajar", nullable = false, length = 100)
    private String mengajar;

    protected PengajarEntity() {
    }

    public PengajarEntity(
            String nama,
            String nomorTelepon,
            String mengajar) {

        setNama(nama);
        setNomorTelepon(nomorTelepon);
        setMengajar(mengajar);
    }

    public Integer getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException(
                    "Nama pengajar tidak boleh kosong.");
        }

        String nilai = nama.trim();

        if (nilai.length() > 100) {
            throw new IllegalArgumentException(
                    "Nama pengajar maksimal 100 karakter.");
        }

        this.nama = nilai;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        if (nomorTelepon == null
                || !nomorTelepon.trim().matches("\\d{10,15}")) {

            throw new IllegalArgumentException(
                    "Nomor telepon harus berupa 10-15 digit angka.");
        }

        this.nomorTelepon = nomorTelepon.trim();
    }

    public String getMengajar() {
        return mengajar;
    }

    public void setMengajar(String mengajar) {
        if (mengajar == null || mengajar.isBlank()) {
            throw new IllegalArgumentException(
                    "Kelas mengajar tidak boleh kosong.");
        }

        String nilai = mengajar.trim();

        if (nilai.length() > 100) {
            throw new IllegalArgumentException(
                    "Kelas mengajar maksimal 100 karakter.");
        }

        this.mengajar = nilai;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Nama: %s | Telepon: %s | Mengajar: %s",
                id, nama, nomorTelepon, mengajar
        );
    }
}