package controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;
import model.calendar.Lesson;
import model.rooms.BookingTime;
import model.rooms.Room;
import view.ViewHandler;

import java.util.ArrayList;

public class BookingController extends ViewController
{
  @FXML TableView tableView;
  @FXML
  private Region root;
  private Model model;
  private ViewHandler viewHandler;


  public void init(ViewHandler viewHandler, Model model, Region root) {
    this.model = model;
    this.viewHandler = viewHandler;
    this.root = root;

    TableColumn nameColumn = new TableColumn("Course");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("course"));
    TableColumn capacityColumn = new TableColumn("Date");
    capacityColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    TableColumn mergeColumn = new TableColumn("StartTime");
    mergeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
    TableColumn endColumn = new TableColumn("EndTime");
    endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
    TableColumn roomColumn = new TableColumn("Room");
    roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));

    tableView.getColumns().addAll(nameColumn, capacityColumn, mergeColumn, endColumn, roomColumn);
    for (Lesson r : this.model.getScheduleList().getAllLessons()) {
      tableView.getItems().add(r);
      System.out.println(r);
    }


  }

  public void reset() {
    tableView.getItems().clear();
    for (Lesson r : this.model.getScheduleList().getAllLessons()) {
      tableView.getItems().add(r);
    }
  }

  public Region getRoot() {
    return root;
  }

  public void back(){
    viewHandler.openView("MainMenu");
  }



  @FXML
  private void onBookButtonClick() {
    onClick("BookRoom");
  }
  @FXML
  private void onUnbookButtonClick() {
    onClick("UnBookRoom");
  }

  public void onClick(String clickId) {
    if (clickId.equals("BookRoom") && tableView.getSelectionModel().getSelectedItem() == null) {
      //Throw alert for not selecting
    }
    else if(clickId.equals("UnBookRoom") && tableView.getSelectionModel().getSelectedItem() != null)
    {

      int index = tableView.getSelectionModel().getFocusedIndex();
      Lesson lesson = this.model.getScheduleList().getAllLessons().get(index);
      BookingTime book = new BookingTime(lesson.getDate(),lesson.getStart(),lesson.getEnd());
      lesson.getRoom().unBook(book);
      lesson.setRoom(null);
      reset();
      return;
    }
    else {
      Lesson lesson = null;
      Stage displayWindow = new Stage();
      displayWindow.setHeight(400);
      displayWindow.setWidth(400);
      displayWindow.setResizable(false);
      displayWindow.initModality(Modality.APPLICATION_MODAL);
      displayWindow.setTitle("Display Lessons");
      GridPane grid = new GridPane();
      grid.setHgap(10);
      grid.setVgap(12);
      ColumnConstraints column1 = new ColumnConstraints();
      column1.setHalignment(HPos.LEFT);
      grid.getColumnConstraints().add(column1);
      ColumnConstraints column2 = new ColumnConstraints();
      column2.setHalignment(HPos.LEFT);
      grid.getColumnConstraints().add(column2);
      HBox hbButtons = new HBox();
      hbButtons.setSpacing(10.0);
      grid.setAlignment(Pos.CENTER);
      Button btnCancel = new Button("Cancel");
      Button btnBook = new Button("Book");
      hbButtons.getChildren().addAll(btnBook, btnCancel);
      switch (clickId) {
        case "BookRoom":
          int index = tableView.getSelectionModel().getFocusedIndex();
          ArrayList<Lesson> allLessons = this.model.getScheduleList().getAllLessons();
          lesson = allLessons.get(index);
          break;
        default:
          break;
      }

      GridPane innerGrid = new GridPane();
      innerGrid.setAlignment(Pos.CENTER);


      Label lblName = new Label("Select room to book:");

      TableView t = new TableView();
      TableColumn nameColumn = new TableColumn("Name");
      nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
      TableColumn capacityColumn = new TableColumn("Capacity");
      capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
      TableColumn mergeColumn = new TableColumn("Merge with");
      mergeColumn.setCellValueFactory(new PropertyValueFactory<>("mergeWith"));

      t.setPrefHeight(200);
      t.getColumns().addAll(nameColumn, capacityColumn, mergeColumn);
      BookingTime book = new BookingTime(lesson.getDate(),lesson.getStart(),lesson.getEnd());
      for (Room r : this.model.getRoomList().getAvailableRoomsAt(book))
      {
        t.getItems().add(r);
      }

      innerGrid.add(hbButtons, 0, 20);
      grid.add(lblName, 0, 1);
      grid.add(t,0, 2);
      grid.add(innerGrid, 0, 3, 3, 1);
      btnCancel.setOnAction(e -> displayWindow.close());
      Lesson finalLesson = lesson;

      btnBook.setOnAction(e ->{
        int index2 = t.getSelectionModel().getFocusedIndex();
        if(finalLesson.getRoom() == null)
        {
          finalLesson.setRoom(
              this.model.getRoomList().getAvailableRoomsAt(book).get(index2));
          this.model.getRoomList().getAvailableRoomsAt(book).get(index2).Book(book);
          displayWindow.close();
          System.out.println(model.getRoomList().getRooms());
        }

      });


      Scene scene1 = new Scene(grid, 300, 300);
      displayWindow.setScene(scene1);
      displayWindow.showAndWait();
      reset();
    }
  }
}
