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
<<<<<<< Updated upstream
    private MainMenuController mainMenuController;
    private ManageDataController manageDataController;
    private BookingController bookingController;
    private ManageRoomsController manageRoomsController;
    private ManageCoursesController manageCoursesController;
    private ManageStudentsController manageStudentsController;
    private ManageTeachersController manageTeachersController;
    private ManageAssesController manageAssesController;
=======
    private StudentListViewController studentListViewController;
    private RoomListViewController roomListViewController;
    private CourseListViewController courseListViewController;
>>>>>>> Stashed changes

    public ViewHandler(Model model) {
        this.model = model;
        this.currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
<<<<<<< Updated upstream
        openView("MainMenu");
=======
        //openView("CalendarView");
        openView("StudentListView");
>>>>>>> Stashed changes
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
                root = loadMainMenuView();
                break;
            case "ManageDataView":
                root = loadManageDataView();
                break;
            case "BookingView":
                root = loadBookingView();
                break;
            case "ManageStudentsView":
                root = loadManageStudentsView();
                break;
            case "ManageTeachersView":
                root = loadManageTeachersView();
                break;
            case "ManageRoomsView":
                root = loadManageRoomsView();
                break;
            case "ManageCoursesView":
                root = loadManageCoursesView();
                break;
            case "ManageAssesView":
                root = loadManageAssesView();
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

    private Region loadMainMenuView() {
        if (mainMenuController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                Region root = loader.load();
                mainMenuController = loader.getController();
                mainMenuController.init(this, model, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mainMenuController.reset();
        }

        return mainMenuController.getRoot();
    }

    private Region loadManageDataView() {
        if (manageDataController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Manage.fxml"));
                Region root = loader.load();
                manageDataController = loader.getController();
                manageDataController.init(this, model, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            manageDataController.reset();
        }

        return manageDataController.getRoot();
    }

  private Region loadBookingView() {
    if (bookingController == null) {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Booking.fxml"));
        Region root = loader.load();
        bookingController = loader.getController();
        bookingController.init(this, model, root);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      bookingController.reset();
    }

    return bookingController.getRoot();
  }

    private Region loadManageStudentsView() {
        if (manageStudentsController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageStudents.fxml"));
                Region root = loader.load();
                manageStudentsController = loader.getController();
                manageStudentsController.init(this, model, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            manageStudentsController.reset();
        }

        return manageStudentsController.getRoot();
    }

    private Region loadManageTeachersView()
    {
        if (manageTeachersController == null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageTeachers.fxml"));
                Region root = loader.load();
                manageTeachersController = loader.getController();
                manageTeachersController.init(this, model, root);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else {
            manageTeachersController.reset();
        }
        return manageTeachersController.getRoot();
    }

    private Region loadManageRoomsView() {
        if (manageRoomsController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageRooms.fxml"));
                Region root = loader.load();
                manageRoomsController = loader.getController();
                manageRoomsController.init(this, model, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            manageRoomsController.reset();
        }

        return manageRoomsController.getRoot();
    }

    private Region loadManageCoursesView() {
        if (manageCoursesController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageCourses.fxml"));
                Region root = loader.load();
                manageCoursesController = loader.getController();
                manageCoursesController.init(this, model, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            manageCoursesController.reset();
        }

        return manageCoursesController.getRoot();
    }

    private Region loadManageAssesView() {
        if (manageAssesController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ManageAsses.fxml"));
                Region root = loader.load();
                manageAssesController = loader.getController();
                manageAssesController.init(this, model, root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            manageAssesController.reset();
        }

        return manageAssesController.getRoot();
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
