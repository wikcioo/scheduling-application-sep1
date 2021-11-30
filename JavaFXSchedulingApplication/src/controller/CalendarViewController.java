package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import model.Model;
import view.ViewHandler;

import java.awt.*;

public class CalendarViewController {
    @FXML
    private TextField inputField;

    private Region root;
    private Model model;
    private ViewHandler viewHandler;

    public void CreateCalendarViewController() {

    }

    public void init(ViewHandler viewHandler, Model model, Region root) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
    }

    public void reset() {
        inputField.setText("");
    }

    public Region getRoot() {
        return root;
    }

    @FXML
    private void onButtonClick() {

    }
}
