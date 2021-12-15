package controller;

import javafx.scene.layout.Region;
import model.Model;
import view.ViewHandler;

/**
 * The purpose of this class is used to initialize the UI elements of Manage.fxml
 */
public class ManageDataController extends ViewController
{
  private Region root;
  private Model model;
  private ViewHandler viewHandler;

  /**
   * The purpose of this method is to lunch the window and initialize all
   * controls of the window
   *
   * @param viewHandler;
   * @param model;
   * @param root;
   */
  public void init(ViewHandler viewHandler, Model model, Region root)
  {
    this.model = model;
    this.viewHandler = viewHandler;
    this.root = root;
  }

  public void reset()
  {
  }

  /**
   * @return the root of the controller
   */
  public Region getRoot()
  {
    return root;
  }

  /**
   * The purpose of this method is to open up the RoomListView window
   */
  public void openManageRooms()
  {
    viewHandler.openView("RoomListView");
  }

  /**
   * The purpose of this method is to open up the CourseListView window
   */
  public void openManageCourses()
  {
    viewHandler.openView("CourseListView");
  }

  /**
   * The purpose of this method is to open up the ClassListView window
   */
  public void openManageClasses()
  {
    viewHandler.openView("ClassListView");
  }

  /**
   * The purpose of this method is to open up the ScheduleListView window
   */
  public void openMainMenu()
  {
    viewHandler.openView("ScheduleListView");
  }
}
