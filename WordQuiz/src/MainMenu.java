import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainMenu extends JFrame{
    private JButton wordsILearnedButton;
    private JButton wordQuizFromMistakesButton;
    private JButton startWordQuizButton;
    private JLabel welcome;
    private JButton myMistakesButton;
    private JPanel mainMenuPanel;

    public MainMenu(){
        add(mainMenuPanel);
        setSize(600, 500);
        setTitle("Ana Menü");

        startWordQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //Normal WordQuiz'i açmak için
                MainMenu.this.setVisible(false); // Ana menüyü kapat
                WordQuiz wordQuiz = null;
                try {
                    wordQuiz = new WordQuiz();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                wordQuiz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                wordQuiz.setLocationRelativeTo(null);
                wordQuiz.setVisible(true); // WordQuiz penceresini aç
            }
        });
        wordQuizFromMistakesButton.addActionListener(new ActionListener() { //Yanlışlardan WordQuiz'i açmak için
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu.this.setVisible(false);
                MistakesWordQuiz mistakesWordQuiz  = null;
                try {
                    mistakesWordQuiz = new MistakesWordQuiz();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                mistakesWordQuiz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mistakesWordQuiz.setLocationRelativeTo(null);
                mistakesWordQuiz.setVisible(true);
            }
        });
        wordsILearnedButton.addActionListener(new ActionListener() { //Öğrenilen kelimeler penceresini açmak için
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu.this.setVisible(false);
                LearnedWords learnedWords  = null;
                learnedWords = new LearnedWords();
                learnedWords.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                learnedWords.setLocationRelativeTo(null);
                learnedWords.setVisible(true);
            }
        });
        myMistakesButton.addActionListener(new ActionListener() { //Yanlış bilinen kelimeler listesini açmak için
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu.this.setVisible(false);
                MyMistakes myMistakes  = null;
                myMistakes = new MyMistakes();
                myMistakes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                myMistakes.setLocationRelativeTo(null);
                myMistakes.setVisible(true);
            }
        });
    }
}
