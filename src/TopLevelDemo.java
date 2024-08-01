import java.awt.*;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.swing.*;

public class TopLevelDemo {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */


    private static void createAndShowGUI() {
        int YEAR = Calendar.YEAR;
        int MONTH = Calendar.MONTH;
        int DAY_OF_WEEK = Calendar.DAY_OF_WEEK;
        int DAY_OF_MONTH = Calendar.DAY_OF_MONTH;
        int WEEK_OF_MONTH = Calendar.WEEK_OF_MONTH;
        int MONDAY = Calendar.MONDAY;
        Locale LOCALE = Locale.ENGLISH;
        TimeZone TZ = TimeZone.getTimeZone("Europe/Prague");

        // DateFormat initialization
        // Configured to extract simple date format from the Date object
        DateFormat df = DateFormat.getDateInstance(DateFormat.DATE_FIELD);

        // Calendar initialization with timezone and locale
        Calendar calendar = Calendar.getInstance(TZ, LOCALE);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        // Get date for current day
        // use to compare days in calendar to identify a day as today
        String today = df.format(calendar.getTime());

        // Creates an array of full month names
        String[] monthNames = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();

        // Get current year and month values
        int setYear  = calendar.get(YEAR);
        int setMonth = calendar.get(MONTH);

        // Rewinds the calendar to start on monday of the first week in the current month
        // May contain previous month days to fill the gaps
        calendar.set(WEEK_OF_MONTH, 1);
        calendar.set(DAY_OF_WEEK, MONDAY);

        // Creates a panel to contain the calendar
        JPanel calendarGrid = new JPanel(new GridLayout(0, 7));

        // Row here serves a simple purpose to segment a month into full weeks
        // with overlaps to other months when necessary
        boolean nextRow = true;
        while (nextRow) {
            for (int day = 0; day < 7; day++) {
                // Creates new button with name derived from the current day iteration
                JButton dayButton = new JButton(String.valueOf(calendar.get(DAY_OF_MONTH)));
                // Disables button that is not within current month
                dayButton.setEnabled(calendar.get(Calendar.MONTH) == setMonth);
                calendarGrid.add(dayButton);
                // Updates calendar to the next day
                calendar.add(DAY_OF_MONTH, 1);
            }
            // Checks if next month has been reached in the current week iteration
            if ((calendar.get(MONTH) > setMonth) || (calendar.get(YEAR) > setYear)
            ) {
                nextRow = false;
            }
        }

        // Creates a label with current month name
        // and positions it in the middle with some padding around
        JLabel monthLabel = new JLabel(monthNames[setMonth]);
        monthLabel.setPreferredSize(new Dimension(0, 40));
        monthLabel.setHorizontalAlignment(JLabel.CENTER);
        monthLabel.setVerticalAlignment(JLabel.CENTER);

        // Create and set up the window.
        JFrame frame = new JFrame("Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        // Add components to the window
        frame.getContentPane().add(monthLabel, BorderLayout.PAGE_START);
        frame.getContentPane().add(calendarGrid, BorderLayout.CENTER);

        // Lay out components and set visibility
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
