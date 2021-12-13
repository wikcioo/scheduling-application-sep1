package controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;
import model.rooms.Room;
import view.ViewHandler;

import java.io.File;
import java.util.Optional;


public class RoomListViewController extends ViewController {
    @FXML
    TextField textField;
    @FXML
    TableView tableView;
    @FXML
    private Region root;
    private Model model;
    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, Model model, Region root) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;

        TableColumn nameColumn = new TableColumn("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn capacityColumn = new TableColumn("Capacity");
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        TableColumn mergeColumn = new TableColumn("Merge with");
        mergeColumn.setCellValueFactory(new PropertyValueFactory<>("mergeWith"));

        tableView.getColumns().addAll(nameColumn, capacityColumn, mergeColumn);
        for (Room r : this.model.getRoomList().getRooms()) {
            tableView.getItems().add(r);
        }
    }

    public void reset() {
        tableView.getItems().clear();
        for (Room r : this.model.getRoomList().getRooms()) {
            tableView.getItems().add(r);
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
            alert.setHeaderText("Confirm removing room");
            alert.setContentText("Are you sure? This action will remove the selected room.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Room room = (Room) tableView.getSelectionModel().getSelectedItem();
                this.model.getRoomList().removeRoom(room);
                tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    public void onImportFileButtonClick() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        this.model.getRoomList().readRoomsFromTXTFile(file);
        reset();
    }

    public void onNewFilter() {
        String filter = textField.getText();
        if (filter != "") {
            tableView.getItems().clear();
            for (Room r : this.model.getRoomList().getRooms()) {
                if (r.getName().toLowerCase().contains(filter.toLowerCase()) || r.getMergeWith().toLowerCase().contains(filter.toLowerCase()) || Integer.toString(r.getCapacity()).contains(filter)) {
                    tableView.getItems().add(r);
                }
            }
        }
    }

    public void onClick(String clickId) {
        if (clickId.equals("edit") && tableView.getSelectionModel().getSelectedItem() == null) {
            //Throw alert for not selecting
        } else {
            Stage displayWindow = new Stage();
            displayWindow.initModality(Modality.APPLICATION_MODAL);
            displayWindow.setTitle("Display room");
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
            TextField tfCapacity = new TextField();
            TextField tfMerge = new TextField();
            switch (clickId) {
                case "edit":
                    Button btnChange = new Button("Change");
                    Button btnReset = new Button("Reset");
                    hbButtons.getChildren().addAll(btnChange, btnReset, btnCancel);

                    Room room = (Room) tableView.getSelectionModel().getSelectedItem();
                    int index = this.model.getRoomList().getRooms().indexOf(room);
                    tfName.setText(room.getName());
                    tfCapacity.setText(Integer.toString(room.getCapacity()));
                    tfMerge.setText(room.getMergeWith());
                    btnChange.setOnAction(e -> {
                        this.model.getRoomList().getRooms().set(index, new Room(tfName.getText(), Integer.parseInt(tfCapacity.getText()), tfMerge.getText()));
                        displayWindow.close();
                    });
                    btnReset.setOnAction(e -> {
                        tfName.setText(room.getName());
                        tfCapacity.setText(Integer.toString(room.getCapacity()));
                        tfMerge.setText(room.getMergeWith());
                    });
                    break;
                default://Adding case
                    Button btnAdd = new Button("Add");
                    Button btnClear = new Button("Clear");
                    hbButtons.getChildren().addAll(btnAdd, btnClear, btnCancel);
                    btnClear.setOnAction(e -> {
                        tfCapacity.setText("");
                        tfMerge.clear();
                        tfName.clear();
                    });
                    btnAdd.setOnAction(e -> {
                        this.model.getRoomList().getRooms().add(new Room(tfName.getText(), Integer.parseInt(tfCapacity.getText()), tfMerge.getText()));
                        displayWindow.close();
                    });
                    break;
            }

            GridPane innerGrid = new GridPane();
            innerGrid.setAlignment(Pos.CENTER);
            innerGrid.add(hbButtons, 0, 0);

            Label lblName = new Label("Room name:");
            Label lblID = new Label("Capacity:");
            Label lblClass = new Label("Merge with:");

            grid.add(lblName, 0, 0);
            grid.add(tfName, 1, 0);
            grid.add(lblID, 0, 1);
            grid.add(tfCapacity, 1, 1);
            grid.add(lblClass, 0, 2);
            grid.add(tfMerge, 1, 2);
            grid.add(innerGrid, 0, 3, 3, 1);
            btnCancel.setOnAction(e -> displayWindow.close());
            Scene scene1 = new Scene(grid, 300, 300);
            displayWindow.setScene(scene1);
            displayWindow.showAndWait();
            reset();
        }
    }
}
