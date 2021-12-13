package controller;

import javafx.scene.layout.Region;
import model.Model;
import view.ViewHandler;

public class ManageDataController extends ViewController {
    private Region root;
    private Model model;
    private ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, Model model, Region root) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
    }

    public void reset() {
    }

    public Region getRoot() {
        return root;
    }

    public void openManageRooms() {
        viewHandler.openView("RoomListView");
    }

    public void openManageCourses() {
        viewHandler.openView("CourseListView");
    }

    public void openManageClasses() {
        viewHandler.openView("ClassListView");
    }

    public void openMainMenu() {
        viewHandler.openView("MainMenu");
    }
}
