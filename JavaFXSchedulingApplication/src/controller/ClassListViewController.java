package controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;
import model.courses.ClassOfStudents;
import model.students.StudentList;
import view.ViewHandler;

import java.io.File;
import java.util.Optional;

/**
 * The purpose of this class is used to initialize the UI elements of ClassListView.fxml
 */
public class ClassListViewController extends ViewController
{
  @FXML TextField textField;
  @FXML TableView tableView;
  @FXML Button editButton;
  @FXML Button addButton;
  @FXML Button removeButton;
  @FXML Button detailsButton;
  @FXML private Region root;
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

    TableColumn _classColumn = new TableColumn("Class name");
    _classColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    tableView.getColumns().addAll(_classColumn);
    for (ClassOfStudents c : this.model.getClasses())
    {
      tableView.getItems().add(c);
    }
    setDisableCellSpecificButtons(true);
  }

  /**
   * The purpose of this method is to clear all the classes from table and
   * repopulate the table with the saved classes
   */
  public void reset()
  {
    tableView.getItems().clear();
    for (ClassOfStudents c : this.model.getClasses())
    {
      tableView.getItems().add(c);
    }
  }

  /**
   * The purpose of this method is to check if an item is selected
   */
  @FXML public void onChosenCell()
  {
    if (tableView.getSelectionModel().getSelectedItem() != null)
      setDisableCellSpecificButtons(false);
  }

  /**
   * The purpose of these methods is to disable the buttons if disable is true
   * and let them enable if it false
   *
   * @param disable the status of the buttons
   */
  public void setDisableCellSpecificButtons(boolean disable)
  {
    editButton.setDisable(disable);
    removeButton.setDisable(disable);
    detailsButton.setDisable(disable);
  }

  /**
   * @return the roog of the controller
   */
  public Region getRoot()
  {
    return root;
  }

  /**
   * The purpose of this method is to open up the Manage Data View window
   */
  @FXML private void onBackButtonClick()
  {
    viewHandler.openView("ManageDataView");
  }

  /**
   * The purpose of this button is to open up a new window where u can add a new class
   * by inserting its name
   */
  @FXML private void onAddButtonClick()
  {
    onClick("add");
  }

  /**
   * The purpose of this button is to open up a new window where u can edit the class
   */
  @FXML private void onEditButtonClick()
  {
    onClick("edit");
  }

  /**
   * The purpose of this method is to remove a selected class
   * After u press remove a pop-up will show to confirm the decision
   */
  @FXML private void onRemoveButtonClick()
  {
    if (tableView.getSelectionModel().getSelectedItem() != null)
    {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirm removing");
      alert.setHeaderText("Confirm removing class");
      alert.setContentText(
          "Are you sure? This action will remove the selected class.");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK)
      {
        ClassOfStudents classOfStudents = (ClassOfStudents) tableView.getSelectionModel()
            .getSelectedItem();
        this.model.removeClass(classOfStudents);
        tableView.getItems()
            .remove(tableView.getSelectionModel().getSelectedItem());
      }
    }
  }

  /**
   * The purpose of this method is to choose a file from your computer to import
   */
  @FXML public void onImportFileButtonClick()
  {
    FileChooser fileChooser = new FileChooser();
    File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
    this.model.getClasses().get(this.model.getCurrentlySelectedClass())
        .getStudentList().readStudentFromTXTFile(file);
    reset();
  }

  /**
   * The purpose of this method is to assure the functionality of all the buttons
   * Once a button is clicked, depending on its id, different actions will occur
   * When pressed, some buttons will display a pop-up window where other buttons will have
   * their own functionality
   *
   * @param clickId the id of the button
   */
  public void onClick(String clickId)
  {
    if (clickId.equals("edit")
        && tableView.getSelectionModel().getSelectedItem() == null)
    {
      //Throw alert for not selecting
    }
    else
    {
      setDisableCellSpecificButtons(true);
      Stage displayWindow = new Stage();
      displayWindow.initModality(Modality.APPLICATION_MODAL);
      displayWindow.setTitle("Display Class");
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(12);
      ColumnConstraints column1 = new ColumnConstraints();
      column1.setHalignment(HPos.RIGHT);
      grid.getColumnConstraints().add(column1);
      ColumnConstraints column2 = new ColumnConstraints();
      column2.setHalignment(HPos.LEFT);
      grid.getColumnConstraints().add(column2);
      HBox hbButtons = new HBox();
      hbButtons.setSpacing(10.0);
      grid.setAlignment(Pos.CENTER);
      Button btnCancel = new Button("Cancel");
      TextField tfClassName = new TextField();

      switch (clickId)
      {
        case "edit":
          Button btnChange = new Button("Change");
          Button btnReset = new Button("Reset");
          hbButtons.getChildren().addAll(btnChange, btnReset, btnCancel);

          ClassOfStudents classOfStudents = (ClassOfStudents) tableView.getSelectionModel()
              .getSelectedItem();
          int index = this.model.getClasses().indexOf(classOfStudents);
          tfClassName.setText(classOfStudents.getName());

          btnChange.setOnAction(e -> {
            this.model.getClasses().set(index,
                new ClassOfStudents(tfClassName.getText(),
                    classOfStudents.getStudentList()));
            displayWindow.close();
          });
          btnReset.setOnAction(
              e -> tfClassName.setText(classOfStudents.getName()));
          break;
        default://Adding case
          Button btnAdd = new Button("Add");
          Button btnClear = new Button("Clear");
          hbButtons.getChildren().addAll(btnAdd, btnClear, btnCancel);
          btnClear.setOnAction(e -> tfClassName.clear());
          btnAdd.setOnAction(e -> {
            this.model.getClasses().add(
                new ClassOfStudents(tfClassName.getText(), new StudentList()));
            displayWindow.close();
          });
          break;
      }

      GridPane innerGrid = new GridPane();
      innerGrid.setAlignment(Pos.CENTER);
      innerGrid.add(hbButtons, 0, 0);
      Label lblClassName = new Label("Class Name:");
      grid.add(lblClassName, 0, 0);
      grid.add(tfClassName, 1, 0);
      grid.add(innerGrid, 0, 1, 3, 1);
      btnCancel.setOnAction(e -> displayWindow.close());
      Scene scene1 = new Scene(grid, 300, 300);
      displayWindow.setScene(scene1);
      displayWindow.showAndWait();
      reset();
    }
  }

  /**
   * The purpose of this method is to go to the student list of a selected class
   */
  public void onViewDetailsClick()
  {
    if (tableView.getSelectionModel().getSelectedItem() != null)
    {
      ClassOfStudents classOfStudents = (ClassOfStudents) tableView.getSelectionModel()
          .getSelectedItem();
      this.model.setCurrentlySelectedClass(
          this.model.getClasses().indexOf(classOfStudents));
      this.viewHandler.openView("StudentListView");
    }
  }

  /**
   * The purpose of this method is to filter the classes
   */
  public void onNewFilter()
  {
    tableView.getItems().clear();
    for (ClassOfStudents c : this.model.getClasses())
    {
      String filter = textField.getText();
      setDisableCellSpecificButtons(true);
      if (filter != "" && (c.getName().toLowerCase()
          .contains(filter.toLowerCase())))
      {
        tableView.getItems().add(c);
      }

    }
  }
}
