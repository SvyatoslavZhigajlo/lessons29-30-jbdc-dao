import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ReviewDao {

    private final Connection connection;

    public ReviewDao(Connection connection) {
        this.connection = connection;
    }

    public void createTableReview() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS review (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER," +
                    "book_id INTEGER," +
                    "review VARCHAR (200)" +
                    ")");
        }
    }

    public void insert(Review review) throws SQLException {
        if (review.id != 0) {
            throw new IllegalArgumentException("ID is: " + review.id);
        }

        final String sql = "INSERT INTO review (user_id, book_id, review) VALUES (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, review.userId);
            statement.setInt(2, review.bookId);
            statement.setString(3, review.review);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    review.id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            statement.executeUpdate();
        }
    }

    public Collection<Review> getAllReviewByBook(int bookId) {
        Collection<Review> allReviewByBook = new ArrayList<>();
        String sql = "SELECT * FROM review WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet cursor = statement.executeQuery();
            statement.setInt(1, bookId);
            statement.executeUpdate();
            while (cursor.next()) {
                allReviewByBook.add(createReviewFromCursorIfPossible(cursor));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allReviewByBook;
    }

    private Review createReviewFromCursorIfPossible(ResultSet cursor) throws SQLException {
        Review review = new Review();
        review.id = cursor.getInt("id");
        review.bookId = cursor.getInt("book_id");
        review.userId = cursor.getInt("user_id");
        review.review = cursor.getString("review");
        return review;
    }
}
