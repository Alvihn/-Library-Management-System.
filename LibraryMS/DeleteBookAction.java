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
import javax.swing.table.DefaultTableModel;

public class DeleteBookAction extends AbstractAction {
    private final JTable booksTable;

    public DeleteBookAction(JTable booksTable) {
        super("Delete Book");
        this.booksTable = booksTable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = booksTable.getSelectedRow();

        if (selectedRow >= 0) {
            String bookID = (String) booksTable.getValueAt(selectedRow, 0);

            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "DELETE FROM Table1 WHERE BookID = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, bookID);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(booksTable, "Book deleted successfully.");
                    DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(booksTable, "Book not found.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(booksTable, "Error deleting book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(booksTable, "No book selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

