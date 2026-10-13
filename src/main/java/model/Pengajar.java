package model;

public class Pengajar extends RumahQuran implements CariData {
    private int id;
    private String mengajar;

    public Pengajar(String nama, String nomorTelepon, String mengajar) {
        super(nama, nomorTelepon);
        setMengajar(mengajar);
    }
    
    public int getId() {
    return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID pengajar tidak valid");
        }
        this.id = id;
    }

    public static String getHeader() {
        return String.format(
            "%-4s | %-20s | %-15s | %-20s",
            "No", "Nama", "No. Telepon", "Mengajar" );
    }

    public String getMengajar() {
        return mengajar;
    }

    public void setMengajar(String mengajar) {
        if (mengajar == null || mengajar.isBlank()) {
            throw new IllegalArgumentException("Mengajar tidak boleh kosong");
        }
        this.mengajar = mengajar.trim();
    }

    @Override
    public String getJenisPengguna() {
        return "Pengajar";
    }

    @Override
    public boolean cocokDengan(String kataKunci) {
        if (kataKunci == null || kataKunci.isBlank()) {
            return false;
        }
        String kata = kataKunci.toLowerCase();
        return getNama().toLowerCase().contains(kata)
                || getNomorTelepon().contains(kata)
                || getMengajar().toLowerCase().contains(kata);
    }

    @Override
    public String toString() {
        return String.format(
            "%-20s | %-15s | %-20s",
            getNama(),
            getNomorTelepon(),
            mengajar
        );
    }
}