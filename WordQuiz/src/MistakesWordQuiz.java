import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MistakesWordQuiz extends JFrame{
    private JPanel QuizPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JLabel questionLabel;
    private JLabel statementLabel;
    private JLabel streakLabel;
    private JLabel lastStreak;
    private JButton backMainMenu;
    private String english;
    private String turkish;
    private int streak=0;
    MistakesWordQuiz() throws SQLException {
        add(QuizPanel);
        setSize(600, 500);
        setTitle("Mistakes WordQuiz");

        getQuestion(); //Soruları getiren method.

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    processAnswer(button1,english); //Cevabın doğru olup olmadığını kontrol eden method. Sonunda ilgili db işlemlerini yapar ve getQuestion'u tekrar çağırır.
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    processAnswer(button2,english);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    processAnswer(button3,english);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    processAnswer(button4,english);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        backMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MistakesWordQuiz.this.setVisible(false);
                MainMenu mainMenu  = null;
                mainMenu = new MainMenu();
                mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainMenu.setLocationRelativeTo(null);
                mainMenu.setVisible(true);
            }
        });
    }
    private void getQuestion() throws SQLException{
        SelectQuery selectQuery = new SelectQuery();
        selectQuery.selectQuesiton();
        turkish = selectQuery.getTurkish();
        english = selectQuery.getEnglish();

        //kelimeleri getir
        selectQuery.selectAndShuffleEnglishWords();
        String englishOne = selectQuery.getEnglishOne();
        String englishTwo = selectQuery.getEnglishTwo();
        String englishThree = selectQuery.getEnglishThree();

        //rastgele atama için listeler oluştur
        JButton[] buttons = {button1, button2, button3, button4};
        String[] variables = {english, englishOne, englishTwo, englishThree};

        //değişkenleri karıştır
        List<String> variableList = Arrays.asList(variables);
        Collections.shuffle(variableList);

        //atamayı yap
        questionLabel.setText(turkish);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(variables[i]);
        }
    }
    private void processAnswer(JButton selectedButton, String english) throws SQLException {
        //Seçilen buton doğru cevaba yani english değişkenine eş mi?
        if (selectedButton.getText().equals(english)){
            whenAnswerIsTrue();
        }
        else{
            whenAnswerIsFalse();
        }
    }
    private void whenAnswerIsTrue() throws SQLException{
        streak+=1; //Doğru yapılan her soru için seri 1 arttırılır. (High score mantığında tekrar db'li bir işlem yapılabilir)

        LabelManager setLabelText = new LabelManager();
        //Label'lar düzenlenir
        LabelManager.setLabelText(statementLabel, MessageManager.SUCCESS_MESSAGE.getValue());
        LabelManager.setLabelText(streakLabel, MessageManager.STREAK_MESSAGE.getValue() + streak);

        //Doğru işaretlendiğinde satırın ilgili db'e eklenmesi
        CorrectCondition insertToCorrectTable = new CorrectCondition();
        insertToCorrectTable.insertQuery(turkish, english);

        //Bu kısıma göre abstract class'ın isimlendirmesinin yanlış olduğunun farkındayım.
        IncorrectCondition deleteFromMistakesTable = new IncorrectCondition();
        deleteFromMistakesTable.deleteQuery(english); //Doğru bilinen satırın yanlışlar tablosundan çıkarılması.

        getQuestion();
    }
    private void whenAnswerIsFalse() throws SQLException{

        //Label'lar güncellenir
        LabelManager.setLabelText(statementLabel,MessageManager.FAILURE_MESSAGE.getValue() + english);
        LabelManager.setLabelText(lastStreak,(MessageManager.LAST_STREAK_MESSAGE.getValue()) +streak);

        //Yanlış bilindiğinde seri sıfırlanır.
        LabelManager.setLabelText(streakLabel,"0");

        System.out.println("İlgili kelime yanlışlar tablosunda kaldı");

        //Yeni soru çağırılır
        getQuestion();
    }
}

