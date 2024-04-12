import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncorrectCondition extends InsertAndDeleteQueryManager{
    @Override
    void insertQuery(String turkishWord,String englishWord) throws SQLException{ //Yanlış bilinen sorudaki cümleleri yanlışlar tablosuna ekle
        Connection connection = null;
        DbHelper helper = new DbHelper();

        PreparedStatement statement = null;
        ResultSet resultSet;

        try{
            connection = helper.getConnection();
            String sql = "INSERT INTO dictionary.incorrect_words (t_words,e_words) VALUES (?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,turkishWord);
            statement.setString(2,englishWord);
            statement.executeUpdate();
            System.out.println("Yanlışlar Tablosuna Eklendi");
        }catch (SQLException exception){
            helper.showErrorException(exception);
        }finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Override
    void deleteQuery(String englishWord) throws SQLException { //Yanlışlar kısmında soru çözerken doğru bildiğimizde ilgili cümleyi yanlışlar listesinden çıkar.
        Connection connection = null;
        DbHelper helper = new DbHelper();

        PreparedStatement statement = null;
        ResultSet resultSet;

        try {
            connection = helper.getConnection();
            String sql = "DELETE FROM dictionary.incorrect_words where e_words=?;";
            statement = connection.prepareStatement(sql);
            statement.setString(1, englishWord);
            statement.executeUpdate();
            System.out.println("Doğru bilinen kayıt ana tablodan çıkarıldı");
        } catch (SQLException exception) {
            helper.showErrorException(exception);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
