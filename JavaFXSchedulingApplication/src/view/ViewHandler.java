package view;

import controller.CalendarViewController;
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

    public ViewHandler(Model model) {
        this.model = model;
        this.currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView("CalendarView");
    }

    public void openView(String id) {
        Region root;
        switch (id) {
            case "CalendarView":
                root = loadCalendarView();
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
        primaryStage.show();
    }

    private Region loadCalendarView() {
        if (calendarViewController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CalendarView.fxml"));
                Region root = loader.load();
                calendarViewController = loader.getController();
                calendarViewController.init(this, model, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
             calendarViewController.reset();
        }

        return calendarViewController.getRoot();
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
