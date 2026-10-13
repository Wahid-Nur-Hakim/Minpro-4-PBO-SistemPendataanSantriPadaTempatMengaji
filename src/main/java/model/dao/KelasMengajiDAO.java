package model.dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.KelasMengaji;
import model.Pengajar;

public class KelasMengajiDAO {

    private final PengajarDAO pengajarDAO = new PengajarDAO();

    public List<KelasMengaji> getAll() throws SQLException {
        String sql = "SELECT k.kode_kelas, k.nama_kelas, k.kapasitas, "
                + "p.id AS pengajar_id, p.nama AS nama_pengajar, "
                + "p.nomor_telepon, p.mengajar "
                + "FROM kelas_mengaji k "
                + "JOIN pengajar p ON k.pengajar_id = p.id "
                + "ORDER BY k.kode_kelas";

        List<KelasMengaji> daftarKelas = new ArrayList<>();

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                daftarKelas.add(buatKelasDariResultSet(resultSet));
            }
        }

        return daftarKelas;
    }

    public KelasMengaji getByKode(int kodeKelas) throws SQLException {
        String sql = "SELECT k.kode_kelas, k.nama_kelas, k.kapasitas, "
                + "p.id AS pengajar_id, p.nama AS nama_pengajar, "
                + "p.nomor_telepon, p.mengajar "
                + "FROM kelas_mengaji k "
                + "JOIN pengajar p ON k.pengajar_id = p.id "
                + "WHERE k.kode_kelas = ?";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, kodeKelas);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return buatKelasDariResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public boolean insert(KelasMengaji kelas) throws SQLException {
        String sql = "INSERT INTO kelas_mengaji "
                + "(kode_kelas, nama_kelas, pengajar_id, kapasitas) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, kelas.getKodeKelas());
            statement.setString(2, kelas.getNamaKelas());
            statement.setInt(3, kelas.getPengajar().getId());
            statement.setInt(4, kelas.getKapasitas());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean update(KelasMengaji kelas) throws SQLException {
        String sql = "UPDATE kelas_mengaji "
                + "SET nama_kelas = ?, pengajar_id = ?, kapasitas = ? "
                + "WHERE kode_kelas = ?";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, kelas.getNamaKelas());
            statement.setInt(2, kelas.getPengajar().getId());
            statement.setInt(3, kelas.getKapasitas());
            statement.setInt(4, kelas.getKodeKelas());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int kodeKelas) throws SQLException {
        String sql = "DELETE FROM kelas_mengaji WHERE kode_kelas = ?";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, kodeKelas);
            return statement.executeUpdate() > 0;
        }
    }

    public int countSantri(int kodeKelas) throws SQLException {
        String sql = "SELECT COUNT(*) AS jumlah "
                + "FROM santri WHERE kode_kelas = ?";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, kodeKelas);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("jumlah");
                }
            }
        }

        return 0;
    }

    private KelasMengaji buatKelasDariResultSet(ResultSet resultSet)
            throws SQLException {

        Pengajar pengajar = new Pengajar(
                resultSet.getString("nama_pengajar"),
                resultSet.getString("nomor_telepon"),
                resultSet.getString("mengajar")
        );

        pengajar.setId(resultSet.getInt("pengajar_id"));

        return new KelasMengaji(
                resultSet.getInt("kode_kelas"),
                resultSet.getString("nama_kelas"),
                pengajar,
                resultSet.getInt("kapasitas")
        );
    }
}