import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final String SQLITE_CONNECTION_STRING = "jdbc:sqlite:sample.db";

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        MainDao mainDao = new MainDao();
        MainLibraryRepository libraryRepository = new MainLibraryRepository();
        try (Connection connection =
                     DriverManager.getConnection(SQLITE_CONNECTION_STRING)) {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("\t1 - Запустить через DAO");
                System.out.println("\t2 - Запустить через LibraryRepository");
                int numb = scanner.nextInt();
                switch (numb) {
                    case 1:
                        mainDao.runMainDao(connection);
                        break;
                    case 2:
                        libraryRepository.mainLibraryRepository(connection);
                        break;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
