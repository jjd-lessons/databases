package ru.itmo.databases.jdbc.dao;

import ru.itmo.databases.C3P0Pool;
import ru.itmo.databases.jdbc.model.Author;
import ru.itmo.databases.jdbc.model.Note;

import java.sql.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;


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

    public Note getNoteById(int id){
// tb_authors:
// id unique_name registered_at is_active
// tb_notes:
// id title text created_at author_id
        String selectSql = "SELECT " +
                "tb_authors.id AS id_author, tb_authors.unique_name, tb_authors.registered_at, tb_authors.is_active, " +
                "tb_notes.id AS id_note, tb_notes.title, tb_notes.text, tb_notes.created_at " +
                "FROM tb_authors, tb_notes " +
                "WHERE tb_authors.id = tb_notes.author_id AND " +
                "tb_notes.id = ?";
        String selectSqlInnerJoin = "SELECT " +
                "tb_authors.id AS id_author, tb_authors.unique_name, tb_authors.registered_at, tb_authors.is_active, " +
                "tb_notes.id AS id_note, tb_notes.title, tb_notes.text, tb_notes.created_at " +
                "FROM tb_authors " +
                "JOIN tb_notes " +
                "ON tb_authors.id = tb_notes.author_id " +
                "WHERE tb_notes.id = ?";
        // FROM tb_authors
        // JOIN tb_notes
        // ON tb_authors.id = tb_notes.author_id
        // JOIN tb_comments
        // ON tb_notes.id = tb_comments.note_id
        // WHERE tb_notes.id = ?


        try (Connection connection = C3P0Pool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()){
                    Author author = new Author();
                    author.setId(resultSet.getInt("id_author"));
                    author.setUniqueName(resultSet.getString("unique_name"));
                    author.setRegisteredAt(resultSet.getObject("registered_at", LocalDate.class));
                    author.setActive(resultSet.getBoolean("is_active"));
                    Note note = new Note();
                    note.setId(resultSet.getInt("id_note"));
                    note.setTitle(resultSet.getString("title"));
                    note.setText(resultSet.getString("text"));
                    note.setCreatedAt(resultSet.getObject("created_at", OffsetDateTime.class));
                    note.setAuthor(author);
                    return note;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

                                    // tb_authors.unique_name
    // получить названия заметок имя автора которых равно authorName
    public List<Note> getNotesByAuthorName(String authorName){
        String select = "SELECT tb_notes.title " +
                "FROM tb_notes " +
                "JOIN tb_authors " +
                "ON tb_authors.id = tb_notes.author_id " +
                "WHERE tb_authors.unique_name = ?"; // + authorName
        /*String select = "SELECT tb_notes.title " +
                "FROM tb_notes " +
                "JOIN tb_authors " +
                "ON tb_authors.id = tb_notes.author_id " +
                "WHERE tb_authors.unique_name IN (?,?,?)";
        String select = "SELECT tb_notes.title " +
                "FROM tb_notes " +
                "JOIN tb_authors " +
                "ON tb_authors.id = tb_notes.author_id " +
                "WHERE tb_authors.unique_name ILIKE ?)";*/

        // Tom John MiKE
        // ILIKE
        // name ILIKE %o%
        // name ILIKE o%
        // name ILIKE %o
        // name ILIKE o
        // authorName mike
        return null;
    }
}
// 1. CHECK на created_at - дата в прошлом
// 2. INSERT tb_notes
// 3. SELECT tb_notes по идентификатору
// 4. SELECT tb_notes по идентификатору автора
// 5. SELECT tb_notes c LIMIT и OFFSET


//