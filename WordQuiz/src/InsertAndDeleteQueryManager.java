import java.sql.SQLException;

abstract class InsertAndDeleteQueryManager {

    abstract void insertQuery(String turkishWord, String englishWord) throws SQLException;

    abstract void deleteQuery(String englishWord) throws SQLException;
}
