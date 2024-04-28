import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPage extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JTextField emailField;
    private JPasswordField passwordField2;
    private JButton registerButton;
    private JLabel warningLabel;
    private JPanel registerPagePanel;
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
        }else if(usernameExist(username)){
            warningLabel.setText("This username is already taken");
        }else if (password.isEmpty()){
            warningLabel.setText("Password required");
        }else if (isPasswordNotLongEnough(password)){
            warningLabel.setText("Password needs to be at least 6 characters  long");
        }else if (email.isEmpty()){
            warningLabel.setText("Email required");
        }else if (isMailAdressFormatFalse(email)){
            warningLabel.setText("Please enter a valid email address");
        }else if (mailAdressExist(email)){
            warningLabel.setText("This mail adress is already taken");
        }else if (!password.equals(confirmPassword)) {
            warningLabel.setText("Passwords doesn't match");
        }else {
            return false;
        }
        return true;
    }
    public static boolean usernameExist(String username){
        DbHelper dbHelper = new DbHelper();
        try (Connection connection = dbHelper.getConnection()) {
            String sql = "SELECT username FROM dictionary.users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            //isimleri array list ile tutmak
            ArrayList<String> usernames = new ArrayList<>();
            while (resultSet.next()) {
                //username  tanımlamak
                String usernameFromDb = resultSet.getString("username");
                //Döngüdeki her bir veriyi HashMap'e ekle
                usernames.add(usernameFromDb);
            }

            for (String user : usernames){
                if (user.equals(username)){
                    return true;
                }
            }
        } catch (SQLException exception) {
            dbHelper.showErrorException(exception);
        }

        return false;
    }
    public static boolean mailAdressExist(String email){
        DbHelper dbHelper = new DbHelper();
        try (Connection connection = dbHelper.getConnection()) {
            String sql = "SELECT email FROM dictionary.users";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            //isimleri array list ile tutmak
            ArrayList<String> emails = new ArrayList<>();
            while (resultSet.next()) {
                //username  tanımlamak
                String emailFromDb = resultSet.getString("email");
                //Döngüdeki her bir veriyi HashMap'e ekle
                emails.add(emailFromDb);
            }

            for (String mailAdress : emails){
                if (mailAdress.equals(email)){
                    return true;
                }
            }
        } catch (SQLException exception) {
            dbHelper.showErrorException(exception);
        }

        return false;
    }
    public static boolean isMailAdressFormatFalse(String email){
        String registirationInfo = email;
        //Tüm email formatlarını yakalayabilmesi için bu yazımı internetten aldım
        Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b");
        Matcher matcher = pattern.matcher(registirationInfo);

        if (matcher.matches()){
            return false;
        }
        return true;
    }
    public static boolean isPasswordNotLongEnough(String password){
        if (password.length()<6){
            return true;
        }
        return false;
    }
}
