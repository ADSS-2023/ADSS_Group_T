package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ButtonRenderer extends DefaultTableCellRenderer {
    private JButton button;

    public ButtonRenderer() {
        button = new JButton("Assign");
        button.setFont(button.getFont().deriveFont(Font.PLAIN));
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(UIManager.getColor("Button.background"));
        }

        if (value instanceof List) {
            List<String> employeeIds = (List<String>) value;
            button.setText(employeeIds.isEmpty() ? "Assign" : "Assigned");
        } else if (value instanceof Boolean) {
            boolean isAssigned = (Boolean) value;
            button.setText(isAssigned ? "Assigned" : "Assign");
        }

        return button;
    }

}
