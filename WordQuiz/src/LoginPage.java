import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton logInButton;
    private JLabel warningLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel LoginPagePanel;
    private JButton goMain;

    public LoginPage() {
        add(LoginPagePanel);
        setSize(600, 500);
        setTitle("Giriş Ekranı");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage.this.setVisible(false); // Login menüsünü kapat
                RegisterPage registerPage = null;
                registerPage = new RegisterPage();

                registerPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                registerPage.setLocationRelativeTo(null);
                registerPage.setVisible(true); // register penceresini aç
            }
        });
        goMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage.this.setVisible(false); // Login menüsünü kapat
                MainMenu mainMenu = null;
                mainMenu = new MainMenu();

                mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainMenu.setLocationRelativeTo(null);
                mainMenu.setVisible(true); // main menü penceresini aç
            }
        });
    }
}
