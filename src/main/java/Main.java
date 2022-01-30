import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:sample.db";

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        try (Connection connection =
                     DriverManager.getConnection(SQLITE_CONNECTION_STRING)) {
            workingWithDatabase(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void workingWithDatabase(Connection connection) throws SQLException {
        AuthorDao authorDao = new AuthorDao(connection);
        BookDao bookDao = new BookDao(connection);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select the required actions");
            System.out.println("\t1 - Working with the \"BOOKS\"");
            System.out.println("\t2 - Working with the \"AUTHORS\"");
            System.out.println("\t3 - Working with the \"USERS\"");
            System.out.println("\t4 - Working with the \"BOOKS REVIEWS\"");
            System.out.println("\t0 - Exit");
            int selectNumb = scanner.nextInt();

            switch (selectNumb) {

                case 1:
                    System.out.println("\t1 - Create table \"BOOKS\"");
                    System.out.println("\t2 - Add book to list \"BOOKS\"");
                    System.out.println("\t3 - List all books from \"BOOKS\"");
                    System.out.println("\t4 - Delete book from \"BOOKS\"");
                    selectNumb = scanner.nextInt();
                    switch (selectNumb) {
                        case 1:
                            bookDao.createTableBooks();
                            break;
                        case 2:
                            Scanner scanBook = new Scanner(System.in);
                            Book book = new Book();
                            System.out.println("Enter book title");
                            book.title = scanBook.nextLine();
                            System.out.println("Enter author id");
                            book.authorId = scanBook.nextInt();
                            bookDao.insert(book);
                            System.out.println("Book added");
                            break;
                        case 3:
                            bookDao.getAll();
                            break;
                        case 4:
                            Scanner deleteIdBook = new Scanner(System.in);
                            System.out.println("Enter book ID");
                            int idBook = deleteIdBook.nextInt();
                            bookDao.deleteBookById(idBook);
                            break;
                    }
                    break;
                case 2:
                    System.out.println("\t1 - Create table \"AUTHORS\"");
                    System.out.println("\t2 - Add author to \"AUTHORS\"");
                    System.out.println("\t3 - Find an author by name in \"AUTHORS\"");
                    System.out.println("\t4 - Search by ID Author");
                    System.out.println("\t5 - List all authors from \"AUTHORS\"");
                    System.out.println("\t6 - Delete author by ID \"AUTHORS\"");
                    selectNumb = scanner.nextInt();
                    switch (selectNumb){

                        case 1:
                            authorDao.createTableAuthors();
                            System.out.println("Table of authors created!!!");
                            break;
                        case 2:
                            Scanner scanAuthor = new Scanner(System.in);
                            Author author = new Author();
                            System.out.println("Enter Author Name");
                            author.name = scanAuthor.nextLine();
                            System.out.println("Enter the year of birth of the author");
                            author.birthYear = scanAuthor.nextInt();
                            authorDao.insert(author);
                            System.out.println("Author added");
                            break;
                        case 3:
                            Scanner scanText = new Scanner(System.in);
                            System.out.println("Enter a search string");
                            String text = scanText.nextLine();
                            authorDao.findByName(text);
                            break;
                        case 4:
                            Scanner searchIdAuthor = new Scanner(System.in);
                            System.out.println("Enter author ID");
                            int idAuthor = searchIdAuthor.nextInt();
                            Optional<Author> author1 = authorDao.getById(idAuthor);
                            System.out.println(author1);
                            break;
                        case 5:
                            Collection <Author> allAuthors = authorDao.getAll();
                            System.out.println("List of authors" + allAuthors);
                            break;
                        case 6:
                            Scanner scanIdAuthor = new Scanner(System.in);
                            System.out.println("Enter author ID");
                            int deleteId = scanIdAuthor.nextInt();
                            authorDao.deleteAuthorById(deleteId);
                            System.out.println("Author with ID" + deleteId +"- deleted");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("\t1 - Create table \"USERS\"");
                    System.out.println("\t2 - Add user to \"USERS\"");
                    System.out.println("\t5 - List all authors from \"USERS\"");
                    System.out.println("\t6 - Delete user by ID \"USERS\"");

                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Не правильный выбор, повторите попытку");
            }
        }
    }
}
