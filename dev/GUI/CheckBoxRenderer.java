package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class CheckBoxRenderer extends JCheckBox implements javax.swing.table.TableCellRenderer {
    private JCheckBox checkBox;

    public CheckBoxRenderer() {
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    }

    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Boolean) {
            setSelected((Boolean) value);
        } else {
            setSelected(false); // Set the checkbox as unchecked if the value is not a boolean
        }
        return this;
    }
}




