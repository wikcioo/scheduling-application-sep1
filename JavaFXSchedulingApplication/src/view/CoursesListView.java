package view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import model.calendar.Day;
import model.calendar.Lesson;
import model.calendar.Week;


import java.util.ArrayList;

public class CoursesListView {
    private VBox finalView = new VBox(4);
    private ArrayList<String> listOfCourses = new ArrayList<>();
    private ArrayList<Button> buttonsForEachCourse = new ArrayList<>();
    public CoursesListView(Week week) {

        for (Day day : week.getDays()) {
            for (Lesson lesson :day.getLessons()) {
                if (!listOfCourses.contains(lesson.getCourse()))
                    listOfCourses.add(lesson.getCourse().getTitle());
            }
        }

        for (String course:listOfCourses) {
            Button button = new Button(course);
            button.getStyleClass().add(course);
            button.setTextFill(Color.WHITE);
            button.setFont(Font.font ("Century Gothic", 22));
            finalView.getChildren().add(button);
            buttonsForEachCourse.add(button);
        }
    }

    public VBox getFinalView() {
        return finalView;
    }

    public ArrayList<Button> getButtonsForEachCourse() {
        return buttonsForEachCourse;
    }
}
