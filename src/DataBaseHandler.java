import java.sql.*;
import java.io.*;

public class DataBaseHandler {
    Connection connection;
    private String url;
    private String username;
    private String password;

    public DataBaseHandler(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        setConnection();
    }

    private void setConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();
            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Accounts (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "EMAIL CHAR(255) UNIQUE NOT NULL," +
                    "PASSWORD CHAR(255) NOT NULL, " +
                    "USERNAME CHAR(255) UNIQUE," +
                    "GAME_PASSWORD CHAR(255)," +
                    "GAME_QUANTITY INT DEFAULT 0," +
                    "DATE_CREATED DATETIME DEFAULT CURRENT_TIMESTAMP()" +
                ");"
            );

            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS Games (" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "TITLE CHAR(255) UNIQUE NOT NULL" +
                ");"
            );

            statement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS AccountGames(" +
                    "ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "ACCOUNT_ID INT," +
                    "GAME_ID INT," +
                    "FOREIGN KEY(ACCOUNT_ID) REFERENCES ACCOUNTS(ID)," +
                    "FOREIGN KEY(GAME_ID) REFERENCES Games(ID)" +
                ");"
            );
        } catch (Exception e) {
            System.out.println("Ошибка при подключении к базе данных:");
            e.printStackTrace();
        }
    }

    public void readEmails(String file_name) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file_name))) {
            String line;
            PreparedStatement preparedStmt;

            while ((line = reader.readLine()) != null) {
                int double_dot = line.indexOf(":");
                String username = line.substring(0, double_dot);
                String password = line.substring(double_dot + 1);

                preparedStmt = connection.prepareStatement("INSERT INTO Accounts (Email, password) VALUES (?, ?)");
                preparedStmt.setString(1, username);
                preparedStmt.setString(2, password);
                preparedStmt.execute();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
