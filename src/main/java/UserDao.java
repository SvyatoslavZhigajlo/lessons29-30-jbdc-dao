import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class UserDao {

    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    //Создание таблицы Пользователей
    public void createTableUsers() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "user_name VARCHAR (100)," +
                    "birth_Year" +
                    ")");
        }
    }

    //добавление пользователя

    public void insert(User user) throws SQLException {
        if (user.id != 0) {
            throw new IllegalArgumentException("ID is: " + user.id);
        }

        final String sql = "INSERT INTO users (user_name,birth_Year) VALUES (?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.userName);
            statement.setInt(2,user.birthYearUser);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            statement.executeUpdate();
        }
    }

    /**
     * Отдает всех пользователей портала
     * @return коллекция всех пользователей из таблицы БД
     * @throws SQLException
     */
    public Collection<User> getAll() throws SQLException {
        Collection<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet cursor = statement.executeQuery("SELECT * FROM users");
            while (cursor.next()) {
                users.add(createUsersFromCursorIfPossible(cursor));
            }
        }
        return users;
    }

    private User createUsersFromCursorIfPossible(ResultSet cursor) throws SQLException {
        User user = new User();
        user.id = cursor.getInt("id");
        user.userName = cursor.getString("user_name");
        user.birthYearUser = cursor.getInt("birth_Year");
        return user;
    }

    //Обновление данных пользователя
    public void update(User user) throws SQLException {
        if (user.id == 0) {
            throw new IllegalArgumentException("ID is not set ");
        }
        final String sql = "UPDATE users" +
                "SET user_name = ?" +
                "WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.userName);
            statement.setInt(2, user.id);

            statement.executeUpdate();
        }
    }

    public void deleteUserById(int id ){
        if (id == 0) {
            throw new IllegalArgumentException("ID is: " + id);
        }
        final String sql = "DELETE FROM users WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User with ID " + "\"" +id + "\" deleted");
    }
}
