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
                mainMenu.setVisible(true);
                mainMenu.setLocationRelativeTo(null);
                mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

    }
}