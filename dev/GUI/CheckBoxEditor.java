package GUI;

import javax.swing.*;
import java.awt.*;

class CheckBoxEditor extends DefaultCellEditor {
    private JCheckBox checkBox;

    public CheckBoxEditor(JCheckBox checkBox) {
        super(checkBox);
        this.checkBox = checkBox;
    }

    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof Boolean) {
            checkBox.setSelected((Boolean) value);
        }
        return checkBox;
    }
}
