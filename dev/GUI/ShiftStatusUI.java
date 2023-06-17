//import GUI.Generic.GenericButton;
//import GUI.Generic.GenericDatePicker;
//import GUI.Generic.GenericLabel;
//import UtilSuper.Response;
//import UtilSuper.ResponseSerializer;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableCellRenderer;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//public class ShiftStatusUI {
//    private JPanel rightPanel;
//
//    public void showShiftStatusUI() {
//        JButton showShiftStatusButton = new JButton("Show Shift Status");
//        showShiftStatusButton.addActionListener(e -> {
//            System.out.println("Button show shift status clicked");
//            GenericDatePicker dateField = GenericDatePicker.getNewGenericDatePicker();
//            String[] shiftTypes = {"morning", "evening"};
//            JComboBox<String> shiftTypesComboBox = new JComboBox<>(shiftTypes);
//            Response response = ResponseSerializer.deserializeFromJson(branchService.getAllBranches());
//            String[] branches = new String[0];
//            if (response.isError()) {
//                setErrorText(response.getErrorMessage());
//            } else {
//                branches = ((String) response.getReturnValue()).split("\n");
//            }
//            JComboBox<String> branchComboBox = new JComboBox<>(branches);
//            GenericButton btnDone = new GenericButton("Done");
//            JButton btnAssign = new JButton("Assign");
//            JButton btnAssignAll = new JButton("Assign All");
//
//            btnDone.addActionListener(e1 -> {
//                setErrorText("");
//                setFeedbackText("");
//                String date = dateField.getDate();
//                String shiftType = Objects.requireNonNull(shiftTypesComboBox.getSelectedItem()).toString();
//                String branch = Objects.requireNonNull(branchComboBox.getSelectedItem()).toString();
//
//                if (date.isEmpty() || shiftType.isEmpty() || branch.isEmpty()) {
//                    setErrorText("Please fill all fields");
//                } else {
//                    try {
//                        // Call the showShiftStatusUI function and get the shift status data
//                        Map<String, Object> shiftStatusData = shiftService.ShowShiftStatusUI(branch, date, shiftType);
//
//                        boolean isLegalShift = (boolean) shiftStatusData.get("isLegalShift");
//                        List<Map<String, Object>> positionDataList = (List<Map<String, Object>>) shiftStatusData.get("positions");
//
//                        // Create a table model with custom rendering
//                        CustomTableModel tableModel = new CustomTableModel();
//                        String[] columnNames = {"Position", "Assigned", "Required", "Submissions Not Assigned", "Employee IDs Not Assigned"};
//                        tableModel.setColumnIdentifiers(columnNames);
//
//                        for (Map<String, Object> positionData : positionDataList) {
//                            Object[] rowData = new Object[columnNames.length];
//                            rowData[0] = positionData.get("position");
//                            rowData[1] = positionData.get("assigned");
//                            rowData[2] = positionData.get("required");
//                            rowData[3] = positionData.get("submissionsNotAssigned");
//                            rowData[4] = positionData.get("unassignedSubmissions");
//                            tableModel.addRow(rowData);
//                        }
//
//                        JTable shiftStatusTable = new JTable(tableModel);
//
//                        // Set custom renderers for the columns
//                        shiftStatusTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
//                            @Override
//                            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                                cellComponent.setFont(cellComponent.getFont().deriveFont(Font.BOLD));
//                                return cellComponent;
//                            }
//                        });
//
//                        JScrollPane tableScrollPane = new JScrollPane(shiftStatusTable);
//                        tableScrollPane.setPreferredSize(new Dimension(400, 300));
//
//                        JLabel shiftStatusLabel;
//                        if (isLegalShift) {
//                            shiftStatusLabel = new GenericLabel("The shift is approved");
//                        } else {
//                            shiftStatusLabel = new GenericLabel((String) shiftStatusData.get("missingRequirements"));
//                        }
//
//                        btnAssign.addActionListener(e2 -> {
//                            int[] selectedRows = shiftStatusTable.getSelectedRows();
//                            for (int row : selectedRows) {
//                                Object[] rowData = tableModel.getRowData(row);
//                                // Perform the marking logic here
//                            }
//                        });
//
//                        btnAssignAll.addActionListener(e3 -> {
//                            int rowCount = shiftStatusTable.getRowCount();
//                            for (int row = 0; row < rowCount; row++) {
//                                Object[] rowData = tableModel.getRowData(row);
//                                // Perform the marking logic here
//                            }
//                        });
//
//                        rightPanel.removeAll();
//                        rightPanel.setLayout(new GridBagLayout());
//                        GridBagConstraints gbc = new GridBagConstraints();
//                        gbc.gridx = 0;
//                        gbc.gridy = 0;
//                        gbc.fill = GridBagConstraints.HORIZONTAL;
//                        gbc.insets = new Insets(5, 5, 5, 5);
//
//                        rightPanel.add(new GenericLabel("Please enter date:"), gbc);
//                        gbc.gridy++;
//                        rightPanel.add(dateField, gbc);
//                        gbc.gridy++;
//                        rightPanel.add(new GenericLabel("Please choose shift type:"), gbc);
//                        gbc.gridy++;
//                        rightPanel.add(shiftTypesComboBox, gbc);
//                        gbc.gridy++;
//                        rightPanel.add(new GenericLabel("Please choose branch:"), gbc);
//                        gbc.gridy++;
//                        rightPanel.add(branchComboBox, gbc);
//                        gbc.gridy++;
//                        rightPanel.add(btnDone, gbc);
//
//                        gbc.gridy++;
//                        gbc.weighty = 1.0;
//                        gbc.gridwidth = GridBagConstraints.REMAINDER;
//                        gbc.fill = GridBagConstraints.BOTH;
//                        rightPanel.add(tableScrollPane, gbc);
//
//                        gbc.gridy++;
//                        rightPanel.add(shiftStatusLabel, gbc);
//
//                        gbc.gridy++;
//                        gbc.gridwidth = 1;
//                        rightPanel.add(btnAssign, gbc);
//                        gbc.gridx++;
//                        rightPanel.add(btnAssignAll, gbc);
//
//                        rightPanel.revalidate();
//                        rightPanel.repaint();
//                    } catch (Exception ex) {
//                        // Handle the exception
//                        setErrorText("Error occurred while retrieving shift status: " + ex.getMessage());
//                    }
//                }
//            });
//
//            rightPanel.removeAll();
//            rightPanel.setLayout(new GridBagLayout());
//            GridBagConstraints gbc = new GridBagConstraints();
//            gbc.gridx = 0;
//            gbc.gridy = 0;
//            gbc.fill = GridBagConstraints.HORIZONTAL;
//            gbc.insets = new Insets(5, 5, 5, 5);
//
//            rightPanel.add(new GenericLabel(""), gbc);
//            gbc.gridy++;
//            rightPanel.add(new GenericLabel("Please enter date:"), gbc);
//            gbc.gridy++;
//            rightPanel.add(dateField, gbc);
//            gbc.gridy++;
//            rightPanel.add(new GenericLabel("Please choose shift type:"), gbc);
//            gbc.gridy++;
//            rightPanel.add(shiftTypesComboBox, gbc);
//            gbc.gridy++;
//            rightPanel.add(new GenericLabel("Please choose branch:"), gbc);
//            gbc.gridy++;
//            rightPanel.add(branchComboBox, gbc);
//            gbc.gridy++;
//            rightPanel.add(new GenericLabel(""), gbc);
//            gbc.gridy++;
//            rightPanel.add(btnDone, gbc);
//
//            rightPanel.revalidate();
//            rightPanel.repaint();
//        });
//    }
//
//    private class CustomTableModel extends DefaultTableModel {
//        @Override
//        public boolean isCellEditable(int row, int column) {
//            return false;
//        }
//
//        @Override
//        public Class<?> getColumnClass(int columnIndex) {
//            return String.class;
//        }
//
//        public Object[] getRowData(int row) {
//            int columnCount = getColumnCount();
//            Object[] rowData = new Object[columnCount];
//            for (int column = 0; column < columnCount; column++) {
//                rowData[column] = getValueAt(row, column);
//            }
//            return rowData;
//        }
//    }
//
//
//    public void setErrorText(String errorText) {
//        //the first word of the error message is always "Error: " in white color
//        //the rest of the error message is in red color
//        //i want that the whole error message will show for 5 seconds and then disappear
//        errorLabel.setText("<html><font color='white'>Error: </font><font color='red'>" + errorText + "</font></html>");
//        new Thread(() -> {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            errorLabel.setText("<html><font color='white'>Error: </font><font color='red'>" + "</font></html>");
//        }).start();
//    }
//}
