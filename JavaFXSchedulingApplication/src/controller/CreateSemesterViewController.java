package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Region;
import model.Model;
import view.ViewHandler;

import java.time.LocalDate;

public class CreateSemesterViewController extends ViewController
{
  @FXML
  Button confirmButton;
  @FXML
  Button cancelButton;

  @FXML
  DatePicker startDatePicker;
  @FXML
  DatePicker endDatePicker;

  private Region root;
  private Model model;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, Model model, Region root) {
    this.model = model;
    this.viewHandler = viewHandler;
    this.root = root;

    initConfirmButton();
  }

  private void initConfirmButton() {
    confirmButton.setDisable(true);
  }

  @FXML public void onDateClick() {
    LocalDate startDate = startDatePicker.getValue();
    LocalDate endDate = endDatePicker.getValue();
    if(startDate != null && endDate != null && startDate.isBefore(endDate)) {
      confirmButton.setDisable(false);
      model.initSemester(startDate,endDate);
    }
    else {
      confirmButton.setDisable(true);
    }

  }

  @FXML
  public void confirm() {
    viewHandler.openView("MainMenu");
  }

  @FXML
  public void cancel() {
    viewHandler.openView("MainMenu");
  }

  public void reset() {
  }

  public Region getRoot() {
    return root;
  }
}
