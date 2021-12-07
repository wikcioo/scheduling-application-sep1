package view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.calendar.Day;
import model.calendar.Lesson;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class AnchorPaneNode extends AnchorPane {
    private Lesson lesson;
    private Day day;


    public AnchorPaneNode(Node... nodes) {
        super(nodes);
        this.setOnMouseClicked(e -> {
                if ((!this.lesson.getCourse().equals("Break"))) displayLesson();
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
        Button remove = new Button("remove this lesson");
        Button edit = new Button("edit this lesson");
        remove.setOnMouseClicked(event -> {
            displayWindow.close();
            removeLesson();
        });
        edit.setOnMouseClicked(event -> {
            displayWindow.close();
            editlesson();
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
        Text text = new Text("Lol");
        VBox finalView = new VBox(25);
        finalView.getChildren().add(text);
        Scene scene = new Scene(finalView,300,200);
        displayWindow.setScene(scene);
        displayWindow.showAndWait();
    }

    public void removeLesson() {
        day.removeLesson(this.lesson);
        Lesson lesson = new Lesson("Break",this.lesson.getStart(),this.lesson.getEnd());
        this.lesson = lesson;
        this.getStyleClass().clear();
        this.getChildren().remove(0);
        this.getChildren().remove(0);
    }


    @Override
    public String toString() {
        return "AnchorPaneNode{" +
                "lesson=" + lesson +
                '}';
    }

    public Lesson getLesson() {
        return lesson;
    }
}
