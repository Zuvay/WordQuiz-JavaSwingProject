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
/*
            BU KODLAR ÇALIŞIYOR FAKAT SONRAKİ ADIMI BECEREMEDİĞİMDEN PROJENİN BU KISMI ASKIDA KALACAK

            //Profil ile ilgili tabloların oluşturulması
            // Kullanıcının kimlik bilgisini alalım
            sql = "SELECT user_id FROM dictionary.users WHERE username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next(); // İlk satıra git
            int user_id = resultSet.getInt("user_id");

            // Kullanıcının profil tablolarını belirleme
            String createMainTableSQL = "CREATE TABLE dictionary.dictionary_table_" + user_id + " (id INT AUTO_INCREMENT, t_words VARCHAR(255), e_words VARCHAR(255), PRIMARY KEY (id), UNIQUE KEY (t_words, e_words))";
            String createCorrectTableSQL = "CREATE TABLE dictionary.correctTable_" + user_id + " (id INT AUTO_INCREMENT, t_words VARCHAR(255), e_words VARCHAR(255), count INT, PRIMARY KEY (id), FOREIGN KEY (t_words, e_words) REFERENCES dictionary.dictionary_table_" + user_id + "(t_words, e_words))";
            String createIncorrectTableSQL = "CREATE TABLE dictionary.incorrectTable_" + user_id + " (id INT AUTO_INCREMENT, t_words VARCHAR(255), e_words VARCHAR(255), count INT, PRIMARY KEY (id), FOREIGN KEY (t_words, e_words) REFERENCES dictionary.dictionary_table_" + user_id + "(t_words, e_words))";

            // Tabloların oluşturulma emri
            statement.executeUpdate(createMainTableSQL);
            statement.executeUpdate(createCorrectTableSQL);
            statement.executeUpdate(createIncorrectTableSQL);
            System.out.println("Profil tabloları oluşturuldu");

            // Yeni oluşturulan tabloya mainTable'daki verileri kopyala
            String copyDataSQL = "INSERT INTO dictionary.dictionary_table_" + user_id + " SELECT * FROM dictionary.all_words";
            statement.executeUpdate(copyDataSQL);
            System.out.println("Tüm kelimelerin bulunduğu tablodaki veriler yeni tabloya kopyalandı");

 */

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
                //username ve şifreleri tanımlamak
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                //Döngüdeki her bir veriyi HashMap'e ekle
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