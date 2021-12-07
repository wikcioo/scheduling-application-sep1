package view;

import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.Model;

public class ViewHandler {
    private Scene currentScene;
    private Stage primaryStage;
    private Model model;
    private CalendarViewController calendarViewController;
    private StudentListViewController studentListViewController;
    private RoomListViewController roomListViewController;
    private CourseListViewController courseListViewController;


    private MainMenuController mainMenuController;
    private ManageDataController manageDataController;
    private BookingController bookingController;
    private ManageTeachersController manageTeachersController;
    private ManageClassesController manageClassesController;
    //Manage classes




    public ViewHandler(Model model) {
        this.model = model;
        this.currentScene = new Scene(new Region());
    }
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView("MainMenu");
        //openView("CalendarView");
        //openView("StudentListView");
    }

    public void openView(String id) {
        Region root;
        switch (id) {
            case "CalendarView":
                root = loadView("CalendarView.fxml", calendarViewController);
                break;
            case "StudentListView":
                root = loadView("StudentListView.fxml", studentListViewController);
                break;
            case "RoomListView":
                root = loadView("RoomListView.fxml",roomListViewController);
                break;
            case "CourseListView":
                root = loadView("CourseListView.fxml",courseListViewController);
                break;
            case "MainMenu":
                root = loadView("MainMenu.fxml",mainMenuController);
                break;
            case "ManageDataView":
                root = loadView("Manage.fxml",manageDataController);
                break;
            case "BookingView":
                root = loadView("Booking.fxml", bookingController);
                break;
            case "ManageTeachersView":
                root = loadView("ManageTeachers.fxml", manageTeachersController);
                break;
            case "ManageClassesView":
                root = loadView("ManageClasses.fxml", manageClassesController);
                break;
            default:
                root = new Region();
        }

        currentScene.setRoot(root);
        currentScene.getStylesheets().add("styles/styles.css");
        String title = "";
        if (root.getUserData() != null) {
            title += root.getUserData();
        }

        primaryStage.setTitle(title);
        primaryStage.setScene(currentScene);
        primaryStage.setWidth(root.getPrefWidth());
        primaryStage.setHeight(root.getPrefHeight());
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Region loadView(String fxmlFile, ViewController viewController) {
        if (viewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(fxmlFile));
                Region root = loader.load();
                viewController = loader.getController();
                viewController.init(this, model, root);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            viewController.reset();
        }
        return viewController.getRoot();
    }



    private Region loadAddLessonView() {
        return null;
    }

    public void closeView() {
        this.model.saveSemesterData();
        primaryStage.close();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
