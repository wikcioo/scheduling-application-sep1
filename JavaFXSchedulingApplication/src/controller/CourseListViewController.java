package controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Model;
import model.courses.ClassOfStudents;
import model.courses.Course;
import model.courses.Teacher;
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
    Button editButton;
    @FXML
    Button addButton;
    @FXML
    Button removeButton;
    private Region root;
    private Model model;
    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, Model model, Region root) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;

        TableColumn titleColumn = new TableColumn("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn _classColumn = new TableColumn("Class name");
        _classColumn.setCellValueFactory(new PropertyValueFactory<>("ClassName"));
        TableColumn teacherColumn = new TableColumn("Teachers");
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("TeacherName"));


        tableView.getColumns().addAll(titleColumn, _classColumn, teacherColumn);
        for (Course c : this.model.getCourses()) {
            tableView.getItems().add(c);
        }
        setDisableCellSpecificButtons(true);
    }

    public void reset() {
        tableView.getItems().clear();
        for (Course c : this.model.getCourses()) {
            tableView.getItems().add(c);
        }
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    public void onChosenCell() {
        setDisableCellSpecificButtons(false);
    }

    public void setDisableCellSpecificButtons(boolean disable) {
        editButton.setDisable(disable);
        removeButton.setDisable(disable);
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
            setDisableCellSpecificButtons(true);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm removing");
            alert.setHeaderText("Confirm removing course");
            alert.setContentText("Are you sure? This action will remove the selected course.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Course course = (Course) tableView.getSelectionModel().getSelectedItem();
                this.model.removeCourse(course);
                tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    public void onImportFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        this.model.readCoursesFromTXTFile(file);
        reset();
    }

    public void onClick(String clickId) {
        if (clickId.equals("edit") && tableView.getSelectionModel().getSelectedItem() == null) {
            //Throw alert for not selecting
        } else {
            setDisableCellSpecificButtons(true);
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
            ComboBox<ClassOfStudents> cbClassOfStudents = CourseComboBox();
            cbClassOfStudents.getItems().addAll(model.getClasses());
            TextField tfTeacher = new TextField();
            ColorPicker colorPicker = new ColorPicker();
            Label lblColor = new Label("Pick Course Color:");

            switch (clickId) {
                case "edit":
                    Button btnChange = new Button("Change");
                    Button btnReset = new Button("Reset");
                    hbButtons.getChildren().addAll(btnChange, btnReset, btnCancel);

                    Course course = (Course) tableView.getSelectionModel().getSelectedItem();
                    int index = this.model.getCourses().indexOf(course);
                    tfTitle.setText(course.getTitle());

                    //TODO: THIS IS GONNA LOSE ITS SHIT ONCE CLASS BECOMES NULL
                    cbClassOfStudents.getSelectionModel().select(course.getClassOfStudents());

                    tfTeacher.setText(course.getTeacherList().getTeacherByIndex(0).getName());

                    btnChange.setOnAction(e -> {
                        ArrayList<Teacher> teacherList = new ArrayList<Teacher>();
                        String[] token = tfTeacher.getText().split(",");
                        for (String s : token) {
                            teacherList.add(new Teacher(s.trim()));
                        }
                        this.model.getCourses().set(index, new Course(tfTitle.getText(), teacherList, cbClassOfStudents.getValue()));
                    });
                    btnReset.setOnAction(e -> {
                        tfTitle.setText(course.getTitle());
                        cbClassOfStudents.getSelectionModel().select(course.getClassOfStudents());


                        tfTeacher.setText(course.getTeacherList().getTeacherByIndex(0).toString());

                    });
                    break;
                default://Adding case
                    Button btnAdd = new Button("Add");
                    Button btnClear = new Button("Clear");
                    hbButtons.getChildren().addAll(btnAdd, btnClear, btnCancel);
                    btnClear.setOnAction(e -> {
                        tfTitle.clear();
                        tfTeacher.clear();
                    });
                    btnAdd.setOnAction(e -> {
                        ArrayList<Teacher> teacherList = new ArrayList<Teacher>();
                        String[] token = tfTeacher.getText().split(",");
                        for (String s : token) {
                            teacherList.add(new Teacher(s.trim()));
                        }
                        Color color = colorPicker.getValue();
                        this.model.addCourse(new Course(tfTitle.getText(), teacherList, cbClassOfStudents.getValue()));
                        int lastCourse = this.model.getCourses().size() - 1;
                        this.model.getCourses().get(lastCourse).setHexColor(String.format("#%02X%02X%02X",
                                (int) (color.getRed() * 255),
                                (int) (color.getGreen() * 255),
                                (int) (color.getBlue() * 255)));
                        displayWindow.close();
                    });
                    break;
            }

            GridPane innerGrid = new GridPane();
            innerGrid.setAlignment(Pos.CENTER);
            innerGrid.add(hbButtons, 0, 0);

            Label lblTitle = new Label("Course Title:");
            Label lblClassName = new Label("Class Name:");
            Label lblTeacher = new Label("Teachers:");


            grid.add(lblTitle, 0, 0);
            grid.add(tfTitle, 1, 0);
            grid.add(lblClassName, 0, 1);
            grid.add(cbClassOfStudents, 1, 1);
            grid.add(lblTeacher, 0, 2);
            grid.add(tfTeacher, 1, 2);
            if(clickId!="edit"){
                grid.add(lblColor, 0, 3);
                grid.add(colorPicker, 1, 3);
                grid.add(innerGrid, 0, 4, 3, 1);
            }
            else{
                grid.add(innerGrid, 0, 3, 3, 1);
            }

            btnCancel.setOnAction(e -> displayWindow.close());
            Scene scene1 = new Scene(grid, 300, 300);
            displayWindow.setScene(scene1);
            displayWindow.showAndWait();
            reset();
        }
    }

    public ComboBox<ClassOfStudents> CourseComboBox() {
        ComboBox<ClassOfStudents> comboBox = new ComboBox<>();
        comboBox.setCellFactory(new Callback<ListView<ClassOfStudents>, ListCell<ClassOfStudents>>() {
            @Override
            public ListCell<ClassOfStudents> call(ListView<ClassOfStudents> param) {
                ListCell<ClassOfStudents> cell = new ListCell<ClassOfStudents>() {
                    @Override
                    protected void updateItem(ClassOfStudents item, boolean empty) {
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
        comboBox.setButtonCell(new ListCell<ClassOfStudents>() {
            @Override
            protected void updateItem(ClassOfStudents item, boolean empty) {
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

    public void onNewFilter() {
        tableView.getItems().clear();
        for (Course c : this.model.getCourses()) {
            String filter = textField.getText();
            if (filter != "" && (c.getTitle().toLowerCase().contains(filter.toLowerCase()) || c.getClassName().toLowerCase().contains(filter.toLowerCase()) || c.getTeacherName().toLowerCase().contains(filter.toLowerCase()))) {
                tableView.getItems().add(c);
            }

        }
    }
}
