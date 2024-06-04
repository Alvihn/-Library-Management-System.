/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LibraryMS;

/**
 *
 * @author Alvihn-PC
 */
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AddBookAction extends AbstractAction {
    private final JTable booksTable;
    private final JTextField bookIDField;
    private final JTextField titleField;
    private final JTextField authorField;
    private final JTextField publishedField;

    public AddBookAction(JTable booksTable, JTextField bookIDField, JTextField titleField, JTextField authorField, JTextField publishedField) {
        super("Add Book");
        this.booksTable = booksTable;
        this.bookIDField = bookIDField;
        this.titleField = titleField;
        this.authorField = authorField;
        this.publishedField = publishedField;
    }

      
    @Override
    public void actionPerformed(ActionEvent e) {
        String bookID = bookIDField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String published = publishedField.getText();
        

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Table1 (BookID, Title, Author, published) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, bookID);
            statement.setString(2, title);
            statement.setString(3, author);
            statement.setString(4, published);
            int rowsAffected = statement.executeUpdate();

            if (0 <= rowsAffected) {
                JOptionPane.showMessageDialog(booksTable, "Book added successfully.");
                DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
                model.addRow(new Object[]{bookID, title, author, published});
            } else {
                JOptionPane.showMessageDialog(booksTable, "Failed to add book.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(booksTable, "Error adding book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

   }
}

        
