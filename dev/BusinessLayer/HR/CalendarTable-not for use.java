package BusinessLayer.HR;

import java.text.DateFormat;
import java.text.Format;
import java.util.Calendar;

public class CalendarTable {
    private final int rows;
    private final int columns;
    private final Object[][] table;

    public CalendarTable(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.table = new Object[rows][columns];

        // Initialize the table with dates
        Calendar calendar = Calendar.getInstance();
        Format dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                table[row][col] = dateFormat.format(calendar.getTime());
                calendar.add(Calendar.DATE, 1);
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Object getValue(int row, int col) {
        return table[row][col];
    }

    public void setValue(int row, int col, Object value) {
        table[row][col] = value;
    }

    public void printByWeek() {
        Calendar calendar = Calendar.getInstance();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                calendar.setTime((DateFormat.getDateInstance(DateFormat.SHORT).parse((String) table[row][col])));
                int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
                if (col == 0) {
                    System.out.printf("%-3d", weekOfYear);
                }
                System.out.printf("%-12s", table[row][col]);
                if (col == columns - 1) {
                    System.out.println();
                }
            }
        }
    }

    public void printByMonth() {
        Calendar calendar = Calendar.getInstance();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                calendar.setTime((DateFormat.getDateInstance(DateFormat.SHORT).parse((String) table[row][col])));
                int month = calendar.get(Calendar.MONTH);
                if (col == 0) {
                    System.out.printf("%-12s", calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, calendar.getLocale()));
                }
                if (month == calendar.get(Calendar.MONTH)) {
                    System.out.printf("%-12s", table[row][col]);
                } else {
                    System.out.printf("%-12s", "");
                }
                if (col == columns - 1) {
                    System.out.println();
                }
            }
        }
    }
}

