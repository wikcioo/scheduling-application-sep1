package controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;
import model.courses.ClassOfStudents;
import model.courses.Course;
import model.courses.Teacher;
import model.courses.TeacherList;
import model.students.Student;
import view.ViewHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;


public class CourseListViewController extends ViewController {
    @FXML
    TextField textField;
    @FXML
    TableView tableView;
    @FXML
    private Region root;
    private Model model;
    private ViewHandler viewHandler;

    public CourseListViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, Model model, Region root) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;

        TableColumn titleColumn = new TableColumn("Title");
        titleColumn.setSortable(false);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn _classColumn = new TableColumn("Class name");
        _classColumn.setSortable(false);
        _classColumn.setCellValueFactory(new PropertyValueFactory<>("ClassName"));
        TableColumn teacherColumn = new TableColumn("Teachers");
        teacherColumn.setSortable(false);
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("TeacherName"));


        tableView.getColumns().addAll(titleColumn, _classColumn, teacherColumn);
        for (Course c : this.model.getCourseList().getCourses()) {
            tableView.getItems().add(c);
        }

    }

    public void reset() {
        tableView.getItems().clear();
        for (Course c : this.model.getCourseList().getCourses()) {
            tableView.getItems().add(c);
        }
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void onBackButtonClick() {
        viewHandler.openView("ManageDataView");
    }

    @FXML
    private void onAddButtonClick() {
        onClick("add");
    }

    @FXML
    private void onEditButtonClick() {
        onClick("edit");
    }

    @FXML
    private void onRemoveButtonClick() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm removing");
            alert.setHeaderText("Confirm removing course");
            alert.setContentText("Are you sure? This action will remove the selected course.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                Course course = (Course) tableView.getSelectionModel().getSelectedItem();
                this.model.getCourseList().removeCourse(course);
                tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    public void onImportFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        this.model.getCourseList().readCoursesFromTXTFile(file);
        reset();
    }

    @FXML
    public void onResetButtonClick() {
        reset();
    }

    public void onClick(String clickId) {
        if (clickId.equals("edit") && tableView.getSelectionModel().getSelectedItem() == null) {
            //Throw alert for not selecting
        } else {
            Stage displayWindow = new Stage();
            displayWindow.initModality(Modality.APPLICATION_MODAL);
            displayWindow.setTitle("Display Student");
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(12);
            ColumnConstraints column1 = new ColumnConstraints();
            column1.setHalignment(HPos.RIGHT);
            grid.getColumnConstraints().add(column1);
            ColumnConstraints column2 = new ColumnConstraints();
            column2.setHalignment(HPos.LEFT);
            grid.getColumnConstraints().add(column2);
            HBox hbButtons = new HBox();
            hbButtons.setSpacing(10.0);
            grid.setAlignment(Pos.CENTER);
            Button btnCancel = new Button("Cancel");
            TextField tfTitle = new TextField();
            TextField tfClassName = new TextField();
            TextField tfTeacher = new TextField();


            switch (clickId) {
                case "edit":
                    Button btnChange = new Button("Change");
                    Button btnReset = new Button("Reset");
                    hbButtons.getChildren().addAll(btnChange, btnReset, btnCancel);
                    int index = tableView.getSelectionModel().getFocusedIndex();
                    Course course = this.model.getCourseList().getCourses().get(index);
                    tfTitle.setText(course.getTitle());
                    tfClassName.setText(course.getClassName());
                    tfTeacher.setText(course.getTeacherList().getTeacherByIndex(0).getName());

                    btnChange.setOnAction(e -> {
                        ArrayList<Teacher> teacherList = new ArrayList<Teacher>();
                        String[] token = tfTeacher.getText().split(",");
                        for (String s: token) {
                            teacherList.add(new Teacher(s.trim()));
                        }
                        this.model.getCourseList().getCourses().set(index, new Course(tfTitle.getText(), teacherList , new ClassOfStudents(tfClassName.getText())));
                        displayWindow.close();
                    });
                    btnReset.setOnAction(e -> {
                        tfTitle.setText(course.getTitle());
                        tfClassName.setText(course.getClassName());
                        tfTeacher.setText(course.getTeacherList().getTeacherByIndex(0).toString());

                    });
                    break;
                default://Adding case
                    Button btnAdd = new Button("Add");
                    Button btnClear = new Button("Clear");
                    hbButtons.getChildren().addAll(btnAdd, btnClear, btnCancel);
                    btnAdd.setOnAction(e -> {
                        ArrayList<Teacher> teacherList = new ArrayList<Teacher>();
                        String[] token = tfTeacher.getText().split(",");
                        for (String s: token) {
                            teacherList.add(new Teacher(s.trim()));
                        }
                        this.model.getCourseList().addCourse(new Course(tfTitle.getText(), teacherList, new ClassOfStudents(tfClassName.getText())));
                        displayWindow.close();
                    });
                    break;
            }

            GridPane innerGrid = new GridPane();
            innerGrid.setAlignment(Pos.CENTER);
            innerGrid.add(hbButtons, 0, 0);

            Label lblTitle = new Label("Course title:");
            Label lblClassName = new Label("Class Name:");
            Label lblTeacher = new Label("Teachers:");


            grid.add(lblTitle, 0, 0);
            grid.add(tfTitle, 1, 0);
            grid.add(lblClassName, 0, 1);
            grid.add(tfClassName, 1, 1);
            grid.add(lblTeacher, 0, 2);
            grid.add(tfTeacher, 1, 2);
            grid.add(innerGrid, 0, 3, 3, 1);
            btnCancel.setOnAction(e -> displayWindow.close());
            Scene scene1 = new Scene(grid, 300, 300);
            displayWindow.setScene(scene1);
            displayWindow.showAndWait();
            reset();
        }
    }

    public void onViewDetailsClick(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            this.model.getCourseList().setCurrentSelectedCourse(tableView.getSelectionModel().getFocusedIndex());
            this.viewHandler.openView("TeacherListView");
        }
    }

    public void onNewFilter(){
        tableView.getItems().clear();
        for (Course c : this.model.getCourseList().getCourses()) {
            String filter = textField.getText();
            if(filter!=""&&(c.getTitle().toLowerCase().contains(filter.toLowerCase())||c.getClassName().toLowerCase().contains(filter.toLowerCase())||c.getTeacherName().toLowerCase().contains(filter))){
                tableView.getItems().add(c);
            }

        }
    }
}
