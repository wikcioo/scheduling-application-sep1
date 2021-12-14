package view;


import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Model;
import model.calendar.Day;
import model.calendar.Lesson;
import model.courses.Course;
import model.courses.Teacher;
import model.rooms.BookingTime;
import model.rooms.Room;
import utilities.Logger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

public class AnchorPaneNode extends AnchorPane {
    private Lesson lesson;
    private Day day;
    private Model model;
    @FXML
    private ComboBox<Course> userInputForCourse;
    @FXML
    private ComboBox<Teacher> userInputForTeacher;

    public AnchorPaneNode(Model model, Node... nodes) {
        super(nodes);
        this.model = model;
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
        Label date = new Label("Date: " + day.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        finalView.getChildren().add(course);
        finalView.getChildren().add(start);
        finalView.getChildren().add(end);
        finalView.getChildren().add(date);

        Label room;
        if (lesson.getRoom() != null) {
            room = new Label("Room : " + lesson.getRoom());
            finalView.getChildren().add(room);
        }

        Label room2;
        if (lesson.getRoom2() != null) {
            room2 = new Label("Room : " + lesson.getRoom());
            finalView.getChildren().add(room2);
        }

        Button remove = new Button("remove this lesson");
        remove.setOnMouseClicked(event -> {
            displayWindow.close();
            removeLesson();
        });

        finalView.getChildren().add(remove);
        finalView.setAlignment(Pos.CENTER);
        Scene scene = new Scene(finalView, 300, 300);
        displayWindow.setScene(scene);
        displayWindow.showAndWait();
    }

    public void editLessonDisplay() {
        //Current Lesson info
        Stage displayWindow = new Stage();
        displayWindow.initModality(Modality.APPLICATION_MODAL);
        displayWindow.setTitle("Edit a lesson");
        Text text = new Text("Edit a lesson");
        Label course = new Label("Course : " + lesson.getCourse());
        Label start = new Label("Lesson starts at : " + lesson.getStart());
        Label end = new Label("Lesson ends at : " + lesson.getEnd());
        //GridPane Info
        int gridRow = 0;
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        //Course combo
        Label labelForCourse = new Label("Course:  ");
        userInputForCourse = CourseComboBox();
        userInputForCourse.getItems().addAll(model.getCoursesByClass(model.getCurrentSchedule().getClassOfStudents()));
        gridPane.add(labelForCourse, 0, gridRow);
        gridPane.add(userInputForCourse, 1, gridRow);

        userInputForCourse.setOnAction(event -> {
            userInputForTeacher.setDisable(false);
            userInputForTeacher.getItems().clear();
            if (userInputForCourse.getValue() != null)
                userInputForTeacher.getItems().addAll(userInputForCourse.getValue().getTeacherList().getTeacherList());
        });

        gridRow++;
        Label labelForStart = new Label("Start Hour:  ");
        TextField userInputForStart = new TextField();
        gridPane.add(labelForStart, 0, gridRow);
        gridPane.add(userInputForStart, 1, gridRow);

        gridRow++;
        Label labelForStartMin = new Label("Start Min:  ");
        TextField userInputForStartMin = new TextField();
        gridPane.add(labelForStartMin, 0, gridRow);
        gridPane.add(userInputForStartMin, 1, gridRow);

        gridRow++;
        Label labelForEnd = new Label("End Hour:  ");
        TextField userInputForEnd = new TextField();
        gridPane.add(labelForEnd, 0, gridRow);
        gridPane.add(userInputForEnd, 1, gridRow);

        gridRow++;
        Label labelForEndMin = new Label("End Min:  ");
        TextField userInputForEndMin = new TextField();
        gridPane.add(labelForEndMin, 0, gridRow);
        gridPane.add(userInputForEndMin, 1, gridRow);

        Button submit = new Button("Submit");
        submit.setOnMouseClicked(event -> {
            editLesson(userInputForCourse.getValue(), userInputForStart.getText(), userInputForStartMin.getText(), userInputForEnd.getText(), userInputForEndMin.getText());
            displayWindow.close();
        });
        VBox finalView = new VBox(25);
        finalView.getChildren().add(text);
        finalView.getChildren().add(course);
        finalView.getChildren().add(start);
        finalView.getChildren().add(end);
        finalView.getChildren().add(gridPane);
        finalView.getChildren().add(submit);
        finalView.setAlignment(Pos.CENTER);
        Scene scene = new Scene(finalView, 300, 600);
        displayWindow.setScene(scene);
        displayWindow.showAndWait();
    }

    public void addALesson(Course userInputForCourse, String userInputForStart, String userInputForStartMin, String userInputForEnd, String userInputForEndMin) {
        //Convert all data to int
        LocalTime timeStart = LocalTime.of(Integer.parseInt(userInputForStart), Integer.parseInt(userInputForStartMin));
        LocalTime timeEnd = LocalTime.of(Integer.parseInt(userInputForEnd), Integer.parseInt(userInputForEndMin));
        Lesson lesson = new Lesson(userInputForCourse, timeStart, timeEnd);
        if ((day.isValidDataForTime(lesson)) && (timeStart.isBefore(timeEnd))) {
            this.lesson = lesson;
            day.addLesson(lesson);
            bookARoom(lesson);
        } else error3();
    }

    public void editLesson(Course userInputForCourse, String userInputForStart, String userInputForStartMin, String userInputForEnd, String userInputForEndMin) {
        //Convert all data to int
        LocalTime timeStart = LocalTime.of(Integer.parseInt(userInputForStart), Integer.parseInt(userInputForStartMin));
        LocalTime timeEnd = LocalTime.of(Integer.parseInt(userInputForEnd), Integer.parseInt(userInputForEndMin));
        Lesson lesson = new Lesson(userInputForCourse, timeStart, timeEnd);
        day.removeLesson(this.lesson);
        if ((day.isValidDataForTime(lesson)) && (timeStart.isBefore(timeEnd))) {
            this.lesson.setStart(timeStart);
            this.lesson.setEnd(timeEnd);
            this.lesson.setCourse(userInputForCourse);
            day.addLesson(this.lesson);
        } else {
            day.addLesson(this.lesson);
            error3();
        }
    }

    public void addALessonDisplay() {
        Stage displayWindow = new Stage();
        displayWindow.initModality(Modality.APPLICATION_MODAL);
        displayWindow.setTitle("Add a lesson");
        VBox finalView = new VBox(25);
        Label greeting = new Label("Do you want to add a new lesson here?");
        Label timePeriods = new Label("New lesson is between :" + lesson.getStart() + " -- " + lesson.getEnd());
        Label date = new Label("New lesson is in " + day.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
        Label info = new Label("Add a new Lesson with the following fields:");
        GridPane gridPane = new GridPane();
        int gridRow = 0;
        gridPane.setAlignment(Pos.CENTER);

        Label labelForCourse = new Label("Course:  ");
        userInputForCourse = CourseComboBox();
        userInputForCourse.getItems().addAll(model.getCoursesByClass(model.getCurrentSchedule().getClassOfStudents()));
        gridPane.add(labelForCourse, 0, gridRow);
        gridPane.add(userInputForCourse, 1, gridRow);
        userInputForCourse.setOnAction(event -> {
            userInputForTeacher.setDisable(false);
            userInputForTeacher.getItems().clear();
            if (userInputForCourse.getValue() != null)
                userInputForTeacher.getItems().addAll(userInputForCourse.getValue().getTeacherList().getTeacherList());
        });

        gridRow++;
        Label labelForTeacher = new Label("Teacher:  ");
        userInputForTeacher = TeacherComboBox();
        userInputForTeacher.setDisable(true);
        gridPane.add(labelForTeacher, 0, gridRow);
        gridPane.add(userInputForTeacher, 1, gridRow);

        gridRow++;
        Label labelForStart = new Label("Start Hour:  ");
        TextField userInputForStart = new TextField();
        gridPane.add(labelForStart, 0, gridRow);
        gridPane.add(userInputForStart, 1, gridRow);

        gridRow++;
        Label labelForStartMin = new Label("Start Min:  ");
        TextField userInputForStartMin = new TextField();
        gridPane.add(labelForStartMin, 0, gridRow);
        gridPane.add(userInputForStartMin, 1, gridRow);

        gridRow++;
        Label labelForEnd = new Label("End Hour:  ");
        TextField userInputForEnd = new TextField();
        gridPane.add(labelForEnd, 0, gridRow);
        gridPane.add(userInputForEnd, 1, gridRow);

        gridRow++;
        Label labelForEndMin = new Label("End Min:  ");
        TextField userInputForEndMin = new TextField();
        gridPane.add(labelForEndMin, 0, gridRow);
        gridPane.add(userInputForEndMin, 1, gridRow);

        Button submit = new Button("Submit");

        submit.setOnMouseClicked(event -> {
            addALesson(userInputForCourse.getValue(), userInputForStart.getText(), userInputForStartMin.getText(), userInputForEnd.getText(), userInputForEndMin.getText());
            displayWindow.close();
        });

        finalView.getChildren().addAll(greeting, timePeriods, info, gridPane, submit);
        finalView.setAlignment(Pos.CENTER);
        Scene scene = new Scene(finalView, 300, 500);
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
                        if (item != null) {
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
            protected void updateItem(Course item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getTitle());
                } else {
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
                        if (item != null) {
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
            protected void updateItem(Teacher item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                } else {
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

    public void bookARoom(Lesson lesson1) {
        Stage displayWindow = new Stage();

        displayWindow.setResizable(false);
        displayWindow.initModality(Modality.APPLICATION_MODAL);
        displayWindow.setHeight(400);
        displayWindow.setWidth(400);
        displayWindow.setTitle("Room Booking");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.LEFT);
        grid.getColumnConstraints().add(column1);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        grid.getColumnConstraints().add(column2);
        HBox hbButtons = new HBox();
        hbButtons.setSpacing(10.0);
        grid.setAlignment(Pos.CENTER);
        Button btnCancel = new Button("Cancel");
        Button btnBook = new Button("Book");
        Button btnMerge = new Button("MergeRoom");
        hbButtons.getChildren().addAll(btnBook, btnCancel, btnMerge);

        GridPane innerGrid = new GridPane();
        innerGrid.setAlignment(Pos.CENTER);

        Label lblName = new Label("Select room to book for this lesson:");

        TableView t = new TableView();
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn capacityColumn = new TableColumn("Capacity");
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        TableColumn mergeColumn = new TableColumn("Merge with");
        mergeColumn.setCellValueFactory(new PropertyValueFactory<>("mergeWith"));

        t.setPrefHeight(200);
        t.getColumns().addAll(nameColumn, capacityColumn, mergeColumn);
        BookingTime book = new BookingTime(lesson.getDate(), lesson.getStart(), lesson.getEnd());
        for (Room r : this.model.getAvailableRoomsAt(book)) {
            t.getItems().add(r);
        }

        innerGrid.add(hbButtons, 0, 20);

        grid.add(lblName, 0, 1);
        grid.add(t, 0, 2);
        grid.add(innerGrid, 0, 3, 3, 1);
        btnCancel.setOnAction(e -> displayWindow.close());
        Lesson finalLesson = lesson1;

        btnBook.setOnAction(e -> {
            int index2 = t.getSelectionModel().getFocusedIndex();
            if (finalLesson.getRoom() == null && index2 >= 0) {
                finalLesson.setRoom(
                        this.model.getAvailableRoomsAt(book).get(index2));
                this.model.getAvailableRoomsAt(book).get(index2).Book(book);
                displayWindow.close();
                Logger.info(model.getRooms().toString());
            }
        });

        btnMerge.setOnAction(e -> {
            int index3 = t.getSelectionModel().getFocusedIndex();

            if (finalLesson.getRoom() == null && index3 >= 0) {
                Room room = this.model.getAvailableRoomsAt(book).get(index3);
                finalLesson.setRoom(room);
                room.Book(book);
                if (room.getMergeWith() != null) {
                    Room room2 = this.model.getRoomByString(room.getMergeWith());
                    try {
                        if (room2.canBeBookedAt(book)) {
                            finalLesson.setRoom2(room2);
                            room2.Book(book);
                        }
                    } catch (NullPointerException E) {

                    }
                } else {
                    error();
                }
                displayWindow.close();
                Logger.info(model.getRooms().toString());
            } else {
                error2();
            }
        });

        Scene scene1 = new Scene(grid, 400, 400);
        displayWindow.setScene(scene1);
        displayWindow.show();
    }

    public AnchorPane returnAp() {
        return this;
    }

    public Lesson getLesson() {
        return lesson;
    }

    private boolean error() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Cannot booked merged rooms.Only one room booked"
        );
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }

    private boolean error2() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("It cannot be merged. Only one room booked"
        );
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }

    private boolean error3() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("There is already a lesson overlapping");
        Optional<ButtonType> result = alert.showAndWait();
        return (result.isPresent()) && (result.get() == ButtonType.OK);
    }
}
