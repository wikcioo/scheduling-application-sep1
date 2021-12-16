package view;

import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.Model;

/**
 * This class manages the views. It keeps track of the current scene, the primary stage and the model.
 */
public class ViewHandler {
    private Scene currentScene;
    private Stage primaryStage;
    private Model model;
    private ViewController viewController;

    /**
     * Initializes the model and creates a new Scene with an empty Region.
     *
     * @param model the Model instance
     */
    public ViewHandler(Model model) {
        this.model = model;
        this.currentScene = new Scene(new Region());
    }

    /**
     * Assigns the Stage and opens default view ("MainMenu").
     *
     * @param primaryStage javaFX stage
     */
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView("MainMenu");
    }

    /**
     * Calls loadView method based on the provided id. Next, it sets scene and stage properties.
     *
     * @param id view id in String format
     */
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

    /**
     * This method loads the fxml file and initializes view controller. At the end it returns the Region that is
     * then assigned to the scene.
     *
     * @param fxmlFile the name of the fxml file to be loaded
     * @param viewController the view controller
     * @return javaFX Region data structure
     */
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

    /**
     * Closes the stage.
     */
    public void closeView() {
        primaryStage.close();
    }

    /**
     * @return the Stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * @param model the Model to be assigned to the current Model field
     */
    public void reassignModel(Model model) {
        this.model = model;
    }
}
