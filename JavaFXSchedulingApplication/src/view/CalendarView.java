package view;

import javafx.scene.text.Font;
import model.Model;
import model.calendar.Day;
import model.calendar.Lesson;
import model.calendar.Week;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.courses.Course;
import utilities.Logger;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * This calendar view class generates a view dynamically.Through code, you generate 7 VBoxes that represent the days of
 * the week.On each day you can put lessons that are represented by anchor panes.Every anchor pane contains information
 * about the lesson as well as a color.This view also generates empty blocks for breaks , so you can click on them to
 * add other lessons and to represent hours accurately on the schedule.Every time you change the week the calendar
 * regenerates through the controller method "init calendar".
 */

public class CalendarView {
    private Model model;
    private VBox[] daysView = new VBox[7];
    private VBox finalView; //Final view needed to put the calendar in the scrollPane
    private ArrayList<AnchorPaneNode> lessonBlocks;
    private ArrayList<AnchorPaneNode> emptyBlocks;

    /**
     * This is the constructor for the calendar view.You need to pass in the information from the model so the view can
     * be generated based on the data.This constructor initializes all the parameters.
     * Then the class constructor it's going to put all the lesson of the week in the view using specific methods.
     *
     * @param model passes in information from the current week.
     */
    public CalendarView(Model model) {
        this.model = model;
        Week week = model.getCurrentWeek();
        lessonBlocks = new ArrayList<>();
        emptyBlocks = new ArrayList<>();
        int hour = 7;
        HBox calendar = new HBox(5);
        calendar.getStyleClass().add("linearStripes");

        for (int i = 0; i < 7; i++) {
            VBox vb = new VBox(0);
            vb.setPrefWidth(200);
            vb.getStyleClass().add("transparent");
            daysView[i] = vb;
            calendar.getChildren().add(vb);
        }

        putLessonWeekOnCalendar(week);
        finalView = new VBox(calendar);
    }

    /**
     * This method initializes all the days view to put them in the constructor.
     *
     * @param week Current week from the model
     */
    private void putLessonWeekOnCalendar(Week week) {
        for (Day day : week.getDays()) {
            putLessonDayOnCalendar(day);
        }
    }

    /**
     * This method generates AnchorPaneNodes for each dayView.Each day view is a Vbox and you add AnchorPaneNodes to that
     * specific day view.The lessons are sorted, so they are going to be represented in the correct order in the list.
     * This method then calculates the difference between the start fixed hour and the end hour to add empty block before
     * the first lesson(if necessary) and after the last lesson.It also checks for the difference between lesson, so it
     * could add an empty block between lessons
     *
     * @param day represents the day of the current week
     */
    //FIXME this needs refactoring and testing
    private void putLessonDayOnCalendar(Day day) {
        LocalTime fixedStart = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(23, 0);
        ArrayList<Lesson> currentDayLessons = day.getLessons();
        if (currentDayLessons.size() == 0) {
            daysView[day.getIndexForDay()].getChildren().add(getBlockForEmpty(fixedStart, end, day));
            fixedStart = fixedStart.plusHours(1);
        }
        for (int i = 0; i < currentDayLessons.size(); i++) {
            //Put a break before the lesson if the lesson doesn't start at fixedStart
            Lesson lesson = currentDayLessons.get(i);
            if (i == 0) {
                Duration dif = Duration.between(fixedStart, lesson.getStart());
                if ((dif.toHoursPart() > 0) || (dif.toMinutesPart() > 0))
                    daysView[day.getIndexForDay()].getChildren().add(getBlockForEmpty(fixedStart, lesson.getStart(), day));
            }
            //Put current lesson on the calendar
            AnchorPaneNode lessonBlock = getBlockForLesson(lesson.getStart(), lesson.getEnd(), lesson, day);
            daysView[day.getIndexForDay()].getChildren().add(lessonBlock);
            lessonBlocks.add(lessonBlock);
            //Check for break
            if (i + 1 < currentDayLessons.size()) {
                Lesson nextLesson = currentDayLessons.get(i + 1);
                Duration dif = Duration.between(lesson.getEnd(), nextLesson.getStart());
                if ((dif.toHoursPart() >= 0) || (dif.toMinutesPart() >= 0))
                    daysView[day.getIndexForDay()].getChildren().add(getBlockForEmpty(lesson.getEnd(), nextLesson.getStart(), day));
            }
        }
        //Add a break after the last lesson
        if (currentDayLessons.size() > 0) {
            Lesson lastLesson = currentDayLessons.get(currentDayLessons.size() - 1);
            daysView[day.getIndexForDay()].getChildren().add(getBlockForEmpty(lastLesson.getEnd(), end, day));
        }
    }

    /**
     * This method returns a lesson block represented by an AnchorPaneNode.It sets the information for the
     * block as well as the specific styling for it.
     *
     * @param start  start of a lesson
     * @param finish end of a lesson
     * @param lesson lesson info from the current week
     * @param day    day that you want to put the lesson in
     * @return AnchorPaneNode with specific styling as well as information about the lesson(start,end,finish,course etc.)
     */
    private AnchorPaneNode getBlockForLesson(LocalTime start, LocalTime finish, Lesson lesson, Day day) {
        AnchorPaneNode ap = new AnchorPaneNode(model);
        ap.setPrefWidth(200);
        ap.setMaxWidth(200);
        ap.setPrefHeight(calculateHeightForBlock(start, finish));
        ap.getStyleClass().add(lesson.getCourse().getTitle());
        ap.setMaxHeight(calculateHeightForBlock(start, finish));
        String backgroundColor = String.format("-fx-background-color:%s;", lesson.getCourse().getHexColor());
        Logger.info(backgroundColor);
        ap.setStyle(backgroundColor);
        //Set the info for the anchor pane
        ap.setLesson(lesson);
        ap.setDay(day);
        Text text = new Text(lesson.getCourse().getTitle());
        Text description = new Text(start + " -- " + finish);
        Long textFont = calculateHeightForFonts(start, finish);
        text.setFont(Font.font("Century Gothic", textFont + 2));

        description.setFont(Font.font("Century Gothic", textFont - 1));
        text.setFill(Color.WHITE);
        text.setWrappingWidth(75);
        description.setFill(Color.WHITE);

        ap.getChildren().add(text);
        ap.getChildren().add(description);

        text.setLayoutX(10);
        text.setLayoutY(20);
        description.setLayoutX(10);
        description.setLayoutY(20);

        AnchorPane.setTopAnchor(text, 5.0);
        
        AnchorPane.setBottomAnchor(description, 7.0);
        return ap;
    }

    /**
     * This method returns an empty block necessary to represent breaks between lessons.It also sets the day of the
     * empty block as well as information about the block (setLesson(break)).We set the lesson to a break, so we can
     * know the on click functionality that we should add to this anchor pane.
     *
     * @param start  start of the break
     * @param finish end of the break
     * @param day    day that you want to put the empty block in
     * @return a AnchorPaneNode that is empty , has no text and contains no information.
     */
    private AnchorPaneNode getBlockForEmpty(LocalTime start, LocalTime finish, Day day) {
        AnchorPaneNode ap = new AnchorPaneNode(model);
        ap.setPrefWidth(200);
        ap.setPrefHeight(calculateHeightForBlock(start, finish));
        ap.setPrefHeight(calculateHeightForBlock(start, finish));
        //Set the info for the anchor pane
        ap.setDay(day);
        ap.setLesson(new Lesson(new Course("Break", null), start, finish));
        getEmptyBlocks().add(ap);
        return ap;
    }

    /**
     * This method calculates the necessary height for a block based on the lesson/break length.Each minute represents
     * a pixel on the schedule. First it calculates the difference between the start of a lesson and the end of a lesson
     * in minutes and hours,then calculates the result.After that it subtracts 3 to account for the surplus
     * padding of the blocks.
     *
     * @param start  representing the start of a lesson
     * @param finish representing the end of a lesson
     * @return a long representing the length of a lesson
     */
    public long calculateHeightForBlock(LocalTime start, LocalTime finish) {
        //One unit is 60 --> 1 hr ,1 minute is 1 pixel
        Duration duration = Duration.between(start, finish);
        long hour = duration.toHoursPart();
        long minute = duration.toMinutesPart();
        long result = hour * 60 + minute - 1;
        return result;
    }

    public long calculateHeightForFonts(LocalTime start, LocalTime finish) {
        //One unit is 60 --> 1 hr ,1 minute is 1 pixel
        Duration duration = Duration.between(start, finish);
        long hour = duration.toHoursPart();
        long result = 7;
        for (int i = 0; i < hour; i++) result += 2;
        return result;
    }

    /**
     * This method returns the final view of the calendar built in the constructor.
     *
     * @return the final view needed to set the scrollPane
     */
    public VBox getFinalView() {
        return finalView;
    }

    /**
     * This method returns all the AnchorPaneNodes that contain a lesson, so we can set the functionality outside the
     * view
     *
     * @return an arraylist of all the lessons blocks
     */
    public ArrayList<AnchorPaneNode> getLessonBlocks() {
        return lessonBlocks;
    }

    /**
     * This method returns all the AnchorPaneNodes that contain a break, so we can set the functionality outside the
     * view
     *
     * @return an arraylist of all the empty blocks
     */
    public ArrayList<AnchorPaneNode> getEmptyBlocks() {
        return emptyBlocks;
    }
}
