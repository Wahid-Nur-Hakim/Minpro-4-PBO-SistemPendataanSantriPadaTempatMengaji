package model;

public abstract class RumahQuran {
    private String nama;
    private String nomorTelepon;

    protected RumahQuran(String nama, String nomorTelepon) {
        setNama(nama);
        setNomorTelepon(nomorTelepon);
    }

    public String getNama() {
        return nama;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNama(String nama) {
        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong");
        }
        this.nama = nama.trim();
    }

    public void setNomorTelepon(String nomorTelepon) {
        if (nomorTelepon == null || nomorTelepon.isBlank()) {
            throw new IllegalArgumentException("Nomor telepon tidak boleh kosong");
        }
        this.nomorTelepon = nomorTelepon.trim();
    }
    
    public abstract String getJenisPengguna();
}