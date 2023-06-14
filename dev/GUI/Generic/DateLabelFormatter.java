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

            Date d = ((GregorianCalendar) value).getTime();
            s = Time.localDateToString(new java.sql.Date(d.getTime()).toLocalDate());
        }
        return s;
    }

}