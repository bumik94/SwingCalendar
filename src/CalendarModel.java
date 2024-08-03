import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static java.awt.Component.CENTER_ALIGNMENT;

public class CalendarModel {
    private static final Calendar   calendar    = Calendar.getInstance(
            TimeZone.getTimeZone("Europe/Prague"), Locale.ENGLISH);
    private static final DateFormat df          = DateFormat.getDateInstance(DateFormat.DATE_FIELD);
    private static final String[]   monthNames  = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();

    private int currentYear = getCurrentYear();
    private int currentMonth = getCurrentMonth();

    public CalendarModel() {
        // Initialize Calendar to start on Monday of the first week in the current month
        // May overlap with neighbouring months
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.WEEK_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    }

    public String getDateToday() {
        return df.format(calendar.getTime());
    }

    public int getCurrentYear() {
        return calendar.get(Calendar.YEAR);
    }

    public int getCurrentMonth() {
        return calendar.get(Calendar.MONTH);
    }

    public int getCurrentDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public JPanel getCalendarGrid() {
        JPanel grid = new JPanel(new GridLayout(0, 7));
        // Row here serves a simple purpose to segment a month into full weeks
        // with overlaps to other months when necessary
        boolean nextRow = true;
        while (nextRow) {
            for (int day = 0; day < 7; day++) {
                // Creates new button with name derived from the current day iteration
                JButton dayButton = new JButton(String.valueOf(getCurrentDay()));
                // Disables button that is not within the current month
                dayButton.setEnabled(getCurrentMonth() == currentMonth);
                grid.add(dayButton);
                // Updates calendar to the next day
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            // Checks if next month has been reached in the current week iteration
            if (getCurrentMonth() != currentMonth) {
                nextRow = false;
            }
        }
        return grid;
    }

    private JLabel getMonthLabel() {
        JLabel monthLabel = new JLabel(monthNames[getCurrentMonth() - 1]);
        monthLabel.setPreferredSize(new Dimension(0, 25));
        monthLabel.setAlignmentX(CENTER_ALIGNMENT);
        return monthLabel;
    }
    private JLabel getYearLabel() {
        JLabel yearLabel = new JLabel(String.valueOf(getCurrentYear()));
        yearLabel.setPreferredSize(new Dimension(0, 25));
        yearLabel.setAlignmentX(CENTER_ALIGNMENT);
        return yearLabel;
    }

    public JPanel getCalendarHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(Box.createVerticalGlue());
        header.add(getYearLabel());
        header.add(new JSeparator(SwingConstants.HORIZONTAL));
        header.add(getMonthLabel());
        return header;
    }
}
