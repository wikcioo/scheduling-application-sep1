package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Region;
import model.Model;
import view.ViewHandler;

import java.time.LocalDate;

/**
 * The purpose of this class is used to initialize the UI elements of CreateSemesterView.fxml
 */
public class CreateSemesterViewController extends ViewController
{
  @FXML Button confirmButton;
  @FXML Button cancelButton;

  @FXML DatePicker startDatePicker;
  @FXML DatePicker endDatePicker;

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

    initConfirmButton();
  }

  /**
   * The purpose of this method is to initialize the confirmation button
   */
  private void initConfirmButton()
  {
    confirmButton.setDisable(true);
  }

  /**
   * The purpose of this method is select a date for the start and the end of the
   * semester and create it
   */
  @FXML public void onDateClick()
  {
    LocalDate startDate = startDatePicker.getValue();
    LocalDate endDate = endDatePicker.getValue();
    if (startDate != null && endDate != null && startDate.isBefore(endDate))
    {
      confirmButton.setDisable(false);
      model.initSemester(startDate, endDate);
    }
    else
    {
      confirmButton.setDisable(true);
    }

  }

  /**
   * The purpose of this method is to open up the Main Menu window
   */
  @FXML public void confirm()
  {
    viewHandler.openView("MainMenu");
  }

  /**
   * The purpose of this method is to open up the Main Menu window
   */
  @FXML public void cancel()
  {
    viewHandler.openView("MainMenu");
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
}
