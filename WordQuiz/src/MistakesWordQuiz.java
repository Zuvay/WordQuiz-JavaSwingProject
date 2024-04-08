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
        setTitle("WordQuiz");

        getQuestion(); //Sorular gelmeye başlar.

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkAnswer(button1,english); //Cevabı kontrol eder ve getQuestion() metodunu tekrar çalıştırır.
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkAnswer(button2,english);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkAnswer(button3,english);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkAnswer(button4,english);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        backMainMenu.addActionListener(new ActionListener() { //Ana menüye dön tuşu
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
        selectQuery.selectQuesitonFromMistakes();
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

    private void checkAnswer(JButton selectedButton, String correctAnswer) throws SQLException { //Cevabın doğruluğunu kontrol eden fonksiyon
        if (selectedButton.getText().equals(correctAnswer)){
            statementLabel.setText("Doğru cevap");
            streak+=1;
            streakLabel.setText("Art arda Doğru sayısı: " + streak);
            SelectQuery selectQuery = new SelectQuery();
            selectQuery.insertQueryToCorrectTable(turkish, correctAnswer); //Doğru bilindiğinde ilgili satır doğrular listesine gider.
            selectQuery.deleteQueryFromIncorrectTable(english); //Ve mevcut tablodan çıkarılır ki tekrar gözükmesin.
            getQuestion();
        }else{
            statementLabel.setText("Yanlış cevap! Doğrusu => " + correctAnswer);
            lastStreak.setText("Son seri sayısı: " + streak);
            streak=0;
            streakLabel.setText(String.valueOf(streak));
            getQuestion(); //Yanlış bilindiğinde herhangi bir eksiltme olmaz ve sorular tekrar gelmeye başlar. Değişen tek şey anlık streak miktarı olur.
        }
    }
}
