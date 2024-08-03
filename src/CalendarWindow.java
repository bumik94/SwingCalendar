import java.awt.*;
import javax.swing.*;

public class CalendarWindow {

    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Get Calendar components
        CalendarModel calendarModel = new CalendarModel();
        JPanel calendarGrid = calendarModel.getCalendarGrid();
        JPanel calendarHeader = calendarModel.getCalendarHeader();

        // Add components to the window
        frame.getContentPane().add(calendarHeader, BorderLayout.PAGE_START);
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
