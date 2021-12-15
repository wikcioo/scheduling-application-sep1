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
    private ViewController viewController;

    public ViewHandler(Model model) {
        this.model = model;
        this.currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView("MainMenu");
    }

    public void openView(String id) {
        Region root;
        switch (id) {
            case "CalendarView":
                root = loadView("CalendarView.fxml", viewController);
                break;
            case "ScheduleListView":
                root = loadView("ScheduleList.fxml", viewController);
                break;

            case "StudentListView":
                root = loadView("StudentListView.fxml", viewController);
                break;
            case "RoomListView":
                root = loadView("RoomListView.fxml", viewController);
                break;
            case "CourseListView":
                root = loadView("CourseListView.fxml", viewController);
                break;
            case "CreateSemesterView":
                root = loadView("CreateSemesterView.fxml", viewController);
                break;
            case "MainMenu":
                root = loadView("MainMenu.fxml", viewController);
                break;
            case "ManageDataView":
                root = loadView("Manage.fxml", viewController);
                break;
            case "BookingView":
                root = loadView("Booking.fxml", viewController);
                break;
            case "ClassListView":
                root = loadView("ClassListView.fxml", viewController);
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

    public void closeView() {
        primaryStage.close();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void reassignModel(Model model) {
        this.model = model;
    }
}
