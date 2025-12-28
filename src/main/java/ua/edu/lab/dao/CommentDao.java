package ua.edu.lab.dao;

import ua.edu.lab.model.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    private static final String URL =
            "jdbc:h2:file:./data/guest;AUTO_SERVER=TRUE";

    public CommentDao() throws SQLException {
        try (Connection c = getConnection();
             Statement s = c.createStatement()) {
            s.execute("""
                CREATE TABLE IF NOT EXISTS comments (
                    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    author VARCHAR(64) NOT NULL,
                    text VARCHAR(1000) NOT NULL
                )
            """);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public List<Comment> findAllDesc() throws SQLException {
        List<Comment> list = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps =
                     c.prepareStatement("SELECT * FROM comments ORDER BY id DESC");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Comment(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("text")
                ));
            }
        }
        return list;
    }

    public long insert(String author, String text) throws SQLException {
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(
                     "INSERT INTO comments(author, text) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, author);
            ps.setString(2, text);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }
}
