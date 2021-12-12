package controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;
import model.courses.Teacher;
import utilities.Logger;
import view.ViewHandler;

import java.util.Optional;

public class TeacherListViewController extends ViewController {
    @FXML
    TextField textField;
    @FXML
    TableView tableView;
    @FXML
    private Region root;
    private Model model;
    private ViewHandler viewHandler;
    private int courseIndex;

    public TeacherListViewController() {
        // called by FXMLLoader
    }

    public void init(ViewHandler viewHandler, Model model, Region root) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
        courseIndex = this.model.getCourseList().getCurrentSelectedCourse();
        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setSortable(false);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableView.getColumns().addAll(nameColumn);
        for (Teacher t : this.model.getCourseList().getCourses().get(courseIndex).getTeacherList().getTeacherList()) {
            tableView.getItems().add(t);
        }

    }

    public void reset() {
        tableView.getItems().clear();
        for (Teacher t : this.model.getCourseList().getCourses().get(courseIndex).getTeacherList().getTeacherList()) {
            tableView.getItems().add(t);
        }
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void onBackButtonClick() {
        viewHandler.openView("CourseListView");
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
            alert.setHeaderText("Confirm removing teacher");
            alert.setContentText("Are you sure? This action will remove the selected teacher.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                Teacher teacher = (Teacher) tableView.getSelectionModel().getSelectedItem();
                this.model.getCourseList().getCourses().get(courseIndex).getTeacherList().removeTeacher(teacher);
                tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    public void onImportFileButtonClick() {
        Logger.warn("Probably no importing here");
    }

    public void onClick(String clickId) {
        if (clickId.equals("edit") && tableView.getSelectionModel().getSelectedItem() == null) {
            //Throw alert for not selecting
        } else {
            Stage displayWindow = new Stage();
            displayWindow.initModality(Modality.APPLICATION_MODAL);
            displayWindow.setTitle("Display Teacher");
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

            switch (clickId) {
                case "edit":
                    Button btnChange = new Button("Change");
                    Button btnReset = new Button("Reset");
                    hbButtons.getChildren().addAll(btnChange, btnReset, btnCancel);
                    int index = tableView.getSelectionModel().getFocusedIndex();
                    Teacher teacher = this.model.getCourseList().getCourses().get(courseIndex).getTeacherList().getTeacherByIndex(index);
                    tfName.setText(teacher.getName());
                    btnChange.setOnAction(e -> {
                        this.model.getCourseList().getCourses().get(courseIndex).getTeacherList().getTeacherList().set(index, new Teacher(tfName.getText()));
                        displayWindow.close();
                    });
                    btnReset.setOnAction(e -> tfName.setText(teacher.getName()));
                    break;
                default://Adding case
                    Button btnAdd = new Button("Add");
                    Button btnClear = new Button("Clear");
                    hbButtons.getChildren().addAll(btnAdd, btnClear, btnCancel);
                    btnAdd.setOnAction(e -> {
                        this.model.getCourseList().getCourses().get(courseIndex).getTeacherList().getTeacherList().add(new Teacher(tfName.getText()));
                        displayWindow.close();
                    });
                    break;
            }

            GridPane innerGrid = new GridPane();
            innerGrid.setAlignment(Pos.CENTER);
            innerGrid.add(hbButtons, 0, 0);
            Label lblTitle = new Label("Teacher name:");
            grid.add(lblTitle, 0, 0);
            grid.add(tfName, 1, 0);
            grid.add(innerGrid, 0, 1, 3, 1);
            btnCancel.setOnAction(e -> displayWindow.close());
            Scene scene1 = new Scene(grid, 300, 300);
            displayWindow.setScene(scene1);
            displayWindow.showAndWait();
            reset();
        }
    }

    public void onViewDetailsClick(){
    }

    public void onNewFilter(){
        tableView.getItems().clear();
        for (Teacher t : this.model.getCourseList().getCourses().get(courseIndex).getTeacherList().getTeacherList()) {
            String filter = textField.getText();
            if(filter!=""&&(t.getName().toLowerCase().contains(filter.toLowerCase()))){
                tableView.getItems().add(t);
            }
        }
    }
}
