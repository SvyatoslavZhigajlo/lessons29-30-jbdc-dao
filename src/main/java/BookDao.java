import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class BookDao {

    private final Connection connection;

    public BookDao(Connection connection) {
        this.connection = connection;
    }

    public void createTableBooks() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS books (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title VARCHAR (100)," +
                    "author_id INTEGER" +
                    ")");
        }
    }

    public void insert(Book book) throws SQLException {
        if (book.id != 0) {
            throw new IllegalArgumentException("ID is: " + book.id);
        }

        if (book.authorId ==0){
            throw new IllegalArgumentException("Author ID is not set");
        }
        final String sql = "INSERT INTO books (title, author_id) VALUES (?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.title);
            statement.setInt(2, book.authorId);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating book failed, no ID obtained.");
                }
            }
            statement.executeUpdate();
        }
    }

    public Collection<Book> getAll() throws SQLException {
        Collection<Book> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM books");
            while (cursor.next()) {
                books.add(createBooksFromCursorIfPossible(cursor));
            }
        }
        return books;
    }

    public void deleteBookById(int id ){
        if (id == 0) {
            throw new IllegalArgumentException("ID is: " + id);
        }
        final String sql = "DELETE FROM books WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Книга с ID " + "\"" +id + "\" удалена");
    }

    private Book createBooksFromCursorIfPossible(ResultSet cursor) throws SQLException {
        Book book = new Book();
        book.id = cursor.getInt("id");
        book.title = cursor.getString("title");
        book.authorId = cursor.getInt("author_id");
        return book;
    }
}
