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
import model.students.Student;
import model.students.StudentList;
import view.ViewHandler;

import java.io.File;


public class StudentListViewController extends ViewController{
    @FXML
    TableView tableView;
    @FXML
    private Region root;
    private Model model;
    private ViewHandler viewHandler;

    public StudentListViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, Model model, Region root) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn surnameColumn = new TableColumn("ID");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn _classColumn = new TableColumn("_class");
        _classColumn.setCellValueFactory(new PropertyValueFactory<>("_class"));
        TableColumn semesterColumn = new TableColumn("semester");
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));

        tableView.getColumns().addAll(nameColumn, surnameColumn, _classColumn, semesterColumn);
        for (Student s : this.model.getStudentList().getStudentList()) {
            tableView.getItems().add(s);
        }

    }

    public void reset() {
        tableView.getItems().clear();
        for (Student s : this.model.getStudentList().getStudentList()) {
            tableView.getItems().add(s);
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
            Student student = (Student) tableView.getSelectionModel().getSelectedItem();
            this.model.getStudentList().removeStudent(student);
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    public void onImportFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        this.model.readStudentFromTXTFile(file);
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
            TextField tfName = new TextField();
            TextField tfID = new TextField();
            TextField tfClass = new TextField();
            TextField tfSmstr = new TextField();
            switch (clickId) {
                case "edit":
                    Button btnChange = new Button("Change");
                    Button btnReset = new Button("Reset");
                    hbButtons.getChildren().addAll(btnChange, btnReset, btnCancel);
                    int index = tableView.getSelectionModel().getFocusedIndex();
                    Student student = this.model.getStudentList().getStudent(index);
                    tfName.setText(student.getName());
                    tfID.setText(Integer.toString(student.getId()));
                    tfClass.setText(student.get_class());
                    tfSmstr.setText(Integer.toString(student.getSemester()));
                    btnChange.setOnAction(e -> {
                        this.model.getStudentList().getStudentList().set(index, new Student(tfName.getText(), Integer.parseInt(tfID.getText()), tfClass.getText(), Integer.parseInt(tfSmstr.getText())));
                        displayWindow.close();
                    });
                    btnReset.setOnAction(e -> {
                        tfName.setText(student.getName());
                        tfID.setText(Integer.toString(student.getId()));
                        tfClass.setText(student.get_class());
                        tfSmstr.setText(Integer.toString(student.getSemester()));
                    });
                    break;
                default://Adding case
                    Button btnAdd = new Button("Add");
                    Button btnClear = new Button("Clear");
                    hbButtons.getChildren().addAll(btnAdd, btnClear, btnCancel);
                    btnAdd.setOnAction(e -> {
                        this.model.getStudentList().addStudent(new Student(tfName.getText(), Integer.parseInt(tfID.getText()), tfClass.getText(), Integer.parseInt(tfSmstr.getText())));
                        displayWindow.close();
                    });
                    break;
            }

            GridPane innerGrid = new GridPane();
            innerGrid.setAlignment(Pos.CENTER);
            innerGrid.add(hbButtons, 0, 0);

            Label lblName = new Label("Student name:");
            Label lblID = new Label("ID:");
            Label lblClass = new Label("Class:");
            Label lblSmstr = new Label("Semester:");

            grid.add(lblName, 0, 0);
            grid.add(tfName, 1, 0);
            grid.add(lblID, 0, 1);
            grid.add(tfID, 1, 1);
            grid.add(lblClass, 0, 2);
            grid.add(tfClass, 1, 2);
            grid.add(lblSmstr, 0, 3);
            grid.add(tfSmstr, 1, 3);
            grid.add(innerGrid, 0, 4, 4, 1);
            btnCancel.setOnAction(e -> displayWindow.close());
            Scene scene1 = new Scene(grid, 300, 300);
            displayWindow.setScene(scene1);
            displayWindow.showAndWait();
            reset();
        }
    }
}
