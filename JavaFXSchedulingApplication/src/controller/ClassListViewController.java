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
import model.students.Student;
import model.students.StudentList;
import view.ViewHandler;

import java.io.File;
import java.util.Optional;

public class ClassListViewController extends ViewController {
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
    @FXML
    Button detailsButton;
    @FXML
    private Region root;
    private Model model;
    private ViewHandler viewHandler;

    public ClassListViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, Model model, Region root) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;

        TableColumn _classColumn = new TableColumn("Class name");
        _classColumn.setSortable(false);
        _classColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableView.getColumns().addAll( _classColumn);
        for (ClassOfStudents c : this.model.getClasses()) {
            tableView.getItems().add(c);
        }
        setDisableCellSpecificButtons(true);
    }

    public void reset() {
        tableView.getItems().clear();
        for (ClassOfStudents c : this.model.getClasses()) {
            tableView.getItems().add(c);
        }
    }

    @FXML
    public void onChosenCell() {
        setDisableCellSpecificButtons(false);
    }

    public void setDisableCellSpecificButtons(boolean disable) {
        editButton.setDisable(disable);
        removeButton.setDisable(disable);
        detailsButton.setDisable(disable);
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
            alert.setHeaderText("Confirm removing class");
            alert.setContentText("Are you sure? This action will remove the selected class.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                ClassOfStudents classOfStudents = (ClassOfStudents) tableView.getSelectionModel().getSelectedItem();
                this.model.getClassList().removeClass(classOfStudents);
                tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    public void onImportFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        this.model.getStudentList().readStudentFromTXTFile(file);
        reset();
    }


    public void onClick(String clickId) {
        if (clickId.equals("edit") && tableView.getSelectionModel().getSelectedItem() == null) {
            //Throw alert for not selecting
        } else {
            setDisableCellSpecificButtons(true);
            Stage displayWindow = new Stage();
            displayWindow.initModality(Modality.APPLICATION_MODAL);
            displayWindow.setTitle("Display Class");
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
            TextField tfClassName = new TextField();



            switch (clickId) {
                case "edit":
                    Button btnChange = new Button("Change");
                    Button btnReset = new Button("Reset");
                    hbButtons.getChildren().addAll(btnChange, btnReset, btnCancel);
                    int index = tableView.getSelectionModel().getFocusedIndex();
                    ClassOfStudents classOfStudents = this.model.getClasses().get(index);
                    tfClassName.setText(classOfStudents.getName());

                    btnChange.setOnAction(e -> {
                        this.model.getClasses().set(index,new ClassOfStudents(tfClassName.getText(),classOfStudents.getStudentList()));
                        displayWindow.close();
                    });
                    btnReset.setOnAction(e -> tfClassName.setText(classOfStudents.getName()));
                    break;
                default://Adding case
                    Button btnAdd = new Button("Add");
                    Button btnClear = new Button("Clear");
                    hbButtons.getChildren().addAll(btnAdd, btnClear, btnCancel);
                    btnAdd.setOnAction(e -> {
                        this.model.getClasses().add(new ClassOfStudents(tfClassName.getText(),new StudentList()));
                        displayWindow.close();
                    });
                    break;
            }

            GridPane innerGrid = new GridPane();
            innerGrid.setAlignment(Pos.CENTER);
            innerGrid.add(hbButtons, 0, 0);
            Label lblClassName = new Label("Class Name:");
            grid.add(lblClassName, 0, 0);
            grid.add(tfClassName, 1, 0);
            grid.add(innerGrid, 0, 1, 3, 1);
            btnCancel.setOnAction(e -> displayWindow.close());
            Scene scene1 = new Scene(grid, 300, 300);
            displayWindow.setScene(scene1);
            displayWindow.showAndWait();
            reset();
        }
    }

    public void onViewDetailsClick(){
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            this.model.getClassList().setCurrentlySelectedClass(tableView.getSelectionModel().getFocusedIndex());
            this.viewHandler.openView("StudentListView");
        }
    }

    public void onNewFilter(){
        tableView.getItems().clear();
        for (ClassOfStudents c : this.model.getClasses()) {
            String filter = textField.getText();
            if(filter!=""&&(c.getName().toLowerCase().contains(filter.toLowerCase()))){
                tableView.getItems().add(c);
            }

        }
    }
}
