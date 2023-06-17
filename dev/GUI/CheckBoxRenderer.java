package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class CheckBoxRenderer extends DefaultTableCellRenderer {
    private JCheckBox checkBox;

    public CheckBoxRenderer() {
        checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(JCheckBox.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            checkBox.setForeground(table.getSelectionForeground());
            checkBox.setBackground(table.getSelectionBackground());
        } else {
            checkBox.setForeground(table.getForeground());
            checkBox.setBackground(UIManager.getColor("Button.background"));
        }

        checkBox.setSelected(value != null && (boolean) value);
        return checkBox;
    }
}