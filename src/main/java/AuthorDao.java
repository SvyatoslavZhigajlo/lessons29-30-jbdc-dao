
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class AuthorDao {
    private final Connection connection;

    public AuthorDao(Connection connection) {
        this.connection = connection;
    }

    public void createTableAuthors() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS author (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name VARCHAR (100)," +
                    "birth_year INTEGER" +
                    ")");
        }
    }

    public Collection<Author> getAll() throws SQLException {
        Collection<Author> authors = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM author");
            while (cursor.next()) {
                authors.add(createAuthorFromCursorIfPossible(cursor));
            }
        }
        return authors;
    }

    public Optional<Author> getById(int id) throws SQLException {
        String sql = "SELECT * FROM author WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet cursor = statement.executeQuery();
            statement.setInt(1,id);
            if (cursor.next()) {
                return Optional.of(createAuthorFromCursorIfPossible(cursor));
            } else {
                return Optional.empty();
            }
        }
    }

    public Collection<Author> findByName(String text) throws SQLException {
        Collection<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM author WHERE name LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet cursor = statement.executeQuery();
            statement.setString(1, text);
            statement.executeUpdate();
            while (cursor.next()) {
                authors.add(createAuthorFromCursorIfPossible(cursor));
            }
        }
        return authors;
    }

    public void update(Author author) throws SQLException {
        if (author.id == 0) {
            throw new IllegalArgumentException("ID is not set ");
        }
        final String sql = "UPDATE author" +
                "SET name = ?, birth_year = ?" +
                "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, author.name);
            statement.setInt(2, author.birthYear);
            statement.setInt(3, author.id);

            statement.executeUpdate();
        }
    }

    public void insert(Author author) throws SQLException {
        if (author.id != 0) {
            throw new IllegalArgumentException("ID is: " + author.id);
        }
        final String sql = "INSERT INTO author (name, birth_year) VALUES (?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author.name);
            statement.setInt(2, author.birthYear);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating author failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    author.id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating author failed, no ID obtained.");
                }
            }
            statement.executeUpdate();
        }
    }

    public void deleteAuthorById(int id ){
        if (id == 0) {
            throw new IllegalArgumentException("ID is: " + id);
        }
        final String sql = "DELETE FROM author WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Author createAuthorFromCursorIfPossible(ResultSet cursor) throws SQLException {
        Author author = new Author();
        author.id = cursor.getInt("id");
        author.name = cursor.getString("name");
        author.birthYear = cursor.getInt("birth_year");
        return author;
    }


}
