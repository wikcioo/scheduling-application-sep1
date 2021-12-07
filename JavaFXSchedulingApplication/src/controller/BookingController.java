package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import model.Model;
import view.ViewHandler;

public class BookingController extends ViewController
{

  private Region root;
  private Model model;
  private ViewHandler viewHandler;


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

  public void back(){
    viewHandler.openView("MainMenu");
  }
}
