import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterAndLogin {
    public void Register(String username,String email,String password) throws SQLException{
        DbHelper dbHelper = new DbHelper();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = dbHelper.getConnection();
            String sql = "INSERT INTO dictionary.users (username, email, password) values (?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,email);
            statement.setString(3,password);
            statement.executeUpdate();
            System.out.println("Kayıt oluşturuldu");
        }catch (SQLException exception){
            dbHelper.showErrorException(exception);
        }finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }
    public void Login() throws SQLException{

    }

}
