package GUI;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Custom ButtonEditor class to handle button clicks in the table
// Custom ButtonEditor class to handle button clicks in the table
public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private List<String> selectedEmployeeIds;

    public ButtonEditor(JCheckBox checkBox, JTable table, List<String> selectedEmployeeIds) {
        super(checkBox);
        button = new JButton();
        this.selectedEmployeeIds = selectedEmployeeIds;
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                int row = table.convertRowIndexToModel(table.getEditingRow());
                // Perform the logic for the button click based on the row data
                // ...
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        button.setText(value != null ? value.toString() : "");
        Object employeeId = table.getValueAt(row, 4);
        if (employeeId instanceof String) {
            button.putClientProperty("employeeId", (String) employeeId);
        }
        selectedEmployeeIds.clear();
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            // Perform any necessary actions when the button is pushed
            // ...
        }
        isPushed = false;
        return label;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
