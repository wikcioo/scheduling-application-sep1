package view;

import controller.CalendarViewController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.calendar.Day;
import model.calendar.Lesson;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class AnchorPaneNode extends AnchorPane {
    private Lesson lesson;
    private Day day;


    public AnchorPaneNode(Node... nodes) {
        super(nodes);
        this.setOnMouseClicked(e -> {
                if ((this.lesson.getCourse().equals("Break"))) addALessonDisplay();
                else displayLesson();
                System.out.println("This pane's info is: " + toString());});
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public void displayLesson() {
        Stage displayWindow = new Stage();
        displayWindow.initModality(Modality.APPLICATION_MODAL);
        displayWindow.setTitle("Display Lesson");
        VBox finalView = new VBox(25);
        Label course = new Label("Course : " + lesson.getCourse().toString());
        Label start = new Label("Lesson starts at : " + lesson.getStart());
        Label end = new Label("Lesson ends at : " + lesson.getEnd());
        Label date = new Label("Date: "  + day.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        finalView.getChildren().add(course);
        finalView.getChildren().add(start);
        finalView.getChildren().add(end);
        finalView.getChildren().add(date);
        finalView.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(finalView, 300, 200);
        displayWindow.setScene(scene1);
        displayWindow.showAndWait();
    }

    public void addALessonDisplay() {
        this.getStyleClass().add("selected");
        Stage displayWindow = new Stage();
        displayWindow.initModality(Modality.APPLICATION_MODAL);
        displayWindow.setTitle("Add a lesson");
        VBox finalView = new VBox(25);
        Label greeting = new Label("Do you want to add a new lesson here?");
        Label timePeriods = new Label("New lesson is between :" + lesson.getStart() + " -- " + lesson.getEnd());
        Label date = new Label("New lesson is in "  + day.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        Label info = new Label("Add a new Lesson with the following fields:");
        Label labelForCourse = new Label("Course name:");
        TextField userInputForCourse = new TextField();
        Button submit = new Button("Submit");
        submit.setOnMouseClicked(event -> {
            addALesson(userInputForCourse.getText());
            displayWindow.close();
        });
        finalView.getChildren().add(greeting);
        finalView.getChildren().add(timePeriods);
        finalView.getChildren().add(date);
        finalView.getChildren().add(info);
        finalView.getChildren().add(labelForCourse);
        finalView.getChildren().add(userInputForCourse);
        finalView.getChildren().add(submit);
        finalView.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(finalView, 300, 500);
        displayWindow.setScene(scene1);
        displayWindow.showAndWait();
        this.getStyleClass().clear();
    }

    public void addALesson(String userInput) {
        Lesson lesson = new Lesson(userInput,this.lesson.getStart(),this.lesson.getEnd());
        day.addLesson(lesson);
    }

    @Override
    public String toString() {
        return "AnchorPaneNode{" +
                "lesson=" + lesson +
                '}';
    }
}
