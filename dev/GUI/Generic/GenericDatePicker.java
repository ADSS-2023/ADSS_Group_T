package GUI.Generic;
import UtilSuper.Time;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

public class GenericDatePicker extends JDatePickerImpl {
    private DateLabelFormatter dateLabelFormatter;
    private GenericDatePicker(JDatePanelImpl datePanel, JFormattedTextField.AbstractFormatter formatter) {
        super(datePanel, formatter);
        this.dateLabelFormatter = (DateLabelFormatter) formatter;
    }
public String getDate() {
    Date date = (Date) this.getModel().getValue();
    String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
    return dateStr;
    }

    public static GenericDatePicker getNewGenericDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        // Set the properties
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        return new GenericDatePicker(datePanel,new DateLabelFormatter());
    }
}
