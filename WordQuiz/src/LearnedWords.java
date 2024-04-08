import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LearnedWords extends JFrame {
    private JTable table1;
    private JPanel panel1;
    private JButton BackMainMenu;

    public LearnedWords() {
        setSize(800, 700);
        setTitle("Doğru Bildiğim Kelimeler");

        DbHelper dbHelper = null;
        try {
            // Verileri alma
            dbHelper = new DbHelper();
            Connection connection = dbHelper.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT t_words, e_words FROM correct_words");

            // tablonun hatlarını ayarlama
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Türkçe Kelime");
            tableModel.addColumn("İngilizce Kelime");

            // Verileri tabloya ekleme
            while (resultSet.next()) {
                String turkceKelime = resultSet.getString("t_words");
                String ingilizceKelime = resultSet.getString("e_words");
                tableModel.addRow(new Object[]{turkceKelime, ingilizceKelime});
            }

            // Tabloyu oluşturmak
            table1 = new JTable(tableModel);

            JScrollPane scrollPane = new JScrollPane(table1);

            
            panel1 = new JPanel(); //Paneli oluştur ve scrollPane'ye ekle
            panel1.add(scrollPane);
            panel1.add(BackMainMenu);
            
            add(panel1); //Paneli Frame'e ekle
            
        } catch (SQLException exception) {
            dbHelper.showErrorException(exception);
        }
        BackMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LearnedWords.this.setVisible(false);
                MainMenu mainMenu  = null;
                mainMenu = new MainMenu();
                mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Çarpı tuşuyla kapat
                mainMenu.setLocationRelativeTo(null);
                mainMenu.setVisible(true);
            }
        });
    }
}
