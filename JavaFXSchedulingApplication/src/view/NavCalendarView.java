package view;

import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Calendar;

public class NavCalendarView {
    private VBox finalView;
    private ArrayList<Button> gridPaneItems = new ArrayList<>();
    private Text header = new Text();
    private Calendar calendar = Calendar.getInstance();

    public NavCalendarView() {
        //Make GridPane for day label

        GridPane dayGrid = new GridPane();

        //Set the width of Gridpane
        dayGrid.setPrefWidth(235);
        //Array with day labels
        dayGrid.setHgap(5);
        dayGrid.setVgap(5);

        header.setFill(Color.WHITE);
        String[] dayText = {"M", "T", "W", "T", "F", "S", "S"};
        //Set day labels in NavCalendarView
        for (int i = 0; i < 7; i++) {
            Button day = new Button(dayText[i]);
            day.setTextAlignment(TextAlignment.CENTER);
            day.getStyleClass().add("nav");
            GridPane.setHalignment(day, HPos.RIGHT);
            dayGrid.add(day, i, 0);
        }

        //Make Gridpane for days
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
        initDays();
        //Set spacing for vbox and send the final view
        this.finalView = new VBox(20);
        this.finalView.getChildren().add(header);
        updateMonthHeader();
        this.finalView.getChildren().add(dayGrid);
    }

    public void initCalendar() {
        for (Button text : gridPaneItems) {
            text.setText("");
        }
    }

    public String getCurrentMonth() {
        String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return month[calendar.get(Calendar.MONTH)];
    }

    public void updateMonthHeader() {
        header.setText(getCurrentMonth() + " " + calendar.get(Calendar.YEAR));
    }


    public VBox getFinalView() {
        return finalView;
    }

    public void initDays() {
        // FIXME: 12/2/2021 //Test solution
        //Get first day in the month
        int startDay = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (startDay <= 0) startDay = 7 + startDay; // Get startDay if it starts before the row for months that start on Sunday,Saturday,etc.
        //Get the max number of days in a mounth
        int maxNumberOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        //Calculate how much you need to substract and set previous week

        Calendar prevCalendar = (Calendar) calendar.clone();
        prevCalendar.add(Calendar.MONTH, -1); //Become Preceeding month
        int maxPrevDays = prevCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); //Get max number of days of previous month
        int count = startDay - 2;
        for (int i = 0; i < startDay - 1; i++) {
            gridPaneItems.get(i).getStyleClass().remove("white"); //Removing color so it doesn't stay when you update
            gridPaneItems.get(i).getStyleClass().remove("grey");
            gridPaneItems.get(i).getStyleClass().add("grey");
            gridPaneItems.get(i).setText(maxPrevDays - count + "");
            count--;
        }

        int dayCount = 0;
        //Set current week
        for (int i = startDay - 1; i < maxNumberOfDays + startDay - 1; i++) {
            dayCount++;
            gridPaneItems.get(i).getStyleClass().remove("white");
            gridPaneItems.get(i).getStyleClass().remove("grey");
            gridPaneItems.get(i).setText(dayCount + "");
            gridPaneItems.get(i).getStyleClass().add("white");
        }

        //Reset count and go to the next week
        dayCount = 0;
        for (int i = maxNumberOfDays + startDay - 1; i < 42; i++) {
            dayCount++;
            gridPaneItems.get(i).getStyleClass().remove("white");
            gridPaneItems.get(i).getStyleClass().remove("grey");
            gridPaneItems.get(i).getStyleClass().add("grey");
            gridPaneItems.get(i).setText(dayCount + "");
        }
    }

    public void navNextWeek() {
        calendar.add(Calendar.MONTH, 1);
        updateMonthHeader();
        initDays();
    }

    public void navPrevWeek() {
        calendar.add(Calendar.MONTH, -1);
        updateMonthHeader();
        initDays();
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
