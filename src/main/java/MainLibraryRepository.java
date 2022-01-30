import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MainLibraryRepository implements ILibraryRepository {

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final UserDao userDao;
    private final ReviewDao reviewDao;

    public MainLibraryRepository(AuthorDao authorDao, BookDao bookDao, UserDao userDao, ReviewDao reviewDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.userDao = userDao;
        this.reviewDao = reviewDao;
    }

    @Override
    public Collection<Author> getAllAuthors() {
        try {
            return authorDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Book> getAllBooks() {
        return null;
    }

    @Override
    public Collection<User> getAllUsers() {
        return null;
    }

    @Override
    public void saveAuthor(Author author) {
        try {
            if (author.id == 0) {
                authorDao.insert(author);
            } else {
                authorDao.update(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveBook(Book book, Author author) {

    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public void saveReview(Review review, Book book, User user) {

    }
}
