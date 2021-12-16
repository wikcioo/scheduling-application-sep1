package view;

import controller.CalendarViewController;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Model;
import utilities.Logger;
import utilities.Util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;

/** Navigation calendar with month view.Using the gregorian calendar we put the days of the current month as buttons and
 * you can call previous week and next week.The final view will be a 7x6 GridPane containing all the days of the month as
 * well as some days behind and after the month with the option to navigate through this day's.
 */
public class NavCalendarView {
    private VBox finalView;
    private ArrayList<Button> gridPaneItems = new ArrayList<>();
    private Text header = new Text();
    private Calendar calendar = Calendar.getInstance();
    private Model model;
    private CalendarViewController calendarViewController;

    /** Constructor of NavCalendarView.The view is generated inside the constructor, and then you can get the final
     * view to display it dynamically.
     * @param model information from the model
     * @param calendarViewController the controller is needed to link necessary methods to day buttons
     */
    public NavCalendarView(Model model, CalendarViewController calendarViewController) {
        this.model = model;
        this.calendarViewController = calendarViewController;

        GridPane dayGrid = new GridPane();
        //Settings for day grid
        dayGrid.setHgap(5);
        dayGrid.setVgap(5);
        dayGrid.setPrefWidth(235);
        //Array with day labels
        header.setFill(Color.WHITE);
        String[] dayText = {"M", "T", "W", "T", "F", "S", "S"};
        //Set day labels in NavCalendarView.They are vertically aligned
        for (int i = 0; i < 7; i++) {
            Button day = new Button(dayText[i]);
            day.setTextAlignment(TextAlignment.CENTER);
            day.getStyleClass().add("nav");
            GridPane.setHalignment(day, HPos.RIGHT);
            dayGrid.add(day, i, 0);
        }
        //Add the days to the pane
        int count = 1;
        for (int i = 1; i < 7; i++) /* Row */
            for (int j = 0; j < 7; j++) /* Column */ {
                Button text = new Button(count + "");
                text.setTextAlignment(TextAlignment.CENTER);
                GridPane.setHalignment(text, HPos.RIGHT);
                gridPaneItems.add(text);
                text.getStyleClass().add("nav");
                dayGrid.add(text, j, i);
                count++;
            }
        initDays(); //Get the value of the days.
        //Set spacing for vbox and send the final view
        //Update the title with the month
        this.finalView = new VBox(20);
        this.finalView.getChildren().add(header);
        updateMonthHeader();
        this.finalView.getChildren().add(dayGrid);
    }

    /** This method creates an array with all the months and then assigns the current month to a string
     * @return a string representing the current month taken from the gregorian calendar
     */
    public String getCurrentMonth() {
        String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return month[calendar.get(Calendar.MONTH)];
    }

    /** This method sets the text of the header , so it can be accurate.
     *
     */
    public void updateMonthHeader() {
        header.setText(getCurrentMonth() + " " + calendar.get(Calendar.YEAR));
    }

    /** Get the final view of the calendar to display it dynamically
     * @return Vbox (header and day buttons) representing the final view
     */
    public VBox getFinalView() {
        return finalView;
    }

    /** This method initializes the text and the functionality of the buttons inside. Using the gregorian calendar inside
     * of this class we first find the position of the first day in the month.After that this method sets all the days
     * behind the first day.Then we set the days that are actually in the month and the functionality of them.
     *
     */
    public void initDays() {
        //Get first day in the month
        calendar.set(Calendar.DAY_OF_MONTH,1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (startDay <= 0)
            startDay = 7 + startDay; // Get startDay if it starts before the row for months that start on Sunday,Saturday,etc.
        //Get the max number of days in a mounth
        int maxNumberOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //Calculate how much you need to substract and set previous week

        Calendar prevCalendar = (Calendar) calendar.clone();
        prevCalendar.add(Calendar.MONTH, -1); //Become Preceeding month
        int maxPrevDays = prevCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); //Get max number of days of previous month
        int count = startDay - 2;
        for (int i = 0; i < startDay - 1; i++) {
            gridPaneItems.get(i).setOnMouseClicked(null);
            gridPaneItems.get(i).getStyleClass().remove("white"); //Removing color so it doesn't stay when you update
            gridPaneItems.get(i).getStyleClass().remove("grey");
            gridPaneItems.get(i).getStyleClass().add("grey");
            gridPaneItems.get(i).setText(maxPrevDays - count + "");
            count--;
            gridPaneItems.get(i).setOnMouseClicked(event -> navPrevWeek());
        }

        int dayCount = 0;
        //Set current week
        for (int i = startDay - 1; i < maxNumberOfDays + startDay - 1; i++) {
            dayCount++;
            gridPaneItems.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String foo = mouseEvent.getSource().toString();
                    int day_num = Integer.parseInt(foo.substring(foo.indexOf("'") + 1, foo.lastIndexOf("'")));
                    LocalDate date = Util.getMonday(LocalDate.of(calendar.getWeekYear(), Util.monthStringToMonthInt(getCurrentMonth()), day_num));
                    Logger.info(date.toString());
                    int currentWeekIndex = model.getCurrentSchedule().getCurrentWeekIndex();
                    int newWeekIndex = (int) ChronoUnit.WEEKS.between(model.getCurrentSchedule().getSemesterStart(), date);
                    int diff = currentWeekIndex - newWeekIndex;
                    Logger.info("Diff: " + diff + ", old: " + currentWeekIndex + ", new: " + newWeekIndex);
                    if (diff < 0) {
                        for (int i = 0; i < Math.abs(diff); i++) {
                            calendarViewController.onNextWeekClick();
                        }
                    } else if (diff > 0) {
                        for (int i = 0; i < diff; i++) {
                            calendarViewController.onPreviousWeekClick();
                        }
                    }

                    model.getCurrentSchedule().setCurrentWeekIndex(newWeekIndex);
                }
            });

            gridPaneItems.get(i).getStyleClass().remove("white");
            gridPaneItems.get(i).getStyleClass().remove("grey");
            gridPaneItems.get(i).setText(dayCount + "");
            gridPaneItems.get(i).getStyleClass().add("white");
        }

        //Reset count and go to the next week
        dayCount = 0;
        for (int i = maxNumberOfDays + startDay - 1; i < 42; i++) {
            dayCount++;
            gridPaneItems.get(i).setOnMouseClicked(null);
            gridPaneItems.get(i).getStyleClass().remove("white");
            gridPaneItems.get(i).getStyleClass().remove("grey");
            gridPaneItems.get(i).getStyleClass().add("grey");
            gridPaneItems.get(i).setText(dayCount + "");
            gridPaneItems.get(i).setOnMouseClicked(event -> navNextWeek());
        }
    }

    /** This method adds one month to the calendar and update the days as well as the header
     *
     */
    public void navNextWeek() {
        calendar.add(Calendar.MONTH, 1);
        updateMonthHeader();
        initDays();
    }

    /** This method subtracts one month to the calendar and update the days as well as the header
     *
     */
    public void navPrevWeek() {
        calendar.add(Calendar.MONTH, -1);
        updateMonthHeader();
        initDays();
    }

    /** This method returns the gregorian calendar(the brain of the view) from the class
     * @return the gregorian calendar
     */
    public Calendar getCalendar() {
        return calendar;
    }
}
