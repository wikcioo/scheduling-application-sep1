package controller;

import javafx.scene.layout.Region;
import model.Model;
import view.ViewHandler;

public abstract class ViewController {

    public abstract void init(ViewHandler viewHandler, Model model, Region root);

    public abstract void reset();

    public abstract Region getRoot();
}
