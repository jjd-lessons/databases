package ru.itmo.databases.dao;

import ru.itmo.databases.C3P0Pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class NotesDao {
    public boolean create(){
        String createSql = "CREATE TABLE IF NOT EXISTS tb_notes(" +
                "id SERIAL PRIMARY KEY, " +
                "title VARCHAR(120) NOT NULL, " +
                "text TEXT NOT NULL, " +
                "created_at TIMESTAMPTZ NOT NULL, " +
                "author_id INTEGER NOT NULL, " +
                "CONSTRAINT fk_author_notes " +
                "FOREIGN KEY (author_id) " +
                "REFERENCES tb_authors (id))";

        try (Connection connection = C3P0Pool.getConnection()){
            try (Statement statement = connection.createStatement()){
                statement.executeUpdate(createSql);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
// 1. CHECK на created_at - дата в прошлом
// 2. INSERT tb_notes
// 3. SELECT tb_notes по идентификатору
// 4. SELECT tb_notes по идентификатору автора
// 5. SELECT tb_notes c LIMIT и OFFSET


