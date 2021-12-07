package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import model.Model;
import view.ViewHandler;



public class MainMenuController extends ViewController
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

  public void OpenManageData()
  {
       viewHandler.openView("ManageDataView");
  }

  public void OpenCalendar()
  {
    viewHandler.openView("CalendarView");
  }

  public void OpenBooking()
  {
    viewHandler.openView("BookingView");
  }


}
