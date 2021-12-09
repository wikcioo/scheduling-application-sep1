package view;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Model;
import model.calendar.Day;
import model.calendar.Lesson;
import model.courses.ClassOfStudents;
import model.courses.Course;
import model.courses.Teacher;

import java.awt.event.ActionEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class AnchorPaneNode extends AnchorPane {
    private Lesson lesson;
    private Day day;
    private Model model;
    @FXML
    private ComboBox<Course> userInputForCourse;
    @FXML
    private ComboBox<Teacher> userInputForTeacher;


    public AnchorPaneNode(Model model,Node... nodes) {
        super(nodes);
        this.model = model;
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

    public void addALesson(Course userInputForCourse,String userInputForStart,String userInputForStartMin,String userInputForEnd,String userInputForEndMin) {
        //Convert all data to int
        LocalTime timeStart = LocalTime.of(Integer.parseInt(userInputForStart),Integer.parseInt(userInputForStartMin));
        LocalTime timeEnd = LocalTime.of(Integer.parseInt(userInputForEnd),Integer.parseInt(userInputForEndMin));
        Lesson lesson = new Lesson(userInputForCourse,timeStart,timeEnd);
        this.lesson = lesson;
        day.addLesson(lesson);
    }

    public void editALesson(Course userInputForCourse) {
        Lesson lesson = new Lesson(userInputForCourse,this.lesson.getStart(),this.lesson.getEnd());
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
        int gridRow = 0;
        gridPane.setAlignment(Pos.CENTER);

        Label labelForCourse = new Label("Course:  ");
        userInputForCourse = CourseComboBox();
        userInputForCourse.getItems().addAll(model.getCourseList().getCourses());
        gridPane.add(labelForCourse,0,gridRow);
        gridPane.add(userInputForCourse,1,gridRow);
        userInputForCourse.setOnAction(event -> {
            userInputForTeacher.setDisable(false);
            userInputForTeacher.getItems().clear();
            if(userInputForCourse.getValue() != null)
                userInputForTeacher.getItems().addAll(userInputForCourse.getValue().getTeacherList().getTeacherList());
        });

        gridRow++;
        Label labelForTeacher = new Label("Teacher:  ");
        userInputForTeacher = TeacherComboBox();
        userInputForTeacher.setDisable(true);
        gridPane.add(labelForTeacher,0,gridRow);
        gridPane.add(userInputForTeacher,1,gridRow);

        gridRow ++;
        Label labelForStart = new Label("Start Hour:  ");
        TextField userInputForStart = new TextField();
        gridPane.add(labelForStart,0,gridRow);
        gridPane.add(userInputForStart,1,gridRow);

        gridRow ++;
        Label labelForStartMin = new Label("Start Min:  ");
        TextField userInputForStartMin = new TextField();
        gridPane.add(labelForStartMin,0,gridRow);
        gridPane.add(userInputForStartMin,1,gridRow);

        gridRow ++;
        Label labelForEnd = new Label("End Hour:  ");
        TextField userInputForEnd = new TextField();
        gridPane.add(labelForEnd,0,gridRow);
        gridPane.add(userInputForEnd,1,gridRow);

        gridRow ++;
        Label labelForEndMin = new Label("End Min:  ");
        TextField userInputForEndMin = new TextField();
        gridPane.add(labelForEndMin,0,gridRow);
        gridPane.add(userInputForEndMin,1,gridRow);

        Button submit = new Button("Submit");
        submit.setOnMouseClicked(event -> {
            addALesson(userInputForCourse.getValue(),userInputForStart.getText(),userInputForStartMin.getText(),userInputForEnd.getText(),userInputForEndMin.getText());
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

    public ComboBox<Course> CourseComboBox() {
        ComboBox<Course> comboBox = new ComboBox<>();
        comboBox.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() {
            @Override
            public ListCell<Course> call(ListView<Course> param) {
                ListCell<Course> cell = new ListCell<Course>() {
                    @Override
                    protected void updateItem(Course item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null) {
                            setText(item.getTitle());
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        comboBox.setButtonCell(new ListCell<Course>() {
            @Override
            protected void updateItem(Course item, boolean empty){
                super.updateItem(item, empty);
                if (item != null)
                {
                    setText(item.getTitle());
                }
                else
                {
                    setText(null);
                }
            }
        });
        return comboBox;
    }

    public ComboBox<Teacher> TeacherComboBox() {
        ComboBox<Teacher> comboBox = new ComboBox<>();
        comboBox.setCellFactory(new Callback<ListView<Teacher>, ListCell<Teacher>>() {
            @Override
            public ListCell<Teacher> call(ListView<Teacher> param) {
                ListCell<Teacher> cell = new ListCell<Teacher>() {
                    @Override
                    protected void updateItem(Teacher item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null) {
                            setText(item.getName());
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        comboBox.setButtonCell(new ListCell<Teacher>() {
            @Override
            protected void updateItem(Teacher item, boolean empty){
                super.updateItem(item, empty);
                if (item != null)
                {
                    setText(item.getName());
                }
                else
                {
                    setText(null);
                }
            }
        });
        return comboBox;
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
