import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginPage loginPage = null;
                loginPage = new LoginPage();
                loginPage.setVisible(true); //login sayfasını görünür yap
                loginPage.setLocationRelativeTo(null); //pencereyi tam merkezde aç
                loginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //çarpı tuşuyla uygulamayı bitir
            }
        });
    }
}
