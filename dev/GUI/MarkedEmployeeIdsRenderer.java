package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class MarkedEmployeeIdsRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        List<String> markedEmployeeIds = (List<String>) value;
        StringBuilder markedIdsText = new StringBuilder();
        for (String employeeId : markedEmployeeIds) {
            markedIdsText.append(employeeId).append(", ");
        }
        if (markedIdsText.length() > 0) {
            markedIdsText.setLength(markedIdsText.length() - 2); // Remove the trailing comma and space
        }
        setText(markedIdsText.toString());
        return rendererComponent;
    }
}
