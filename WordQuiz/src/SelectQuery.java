import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class SelectQuery {

    private String turkish;
    private String english;
    private String englishOne;
    private String englishTwo;
    private String englishThree;
    public void selectQuesiton() throws SQLException {

        Connection connection = null;
        DbHelper dbHelper = new DbHelper();

        Statement statement;
        ResultSet resultSet;

        try {
            connection = dbHelper.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT t_words, e_words FROM dictionary_table ORDER BY RAND() LIMIT 1");
            if (resultSet.next()) {
                turkish = resultSet.getString("t_words");
                english = resultSet.getString("e_words");
            } else {
                System.out.println("ResultSet boş.");
            }

        } catch (SQLException exception) {
            dbHelper.showErrorException(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }

        }
    }
    public void selectQuesitonFromMistakes() throws SQLException {

        Connection connection = null;
        DbHelper dbHelper = new DbHelper();

        Statement statement;
        ResultSet resultSet;

        try {
            connection = dbHelper.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT t_words, e_words FROM incorrect_words ORDER BY RAND() LIMIT 1");
            if (resultSet.next()) {
                turkish = resultSet.getString("t_words");
                english = resultSet.getString("e_words");
            } else {
                System.out.println("ResultSet boş.");
            }

        } catch (SQLException exception) {
            dbHelper.showErrorException(exception);
        } finally {
            if (connection != null) {
                connection.close();
            }

        }
    }


    public void selectAndShuffleEnglishWords() throws SQLException {
        DbHelper dbHelper = new DbHelper();
        try (Connection connection = dbHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT e_words FROM dictionary_table ORDER BY RAND() LIMIT 3")) {

            ArrayList<String> englishWordsList = new ArrayList<>();
            int count = 0;
            while (resultSet.next() && count < 3) {
                englishWordsList.add(resultSet.getString("e_words"));
                count++;
            }

            if (englishWordsList.size() >= 3) {
                englishOne = englishWordsList.get(0);
                englishTwo = englishWordsList.get(1);
                englishThree = englishWordsList.get(2);
            } else {
                throw new SQLException("Yeterli sayıda İngilizce kelime bulunamadı.");
            }
        } catch (SQLException exception) {
            // SQLException'ı daha anlamlı bir şekilde işleyin veya iletişim kutuları gibi bir kullanıcı arabirimiyle kullanıcıya geri bildirim sağlayın
            dbHelper.showErrorException(exception);
        }
    }

    public void insertQueryToCorrectTable(String turkishWord,String englishWord) throws SQLException{
        Connection connection = null;
        DbHelper helper = new DbHelper();

        PreparedStatement statement = null;
        ResultSet resultSet;

        try{
            connection = helper.getConnection();
            String sql = "INSERT INTO dictionary.correct_words (t_words,e_words) VALUES (?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,turkishWord);
            statement.setString(2,englishWord);
            statement.executeUpdate();
            System.out.println("Doğrular Tablosuna Eklendi");
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

    public void insertQueryToInCorrectTable(String turkishWord,String englishWord) throws SQLException{
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


    public void deleteQuery(String englishWord) throws SQLException {
        Connection connection = null;
        DbHelper helper = new DbHelper();

        PreparedStatement statement = null;
        ResultSet resultSet;

        try {
            connection = helper.getConnection();
            String sql = "DELETE FROM dictionary.dictionary_table where e_words=?;";
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

    public void deleteQueryFromIncorrectTable(String englishWord) throws SQLException {
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



    //Getter'lar
    public String getTurkish() {
        return turkish;
    }

    public  String getEnglish() {
        return english;
    }

    public String getEnglishOne() {
        return englishOne;
    }

    public String getEnglishTwo() {
        return englishTwo;
    }

    public String getEnglishThree() {
        return englishThree;
    }
}
