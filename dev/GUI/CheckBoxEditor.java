package GUI;

import javax.swing.*;
import java.awt.*;

class CheckBoxEditor extends DefaultCellEditor {
    private JCheckBox checkBox;

    public CheckBoxEditor(JCheckBox checkBox) {
        super(checkBox);
        this.checkBox = checkBox;
        setClickCountToStart(1);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        checkBox.setSelected(value != null && (boolean) value);
        return checkBox;
    }
}
