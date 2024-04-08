import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MyMistakes extends JFrame {
    private JTable table1;
    private JPanel panel1;
    private JButton BackMainMenu;

    public MyMistakes() {
        setSize(800, 700);
        setTitle("Yanlış Bildiğim Kelimeler");

        DbHelper dbHelper = null;
        try {
            // Verileri al
            dbHelper = new DbHelper();
            Connection connection = dbHelper.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT t_words, e_words FROM incorrect_words");

            // Tablo içeriğini oluşturmak için model
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Türkçe Kelime");
            tableModel.addColumn("İngilizce Kelime");

            // Verileri tabloya aktarma
            while (resultSet.next()) {
                String turkceKelime = resultSet.getString("t_words");
                String ingilizceKelime = resultSet.getString("e_words");
                tableModel.addRow(new Object[]{turkceKelime, ingilizceKelime});
            }

            //Tabloyu oluştur. Scrollpane'i oluştur. Tabloyu Scrollpane içine al.
            table1 = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table1);
            panel1 = new JPanel();
            
            panel1.add(scrollPane);
            panel1.add(BackMainMenu);

            // Paneli frame'e ekle
            add(panel1);
            
        } catch (SQLException exception) {
            dbHelper.showErrorException(exception);
        }
        BackMainMenu.addActionListener(new ActionListener() { //Ana menüye dönme tuşu
            @Override
            public void actionPerformed(ActionEvent e) {
                MyMistakes.this.setVisible(false);
                MainMenu mainMenu  = null;
                mainMenu = new MainMenu();
                mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainMenu.setLocationRelativeTo(null);
                mainMenu.setVisible(true);
            }
        });
    }
}
