import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainMenu mainMenu = null;
                mainMenu = new MainMenu();
                mainMenu.setVisible(true); //menüyü görünür yap
                mainMenu.setLocationRelativeTo(null); //menüyü tam merkezde aç
                mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //çarpı tuşuyla uygulamayı bitir
            }
        });

    }
}
