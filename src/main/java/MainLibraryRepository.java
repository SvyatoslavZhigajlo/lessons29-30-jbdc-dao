import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class MainLibraryRepository implements ILibraryRepository {

    private AuthorDao authorDao;
    private BookDao bookDao;
    private UserDao userDao;
    private ReviewDao reviewDao;

    public MainLibraryRepository() {
    }

    public MainLibraryRepository(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public MainLibraryRepository(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public MainLibraryRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    public MainLibraryRepository(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    public void mainLibraryRepository(Connection connection){

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
        try {
            return bookDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        try {
            return userDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        try {
            if (author.id == 0) {
                authorDao.insert(author);
            }
            book.authorId = author.id;
            bookDao.insert(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(User user) {
        try {
            userDao.insert(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveReview(Review review, Book book, User user) {
        try {
            if(user.id ==0){
                userDao.insert(user);
            }
            review.bookId = book.id;
            review.userId = user.id;
            reviewDao.insert(review);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
