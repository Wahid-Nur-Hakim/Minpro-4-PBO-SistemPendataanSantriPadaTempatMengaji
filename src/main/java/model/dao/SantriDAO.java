package model.dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.KelasMengaji;
import model.Pengajar;
import model.Santri;

public class SantriDAO {

    public List<Santri> getAll() throws SQLException {
        String sql = sqlDasar()
                + " ORDER BY s.id";

        List<Santri> daftarSantri = new ArrayList<>();

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                daftarSantri.add(buatSantriDariResultSet(resultSet));
            }
        }

        return daftarSantri;
    }

    public Santri getById(int id) throws SQLException {
        String sql = sqlDasar() + " WHERE s.id = ?";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return buatSantriDariResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public boolean insert(Santri santri) throws SQLException {
        String sql = "INSERT INTO santri "
                + "(nama, umur, nomor_telepon_wali, kode_kelas) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, santri.getNama());
            statement.setInt(2, santri.getUmur());
            statement.setString(3, santri.getNomorTelepon());
            statement.setInt(4, santri.getKelasMengaji().getKodeKelas());

            int barisTerpengaruh = statement.executeUpdate();

            if (barisTerpengaruh == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    santri.setId(generatedKeys.getInt(1));
                }
            }

            return true;
        }
    }

    public boolean updateKelas(int idSantri, int kodeKelasBaru)
            throws SQLException {

        String sql = "UPDATE santri SET kode_kelas = ? WHERE id = ?";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, kodeKelasBaru);
            statement.setInt(2, idSantri);

            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM santri WHERE id = ?";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public List<Santri> search(String kataKunci) throws SQLException {
        String sql = sqlDasar()
                + " WHERE LOWER(s.nama) LIKE LOWER(?) "
                + "OR s.nomor_telepon_wali LIKE ? "
                + "OR LOWER(k.nama_kelas) LIKE LOWER(?) "
                + "ORDER BY s.id";

        String pola = "%" + kataKunci.trim() + "%";
        List<Santri> hasil = new ArrayList<>();

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, pola);
            statement.setString(2, pola);
            statement.setString(3, pola);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    hasil.add(buatSantriDariResultSet(resultSet));
                }
            }
        }

        return hasil;
    }

    private String sqlDasar() {
        return "SELECT s.id, s.nama, s.umur, s.nomor_telepon_wali, "
                + "k.kode_kelas, k.nama_kelas, k.kapasitas, "
                + "p.id AS pengajar_id, p.nama AS nama_pengajar, "
                + "p.nomor_telepon, p.mengajar "
                + "FROM santri s "
                + "JOIN kelas_mengaji k ON s.kode_kelas = k.kode_kelas "
                + "JOIN pengajar p ON k.pengajar_id = p.id ";
    }

    private Santri buatSantriDariResultSet(ResultSet resultSet)
            throws SQLException {

        Pengajar pengajar = new Pengajar(
                resultSet.getString("nama_pengajar"),
                resultSet.getString("nomor_telepon"),
                resultSet.getString("mengajar")
        );

        pengajar.setId(resultSet.getInt("pengajar_id"));

        KelasMengaji kelas = new KelasMengaji(
                resultSet.getInt("kode_kelas"),
                resultSet.getString("nama_kelas"),
                pengajar,
                resultSet.getInt("kapasitas")
        );

        Santri santri = new Santri(
                resultSet.getString("nama"),
                resultSet.getInt("umur"),
                resultSet.getString("nomor_telepon_wali"),
                kelas
        );

        santri.setId(resultSet.getInt("id"));

        return santri;
    }
}