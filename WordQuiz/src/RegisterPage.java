import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterPage extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JTextField emailField;
    private JPasswordField passwordField2;
    private JButton registerButton;
    private JLabel warningLabel;
    private JPanel registerPagePanel;
    private JButton goMainButton;
    private JButton goLogin;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    public RegisterPage() {
        add(registerPagePanel);
        setSize(600, 500);
        setTitle("Kayıt Ekranı");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = passwordField1.getText();
                String confirmPassword = passwordField2.getText();

                if (isRegisterInformationFalse(username, email, password, confirmPassword, warningLabel)) {
                    System.out.println("Kayıt oluşturulamadı");
                }else{
                    try {
                        RegisterAndLogin register = new RegisterAndLogin();
                        register.Register(username, email, password);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        goLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterPage.this.setVisible(false); // Login menüsünü kapat
                LoginPage loginPage = null;
                loginPage = new LoginPage();

                loginPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginPage.setLocationRelativeTo(null);
                loginPage.setVisible(true); // login penceresini aç
            }
        });
    }

    public static boolean isRegisterInformationFalse(String username,String email,String password,String confirmPassword,JLabel warningLabel){
        if (username.isEmpty()) {
            warningLabel.setText("Username required");
        } else if (password.isEmpty()) {
            warningLabel.setText("Password required");
        } else if (email.isEmpty()) {
            warningLabel.setText("Email required");
        } else if (!password.equals(confirmPassword)) {
            warningLabel.setText("Passwords doesn't match");
        }else{
            return false;
        }
        return true;
    }
}
