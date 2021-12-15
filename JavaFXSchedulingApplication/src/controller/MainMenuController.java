package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import model.Model;
import model.ModelSaverReader;
import model.rooms.Room;
import view.ViewHandler;

import java.util.Optional;
/**
 * The purpose of this class is used to initialize the UI elements of MainMenu.fxml
 */
public class MainMenuController extends ViewController {
    private Region root;
    private Model model;
    private ViewHandler viewHandler;
    @FXML Button bt1;
    @FXML
    Button bt2;
    @FXML
    Button bt3;

    /**
     * The purpose of this method is to lunch the window and initialize all
     * controls of the window
     *
     * @param viewHandler;
     * @param model;
     * @param root;
     */
    public void init(ViewHandler viewHandler, Model model, Region root) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
    }

    public void reset() {
    }

    /**
     * @return the root of the controller
     */
    public Region getRoot() {
        return root;
    }

    /**
     * The purpose of this method is to open up the ManageDataView window
     */
    @FXML public void OpenManageData() {
        viewHandler.openView("ManageDataView");
    }

    /**
     * The purpose of this method is to open up the ScheduleListView window
     */
    @FXML public void OpenCalendar() {
        viewHandler.openView("ScheduleListView");
    }

    /**
     * The purpose of this method is to open up the BookingView window
     */
    @FXML public void OpenBooking() {
        viewHandler.openView("BookingView");
    }

    /**
     * The purpose of this method is to open up the CreateSemesterView window
     */
    @FXML public void OpenCreateSemester() {
        viewHandler.openView("CreateSemesterView");
    }

    @FXML
    public void onSaveButtonClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Saving current state of the system");
        alert.setHeaderText("Confirm to save the current system");
        alert.setContentText("Are you sure you want to save the current system state");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ModelSaverReader.saveModel(this.model);
        }
    }

    @FXML
    public void onLoadButtonClick() {
        this.model = ModelSaverReader.readModel();
        if (this.model != null) {
            this.viewHandler.reassignModel(this.model);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully loaded current system state", ButtonType.OK);
            alert.setHeaderText("Information");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Couldn't load current system state");
            alert.show();
        }
    }
}
