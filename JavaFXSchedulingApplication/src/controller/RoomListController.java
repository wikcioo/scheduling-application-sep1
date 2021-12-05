package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.Room;
import model.RoomList;

import java.io.IOException;
import java.util.ArrayList;

public class RoomListController
{
  private Stage stage;
  private Scene scene;
  private Region root;
@FXML
private ListView list;
  private RoomList rooms;

  public void SwitchToScreen1(ActionEvent event) throws IOException
  {
    Region root = FXMLLoader.load(getClass().getClassLoader().getResource("view/CalendarView.fxml"));
    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.setWidth(root.getPrefWidth());
    stage.setHeight(root.getPrefHeight());
    stage.centerOnScreen();
    stage.show();
  }

  public void displayRooms()
  {
    list.getItems().clear();
    rooms = new RoomList();
    rooms.readRoomsListFromBinFile();
    ArrayList<Room> rooms = this.rooms.getRooms();
    for(Room room : rooms)
    {
      list.getItems().add(room);
    }
  }
}
