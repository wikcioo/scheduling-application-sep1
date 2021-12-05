import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import model.ModelManager;
import view.ViewHandler;

public class MyApplication extends Application {
    private ViewHandler view;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new ModelManager();
        view = new ViewHandler(model);
        view.start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        view.closeView();
    }
}