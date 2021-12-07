package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import model.Model;
import view.ViewHandler;

public class ManageDataController extends ViewController
{
  private Region root;
  private Model model;
  private ViewHandler viewHandler;
@FXML private Pane leftBar;
@FXML private AnchorPane bg;

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

  public void openManageStudents()
  {
    viewHandler.openView("StudentListView");
  }

  public void openManageTeachers()
  {
    viewHandler.openView("ManageTeachersView");
  }

  public void openManageRooms()
  {
    viewHandler.openView("RoomListView");
  }

  public void openManageCourses()
  {
    viewHandler.openView("CourseListView");
  }

  public void openManageAsses()
  {
    viewHandler.openView("ManageAssesView");
  }

  public void openMainMenu()
  {
    viewHandler.openView("MainMenu");
  }


}
