package ru.itmo.databases.jdbc.dao;

import ru.itmo.databases.C3P0Pool;
import ru.itmo.databases.jdbc.model.Author;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
    public boolean createTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS tb_authors (" +
                "id SERIAL PRIMARY KEY, " +
                "unique_name VARCHAR(50) NOT NULL, " +
                "registered_at DATE DEFAULT CURRENT_DATE NOT NULL, " +
                "is_active BOOLEAN DEFAULT TRUE NOT NULL)";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // import java.sql.*
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/db_name",
                "username",
                "password"
        )) {
            try (Statement statement = connection.createStatement()) {
                // executeUpdate: создание, обновление, удаление
                // таблиц или записей
                // для не SELECT запросов
                statement.executeUpdate(createSql);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(Author author) {
        String insertSql = "INSERT INTO tb_authors (unique_name, is_active) " +
                "VALUES (?, ?)";

        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setString(1, author.getUniqueName());
                ps.setBoolean(2, author.isActive());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] insert(List<Author> authors) {
        String insertSql = "INSERT INTO tb_authors (unique_name, is_active) " +
                "VALUES (?, ?)";

        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                for (Author author : authors) {
                    ps.setString(1, author.getUniqueName());
                    ps.setBoolean(2, author.isActive());
                    ps.addBatch();
                }
                return ps.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int update(Author author) {
        String updateSql = "UPDATE tb_authors SET is_active = ? " +
                "WHERE unique_name = ?";

        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
                ps.setBoolean(1, author.isActive());
                ps.setString(2, author.getUniqueName());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Author getByUniqueName(String uniqueName) {
        String selectSql = "SELECT id, unique_name, " +
                "registered_at AS registered, is_active " +
                "FROM tb_authors " +
                "WHERE unique_name = ?";
        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ps.setString(1, uniqueName);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    Author author = new Author();
                    author.setId(resultSet.getInt("id"));
                    author.setUniqueName(resultSet.getString("unique_name"));
                    author.setRegisteredAt(resultSet.getObject("registered", LocalDate.class));
                    // Boolean b = resultSet.getBoolean("is_active");
                    author.setActive(resultSet.getBoolean("is_active"));
                    return author;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Author> allAuthors() {
        String selectSql = "SELECT id, unique_name, registered_at, is_active " +
                "FROM tb_authors WHERE is_active = true";

        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ResultSet resultSet = ps.executeQuery();
                List<Author> list = new ArrayList<>();
                while (resultSet.next()) {
                    Author author = new Author();
                    author.setId(resultSet.getInt("id"));
                    author.setUniqueName(resultSet.getString("unique_name"));
                    author.setRegisteredAt(resultSet.getObject("registered", LocalDate.class));
                    author.setActive(resultSet.getBoolean("is_active"));
                    list.add(author);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Author> authorsWithoutNotes(){
        String selectSqlLeftJoin = "SELECT id, unique_name, registered_at " +
                "FROM tb_authors " +
                "LEFT JOIN tb_notes " +
                "ON tb_authors.id = tb_notes.author_id " +
                "WHERE tb_notes.author_id IS NULL";
        return null;
    }
}
