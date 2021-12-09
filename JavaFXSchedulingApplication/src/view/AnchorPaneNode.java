package view;


import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.calendar.Day;
import model.calendar.Lesson;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class AnchorPaneNode extends AnchorPane {
    private Lesson lesson;
    private Day day;


    public AnchorPaneNode(Node... nodes) {
        super(nodes);
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void setDay(Day day) {
        this.day = day;
    }
    public void displayLesson()  {
        Stage displayWindow = new Stage();
        displayWindow.initModality(Modality.APPLICATION_MODAL);
        displayWindow.setTitle("Display Lesson");
        VBox finalView = new VBox(25);
        Label course = new Label("Course : " + lesson.getCourse().toString());
        Label start = new Label("Lesson starts at : " + lesson.getStart());
        Label end = new Label("Lesson ends at : " + lesson.getEnd());
        Label date = new Label("Date: "  + day.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        Button remove = new Button("remove this lesson");
        Button edit = new Button("edit this lesson");
        remove.setOnMouseClicked(event -> {
            displayWindow.close();
            removeLesson();
        });
        finalView.getChildren().add(course);
        finalView.getChildren().add(start);
        finalView.getChildren().add(end);
        finalView.getChildren().add(date);
        finalView.getChildren().add(remove);
        finalView.getChildren().add(edit);
        finalView.setAlignment(Pos.CENTER);
        Scene scene = new Scene(finalView, 300, 300);
        displayWindow.setScene(scene);
        displayWindow.showAndWait();
    }

    public void editlesson() {
        Stage displayWindow = new Stage();
        displayWindow.initModality(Modality.APPLICATION_MODAL);
        displayWindow.setTitle("Edit a lesson");
        Text text = new Text("Edit a lesson");
        Label course = new Label("Course : " + lesson.getCourse());
        Label newCourse = new Label("New course is : ");
        TextField newCourseInput = new TextField();
        Label start = new Label("Lesson starts at : " + lesson.getStart());
        Label newStart = new Label("New lesson starts at:");
        TextField newStartInput = new TextField();
        Label end = new Label("Lesson ends at : " + lesson.getEnd());
        Label newEnd = new Label("New lesson ends at:");
        TextField newEndInput = new TextField();
        Button submit = new Button("Submit");
        submit.setOnMouseClicked(event -> {
            displayWindow.close();
        });
        VBox finalView = new VBox(25);
        finalView.getChildren().add(text);
        finalView.getChildren().add(course);
        finalView.getChildren().add(start);
        finalView.getChildren().add(newStart);
        finalView.getChildren().add(newStartInput);
        finalView.getChildren().add(end);
        finalView.getChildren().add(newEnd);
        finalView.getChildren().add(newEndInput);
        finalView.getChildren().add(submit);
        finalView.setAlignment(Pos.CENTER);
        Scene scene = new Scene(finalView,300,600);
        displayWindow.setScene(scene);
        displayWindow.showAndWait();
    }

    public void addALesson(String userInput,String userInputForStart,String userInputForStartMin,String userInputForEnd,String userInputForEndMin) {
        //Convert all data to int
        LocalTime timeStart = LocalTime.of(Integer.parseInt(userInputForStart),Integer.parseInt(userInputForStartMin));
        LocalTime timeEnd = LocalTime.of(Integer.parseInt(userInputForEnd),Integer.parseInt(userInputForEndMin));
        Lesson lesson = new Lesson(userInput,timeStart,timeEnd);
        this.lesson = lesson;
        day.addLesson(lesson);
    }

    public void editALesson(String userInput) {
        Lesson lesson = new Lesson(userInput,this.lesson.getStart(),this.lesson.getEnd());
        this.lesson = lesson;
        day.removeLesson(lesson);
        day.addLesson(lesson);
    }

    public void addALesson() {
        Stage displayWindow = new Stage();
        displayWindow.initModality(Modality.APPLICATION_MODAL);
        displayWindow.setTitle("Add a lesson");
        VBox finalView = new VBox(25);
        Label greeting = new Label("Do you want to add a new lesson here?");
        Label timePeriods = new Label("New lesson is between :" + lesson.getStart() + " -- " + lesson.getEnd());
        Label date = new Label("New lesson is in "  + day.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));

        Label info = new Label("Add a new Lesson with the following fields:");
        GridPane gridPane = new GridPane();
        Label labelForCourse = new Label("Course name:  ");
        TextField userInputForCourse = new TextField();
        gridPane.add(labelForCourse,0,0);
        gridPane.add(userInputForCourse,1,0);
        gridPane.setAlignment(Pos.CENTER);
        Label labelForStart = new Label("Start Hour:  ");
        TextField userInputForStart = new TextField();
        gridPane.add(labelForStart,0,1);
        gridPane.add(userInputForStart,1,1);
        Label labelForStartMin = new Label("Start Min:  ");
        TextField userInputForStartMin = new TextField();
        gridPane.add(labelForStartMin,0,2);
        gridPane.add(userInputForStartMin,1,2);
        Label labelForEnd = new Label("End Hour:  ");
        TextField userInputForEnd = new TextField();
        gridPane.add(labelForEnd,0,3);
        gridPane.add(userInputForEnd,1,3);
        Label labelForEndMin = new Label("End Min:  ");
        TextField userInputForEndMin = new TextField();
        gridPane.add(labelForEndMin,0,4);
        gridPane.add(userInputForEndMin,1,4);
        gridPane.setAlignment(Pos.CENTER);
        Button submit = new Button("Submit");
        submit.setOnMouseClicked(event -> {
            addALesson(userInputForCourse.getText(),userInputForStart.getText(),userInputForStartMin.getText(),userInputForEnd.getText(),userInputForEndMin.getText());
            displayWindow.close();
        });
        finalView.getChildren().add(greeting);
        finalView.getChildren().add(timePeriods);
        finalView.getChildren().add(date);
        finalView.getChildren().add(info);
        finalView.getChildren().add(gridPane);
        finalView.getChildren().add(submit);
        finalView.setAlignment(Pos.CENTER);
        Scene scene = new Scene(finalView,300,500);
        displayWindow.setScene(scene);
        displayWindow.showAndWait();
    }

    public void removeLesson() {
        day.removeLesson(this.lesson);
    }
    @Override
    public String toString() {
        return "AnchorPaneNode{" +
                "lesson=" + lesson +
                '}';
    }

    public AnchorPane returnAp() {
        return this;
    }

    public Lesson getLesson() {
        return lesson;
    }
}
