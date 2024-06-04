/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LibraryMS;

/**
 *
 * @author Alvihn-PC
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class LibraryApplication {
    public static void main(String[] args) {
        // Create frame
        JFrame frame = new JFrame("Library Book Management Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        // Create table model with column names
        String[] columnNames = { "BookID", "Title", "Author", "Published" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable booksTable = new JTable(tableModel);

        // Load data from database
        loadBookData(tableModel);

        // Create text fields for adding a new book
        JTextField bookIDField = new JTextField(5);
        JTextField titleField = new JTextField(10);
        JTextField authorField = new JTextField(10);
        JTextField publishedField = new JTextField(5);

        // Create AddBookAction and button
        AddBookAction addBookAction = new AddBookAction(booksTable, bookIDField, titleField, authorField, publishedField);
        JButton addButton = new JButton(addBookAction);

        // Create DeleteBookAction and button
        DeleteBookAction deleteBookAction = new DeleteBookAction(booksTable);
        JButton deleteButton = new JButton(deleteBookAction);

        // Set up panel and add components
        JPanel panel = new JPanel();
        panel.add(new JScrollPane(booksTable));
        panel.add(bookIDField);
        panel.add(titleField);
        panel.add(authorField);
        panel.add(publishedField);
        panel.add(addButton);
        panel.add(deleteButton);

        // Add panel to frame
        frame.add(panel);

        // Display frame
        frame.setVisible(true);
    }

    private static void loadBookData(DefaultTableModel tableModel) {
        try (Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Table1")) {

            while (resultSet.next()) {
                String bookID = resultSet.getString("BookID");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                String published = resultSet.getString("published");
                tableModel.addRow(new Object[] { bookID, title, author, published });
            }
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage()); // logs the exception message.
            System.err.println("SQLState: " + ex.getSQLState()); // logs the SQL state, which provides more context about the error.
            System.err.println("VendorError: " + ex.getErrorCode()); // logs the vendor-specific error code, which can be useful for diagnosing specific issues related to the database.

  
            }
        } 
    }
