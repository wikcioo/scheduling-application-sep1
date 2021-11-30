import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import model.ModelManager;
import view.ViewHandler;

public class MyApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new ModelManager();
        ViewHandler view = new ViewHandler(model);
        view.start(primaryStage);
    }
}