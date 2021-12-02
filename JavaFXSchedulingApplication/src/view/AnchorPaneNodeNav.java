package view;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class AnchorPaneNodeNav extends AnchorPane {

    public AnchorPaneNodeNav(Node... nodes) {
        super(nodes);
        this.setOnMouseClicked(event -> doSomething());
    }

    private void doSomething() {
        this.getStyleClass().add("nav");
    }

}
