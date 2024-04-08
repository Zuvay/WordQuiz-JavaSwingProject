import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbHelper {
    private String userName ="root";
    private String password ="***";
    private String dbUrl ="jdbc:mysql://localhost:3306/dictionary";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, userName, password);
    }

    public void showErrorException(SQLException exception){
        System.out.println("Error : " + exception.getMessage());
        System.out.println("Error Code : "+ exception.getErrorCode());
    }
}
