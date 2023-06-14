package GUI.Generic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class GenericTable extends JTable {

    private JLabel topicLabel;
    private JTextArea commentsArea;

    public GenericTable() {
        super();
        customizeTable();
    }

    public GenericTable(DefaultTableModel model) {
        super(model);
        customizeTable();
    }

    public GenericTable(Object[][] rowData, String[] columnNames) {
        super(rowData, columnNames);
        customizeTable();
    }

    private void customizeTable() {
        Font tableFont = new Font("Arial", Font.BOLD, 16);
        setFont(tableFont);
        //setRowHeight(20);
        //setFillsViewportHeight(true);
        //setAutoResizeMode(JTable.);

        // Customize table appearance or behavior here


    }

    public JScrollPane createScrollPane() {
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setPreferredSize(new Dimension(400, 300)); // Set the preferred size of the scroll pane
        return scrollPane;
    }

    public void setTopicLabel(JLabel topicLabel) {
        this.topicLabel = topicLabel;
    }

    public void setCommentsArea(JTextArea commentsArea) {
        this.commentsArea = commentsArea;
    }

    public JLabel getTopicLabel() {
        return topicLabel;
    }

    public JTextArea getCommentsArea() {
        return commentsArea;
    }
}
