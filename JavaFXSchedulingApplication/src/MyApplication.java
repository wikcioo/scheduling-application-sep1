import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import model.ModelManager;
import model.courses.ClassOfStudents;
import model.students.StudentList;
import view.ViewHandler;

public class MyApplication extends Application {
    private ViewHandler view;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new ModelManager();

        ClassOfStudents class1 = new ClassOfStudents("1Z", new StudentList());
        model.addClass(class1);
        model.addClass(new ClassOfStudents("4Z", new StudentList()));
        model.addClass(new ClassOfStudents("5Z", new StudentList()));
        model.addClass(new ClassOfStudents("6Z", new StudentList()));

        view = new ViewHandler(model);
        view.start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        view.closeView();
    }
}