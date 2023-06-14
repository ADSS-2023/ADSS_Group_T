package GUI.Generic;

import UtilSuper.Time;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private String datePattern = "yyyy-MM-dd";

    @Override
    public Object stringToValue(String text) throws ParseException {
        return new SimpleDateFormat(datePattern).parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        String s= "";
        if (value instanceof GregorianCalendar){
            //convert the date to a string in the format yyyy-MM-dd
            s = Time.localDateToString(((GregorianCalendar) value).toZonedDateTime().toLocalDate());
        }
        return s;
    }

}