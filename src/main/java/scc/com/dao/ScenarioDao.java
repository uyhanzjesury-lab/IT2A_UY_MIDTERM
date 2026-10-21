
package scc.com.dao;

import scc.com.model.ScenarioEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public void createTable() throws SQLException {

        String sql = """
                CREATE TABLE IF NOT EXISTS vaccination_records (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    petname TEXT NOT NULL,
                    species TEXT NOT NULL,
                    vaccine TEXT NOT NULL,
                    fee REAL NOT NULL
                )
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        }
    }

    public boolean addRecord(ScenarioEntity record) throws SQLException {

        String sql = """
                INSERT INTO vaccination_records
                (petname, species, vaccine, fee)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, record.petname());
            ps.setString(2, record.species());
            ps.setString(3, record.vaccine());
            ps.setDouble(4, record.fee());

            return ps.executeUpdate() > 0;
        }
    }

    public List<ScenarioEntity> viewRecords() throws SQLException {

        String sql = """
                SELECT id, petname, species, vaccine, fee
                FROM vaccination_records
                ORDER BY id
                """;

        List<ScenarioEntity> records = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ScenarioEntity record = new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("petname"),
                        rs.getString("species"),
                        rs.getString("vaccine"),
                        rs.getDouble("fee")
                );

                records.add(record);
            }
        }

        return records;
    }
}
