package model.dao;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Pengajar;

public class PengajarDAO {

    public List<Pengajar> getAll() throws SQLException {
        String sql = "SELECT id, nama, nomor_telepon, mengajar "
                + "FROM pengajar ORDER BY id";

        List<Pengajar> daftarPengajar = new ArrayList<>();

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Pengajar pengajar = new Pengajar(
                        resultSet.getString("nama"),
                        resultSet.getString("nomor_telepon"),
                        resultSet.getString("mengajar")
                );
                pengajar.setId(resultSet.getInt("id"));
                daftarPengajar.add(pengajar);
            }
        }

        return daftarPengajar;
    }

    public Pengajar getById(int id) throws SQLException {
        String sql = "SELECT id, nama, nomor_telepon, mengajar "
                + "FROM pengajar WHERE id = ?";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Pengajar pengajar = new Pengajar(
                            resultSet.getString("nama"),
                            resultSet.getString("nomor_telepon"),
                            resultSet.getString("mengajar")
                    );
                    pengajar.setId(resultSet.getInt("id"));
                    return pengajar;
                }
            }
        }

        return null;
    }

    public Pengajar getByNama(String nama) throws SQLException {
        String sql = "SELECT id, nama, nomor_telepon, mengajar "
                + "FROM pengajar WHERE LOWER(nama) = LOWER(?)";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nama);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Pengajar pengajar = new Pengajar(
                            resultSet.getString("nama"),
                            resultSet.getString("nomor_telepon"),
                            resultSet.getString("mengajar")
                    );
                    pengajar.setId(resultSet.getInt("id"));
                    return pengajar;
                }
            }
        }

        return null;
    }

    public boolean insert(Pengajar pengajar) throws SQLException {
        String sql = "INSERT INTO pengajar (nama, nomor_telepon, mengajar) "
                + "VALUES (?, ?, ?)";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, pengajar.getNama());
            statement.setString(2, pengajar.getNomorTelepon());
            statement.setString(3, pengajar.getMengajar());

            int barisTerpengaruh = statement.executeUpdate();

            if (barisTerpengaruh == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pengajar.setId(generatedKeys.getInt(1));
                }
            }

            return true;
        }
    }

    public boolean updateMengajar(Pengajar pengajar) throws SQLException {
        String sql = "UPDATE pengajar SET mengajar = ? WHERE id = ?";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, pengajar.getMengajar());
            statement.setInt(2, pengajar.getId());

            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM pengajar WHERE id = ?";

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public List<Pengajar> search(String kataKunci) throws SQLException {
        String sql = "SELECT id, nama, nomor_telepon, mengajar "
                + "FROM pengajar "
                + "WHERE LOWER(nama) LIKE LOWER(?) "
                + "OR nomor_telepon LIKE ? "
                + "OR LOWER(mengajar) LIKE LOWER(?) "
                + "ORDER BY id";

        String pola = "%" + kataKunci.trim() + "%";
        List<Pengajar> hasil = new ArrayList<>();

        try (Connection connection = Koneksi.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, pola);
            statement.setString(2, pola);
            statement.setString(3, pola);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Pengajar pengajar = new Pengajar(
                            resultSet.getString("nama"),
                            resultSet.getString("nomor_telepon"),
                            resultSet.getString("mengajar")
                    );
                    pengajar.setId(resultSet.getInt("id"));
                    hasil.add(pengajar);
                }
            }
        }

        return hasil;
    }
}