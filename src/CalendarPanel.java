import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Locale;

public class CalendarPanel extends JPanel implements ActionListener {
    Calendar calendar;

    private int currentYear;
    private int currentMonth;

    public CalendarPanel() {
        this.setLayout(new BorderLayout());

        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(java.util.Calendar.MONDAY);
        currentYear = getCurrentYear();
        currentMonth = getCurrentMonth();

        redrawPanel();
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
        calendar.set(Calendar.WEEK_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

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
                calendar.set(Calendar.MONTH, currentMonth);
            }
        }
        return grid;
    }

    public JPanel  getCalendarHeader() {
        String monthName = calendar.getDisplayName(
                Calendar.MONTH,
                Calendar.LONG_STANDALONE,
                Locale.getDefault());
        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);
        String leftArrow = "⮘";
        String rightArrow = "⮚";

        // Root panel of the header
        JPanel header = new JPanel(new BorderLayout());

        // Label containing the current year
        JLabel yearLabel = new JLabel(String.valueOf(getCurrentYear()), SwingConstants.CENTER);
        yearLabel.setPreferredSize(new Dimension(0, 25));

        // Label containing the current month
        JLabel monthLabel = new JLabel(monthName, SwingConstants.CENTER);
        monthLabel.setPreferredSize(new Dimension(0, 25));

        JButton prevYear = new JButton(leftArrow);
        prevYear.setActionCommand("prevYear");
        prevYear.addActionListener(this);

        JButton nextYear = new JButton(rightArrow);
        nextYear.setActionCommand("nextYear");
        nextYear.addActionListener(this);

        JButton prevMonth = new JButton(leftArrow);
        prevMonth.setActionCommand("prevMonth");
        prevMonth.addActionListener(this);

        JButton nextMonth = new JButton(rightArrow);
        nextMonth.setActionCommand("nextMonth");
        nextMonth.addActionListener(this);

        // Panel containing the current year label and buttons to increment or decrement current year
        JPanel yearPanel = new JPanel(new BorderLayout());
        yearPanel.add(prevYear, BorderLayout.WEST);
        yearPanel.add(yearLabel, BorderLayout.CENTER);
        yearPanel.add(nextYear, BorderLayout.EAST);

        // Panel containing the current month label and buttons to increment or decrement current month
        JPanel monthPanel = new JPanel(new BorderLayout());
        monthPanel.add(prevMonth, BorderLayout.WEST);
        monthPanel.add(monthLabel);
        monthPanel.add(nextMonth, BorderLayout.EAST);

        header.add(yearPanel, BorderLayout.NORTH);
        header.add(new JSeparator(SwingConstants.HORIZONTAL));
        header.add(monthPanel, BorderLayout.SOUTH);

        return header;
    }

    public void redrawPanel() {
        this.removeAll();
        this.add(getCalendarHeader(), BorderLayout.PAGE_START);
        this.add(getCalendarGrid(), BorderLayout.CENTER);
        this.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("prevYear".equals(e.getActionCommand())) {
            calendar.roll(Calendar.YEAR, false);
        }
        else if ("nextYear".equals(e.getActionCommand())) {
            calendar.roll(Calendar.YEAR, true);
        }
        else if ("prevMonth".equals(e.getActionCommand())) {
            calendar.roll(Calendar.MONTH, false);
        }
        else if ("nextMonth".equals(e.getActionCommand())) {
            calendar.roll(Calendar.MONTH, true);
        }

        currentYear = getCurrentYear();
        currentMonth = getCurrentMonth();

        redrawPanel();
    }
}
