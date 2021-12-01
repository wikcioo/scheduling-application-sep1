package view;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class AnchorPaneNode extends AnchorPane {
    private String course;

    public AnchorPaneNode(Node... nodes) {
        super(nodes);
        this.course = course;
        this.setOnMouseClicked(e -> System.out.println("This pane's info is: " + toString()));
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "LessonNode{" +
                "string='" + course + '\'' +
                '}';
    }
}
