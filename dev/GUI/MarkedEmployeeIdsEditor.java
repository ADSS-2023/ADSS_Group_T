package GUI;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MarkedEmployeeIdsEditor extends AbstractCellEditor implements TableCellEditor {
    private JCheckBox checkBox;
    private ArrayList<String> markedEmployeeIds;

    public MarkedEmployeeIdsEditor(JCheckBox checkBox) {
        this.checkBox = checkBox;
        this.markedEmployeeIds = new ArrayList<>();

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkBox.isSelected()) {
                    markedEmployeeIds.add(checkBox.getText());
                } else {
                    markedEmployeeIds.remove(checkBox.getText());
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        checkBox.setSelected(markedEmployeeIds.contains(checkBox.getText()));
        return checkBox;
    }

    @Override
    public Object getCellEditorValue() {
        return markedEmployeeIds;
    }
}
