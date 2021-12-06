package view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.calendar.Lesson;

public class AnchorPaneNode extends AnchorPane {
    private Lesson lesson;

    public AnchorPaneNode(Node... nodes) {
        super(nodes);
        this.setOnMouseClicked(e -> {
                if ((this.lesson.getCourse().equals("Break"))) addALesson();
                else displayLesson();
                System.out.println("This pane's info is: " + toString());});
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void displayLesson() {
        Stage displayWindow = new Stage();
        displayWindow.initModality(Modality.APPLICATION_MODAL);
        displayWindow.setTitle("Display Lesson");
        VBox finalView = new VBox(25);
        Label course = new Label("Course : " + lesson.getCourse().toString());
        Label start = new Label("Lesson starts at : " + lesson.getStart());
        Label end = new Label("Lesson ends at : " + lesson.getEnd());
        finalView.getChildren().add(course);
        finalView.getChildren().add(start);
        finalView.getChildren().add(end);
        finalView.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(finalView, 300, 200);
        displayWindow.setScene(scene1);
        displayWindow.showAndWait();
    }

    public void addALesson() {
        this.getStyleClass().add("selected");
        Stage displayWindow = new Stage();
        displayWindow.initModality(Modality.APPLICATION_MODAL);
        displayWindow.setTitle("Add a lesson");
        VBox finalView = new VBox(25);
        Label greeting = new Label("Do you want to add a new lesson here?");
        Label timePeriods = new Label("New lesson is between :" + lesson.getStart() + " -- " + lesson.getEnd());
        finalView.getChildren().add(greeting);
        finalView.getChildren().add(timePeriods);
        finalView.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(finalView, 300, 200);
        displayWindow.setScene(scene1);
        displayWindow.showAndWait();
        this.getStyleClass().clear();
    }

    @Override
    public String toString() {
        return "AnchorPaneNode{" +
                "lesson=" + lesson +
                '}';
    }
}
