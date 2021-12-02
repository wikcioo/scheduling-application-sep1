package view;

import model.Day;
import model.Lesson;
import model.Week;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class CalendarView extends Node {
    private VBox finalView;
    private VBox[] daysView = new VBox[7];

    public CalendarView(Week week) {
        int hour = 7;
        HBox calendar = new HBox();
        calendar.getStyleClass().add("linearStripes");

        for (int i = 0; i < 7; i++) {
            VBox vb = new VBox();
            vb.setPrefWidth(200);
            vb.getStyleClass().add("transparent");
            daysView[i] = vb;
            calendar.getChildren().add(vb);
        }

//        Day today = new Day();
//        today.setDate(LocalDate.now());
//        LocalTime time1 = LocalTime.of(8, 0);
//        LocalTime time2 = LocalTime.of(10, 0);
//        week.addLesson(new Lesson("SDJ", time1, time2), today.getIndexForDay());
//
//        LocalTime time3 = LocalTime.of(12, 0);
//        LocalTime time4 = LocalTime.of(14, 0);
//
//        week.addLesson(new Lesson("SDJ", time3, time4), today.getIndexForDay());
//
//        LocalTime time5 = LocalTime.of(14, 0);
//        LocalTime time6 = LocalTime.of(15, 0);
//
//        today.setDate(LocalDate.now().plusDays(1));
//        week.addLesson(new Lesson("SDJ", time5, time6), today.getIndexForDay());
//
//        LocalTime time7 = LocalTime.of(15, 10);
//        LocalTime time8 = LocalTime.of(17, 0);
//
//        week.addLesson(new Lesson("SDJ", time7, time8), today.getIndexForDay());
        putLessonWeekOnCalendar(week);
        finalView = new VBox(calendar);
    }

    private void putLessonWeekOnCalendar(Week week) {
        for (Day day : week.getDays()) {
            putLessonDayOnCalendar(day);
        }
    }

    private void putLessonDayOnCalendar(Day day) {
        ArrayList<Lesson> currentDayLessons = day.getLessons();

        for (int i = 0; i < currentDayLessons.size(); i++) {

            //Put currrent lesson on the calendar
            Lesson lesson = currentDayLessons.get(i);
            if (i == 0) {
                LocalTime fixedStart = LocalTime.of(8, 0);
                if (isDiference(fixedStart, lesson.getEnd()))
                    daysView[day.getIndexForDay()].getChildren().add(getBlockForEmpty(fixedStart, lesson.getStart()));
            }
            AnchorPaneNode lessonBlock = getBlockForLesson(lesson.getStart(), lesson.getEnd());
            daysView[day.getIndexForDay()].getChildren().add(lessonBlock);
            //Check for break
            if (i + 1 < currentDayLessons.size()) {
                Lesson nextLesson = currentDayLessons.get(i + 1);
                if (isDiference(lesson.getEnd(), lesson.getEnd()))
                    daysView[day.getIndexForDay()].getChildren().add(getBlockForEmpty(lesson.getEnd(), lesson.getStart()));
            }
        }
    }

    private boolean isDiference(LocalTime start, LocalTime finish) {
        Duration dif = Duration.between(start, finish);
        return (dif.toHoursPart() > 0) || (dif.toMinutesPart() > 0);
    }


    private AnchorPaneNode getBlockForLesson(LocalTime start, LocalTime finish) {
        AnchorPaneNode ap = new AnchorPaneNode();
        ap.setPrefWidth(200);
        ap.setPrefHeight(calculateHeightForBlock(start, finish));
        ap.getStyleClass().add("SDJ");
        Text text = new Text("SDJ-1Z");
        ap.setCourse("SDJ");
        Text description = new Text(start + " -- " + finish);
        text.setFill(Color.WHITE);
        description.setFill(Color.WHITE);
        ap.getChildren().add(text);
        ap.getChildren().add(description);
        text.setTextAlignment(TextAlignment.CENTER);
        AnchorPane.setTopAnchor(text, 5.0);
        AnchorPane.setTopAnchor(description, 25.0);
        return ap;
    }

    private AnchorPaneNode getBlockForEmpty(LocalTime start, LocalTime finish) {
        AnchorPaneNode ap = new AnchorPaneNode();
        ap.setPrefWidth(200);
        ap.setPrefHeight(calculateHeightForBlock(start, finish));
        System.out.println(start);
        System.out.println(finish);
        ap.setPrefHeight(calculateHeightForBlock(start, finish));
        ap.setCourse("Break");
        return ap;
    }

    public long calculateHeightForBlock(LocalTime start, LocalTime finish) {
        //One unit is 60 --> 1 hr ,1 minute is 1 pixel
        Duration duration = Duration.between(start, finish);
        long hour = duration.toHoursPart();
        long minute = duration.toMinutesPart();
        long result = hour * 60 + minute * 1;
        return result;
    }

    public VBox getFinalView() {
        return finalView;
    }
}
