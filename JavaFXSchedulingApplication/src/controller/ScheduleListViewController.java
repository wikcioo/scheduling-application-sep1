package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import model.courses.ClassList;
import model.courses.ClassOfStudents;
import model.students.StudentList;
import utilities.ClassNameSorter;
import view.ViewHandler;

import java.io.IOException;
import java.util.ArrayList;

public class ScheduleListViewController extends ViewController{

  @FXML
  private ListView<ClassOfStudents> listView;
  @FXML
  private Button cancelButton;
  @FXML
  private Button confirmButton;

  private Region root;
  private Model model;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, Model model, Region root) {
    this.model = model;
    this.viewHandler = viewHandler;
    this.root = root;

//    ArrayList<ClassOfStudents> prop = new ArrayList<>();
//    prop.add(new ClassOfStudents("2Z", new StudentList()));
//    prop.add(new ClassOfStudents("1X", new StudentList()));
//    prop.add(new ClassOfStudents("2Y", new StudentList()));
//    prop.add(new ClassOfStudents("Danish 1", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
//    prop.add(new ClassOfStudents("1Z", new StudentList()));
    initListOfClasses();
    initConfirmButton();
  }


  private void initConfirmButton() {
    confirmButton.setDisable(true);
  }

  private void initListOfClasses() {

    //https://stackoverflow.com/questions/41070454/how-can-i-change-the-text-on-a-listview-that-is-holding-an-object-of-type-accoun?rq=1

    listView.setCellFactory(new Callback<ListView<ClassOfStudents>, ListCell<ClassOfStudents>>() {
      @Override
      public ListCell<ClassOfStudents> call(ListView<ClassOfStudents> param) {
        ListCell<ClassOfStudents> cell = new ListCell<ClassOfStudents>() {
          @Override
          protected void updateItem(ClassOfStudents item, boolean empty) {
            super.updateItem(item, empty);
            if(item != null) {
              setText(item.getName());
            } else {
              setText(null);
            }
          }
        };
        return cell;
      }
    });

    ObservableList<ClassOfStudents> observableClasses = FXCollections.observableArrayList(model.getClassList()
        .getClasses());
    listView.setItems(observableClasses);
//    listView.getItems().add(new ClassOfStudents("1Z",new StudentList()));
  }



  @FXML public void handleMouseClick(MouseEvent e) {
    model.getScheduleList().setCurrentSchedule(model.getScheduleList().getScheduleByClass(listView.getSelectionModel().getSelectedItem(),true));
    confirmButton.setDisable(false);
  }

  @FXML public void switchToCalendar()
  {
    viewHandler.openView("CalendarView");
  }

  @FXML public void switchToMenu()
  {
    viewHandler.openView("MainMenu");
  }

  public void reset() {
  }

  public Region getRoot() {
    return root;
  }
}