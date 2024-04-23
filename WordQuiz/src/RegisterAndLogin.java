import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
    public boolean isLoginable(String usernameField, String passwordField, JLabel warningLabel) throws SQLException{
        DbHelper dbHelper = new DbHelper();

        try (Connection connection = dbHelper.getConnection()) {
            String sql = "SELECT username,password FROM dictionary.users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            //Verileri hash map şeklinde tutmak
            HashMap<String, String> users = new HashMap<String, String>();
            while (resultSet.next()) {
                //username ve şifreleri tanımlayalım
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                users.put(username, password);
            }

            //Parametreler ile hash map içinde eşleme
            if (users.containsKey(usernameField)) {

                //usernameField'a ait value'yi çıkarttık
                String storedPassword = users.get(usernameField);

                if (passwordField.equals(storedPassword)) {
                    return true;
                } else {
                    warningLabel.setText("Şifreniz yanlış");
                }
            } else {
                warningLabel.setText("Kullanıcı adı bulunamadı");
            }

        } catch (SQLException exception) {
            dbHelper.showErrorException(exception);
        }

        return false;
    }
}