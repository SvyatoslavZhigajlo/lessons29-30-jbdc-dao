import java.util.Collection;

public interface ILibraryRepository {

    Collection <Author> getAllAuthors();

    Collection <Book> getAllBooks();

    Collection <User> getAllUsers();

    void saveAuthor(Author author);
    void saveBook (Book book, Author author);
    void saveUser (User user);
    void saveReview (Review review, Book book, User user);


}
