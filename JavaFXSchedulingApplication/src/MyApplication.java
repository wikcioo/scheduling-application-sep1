import javafx.application.Application;
import javafx.stage.Stage;
import model.Model;
import model.ModelManager;
import model.calendar.Schedule;
import model.courses.ClassOfStudents;
import model.students.StudentList;
import view.ViewHandler;

import java.time.LocalDate;

public class MyApplication extends Application {
    private ViewHandler view;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new ModelManager();

        ClassOfStudents class1 = new ClassOfStudents("1Z",new StudentList());
        model.getClassList().addClass(class1);
        model.getClassList().addClass(new ClassOfStudents("Rougue zero",new StudentList()));
        model.getClassList().addClass(new ClassOfStudents("Danish one",new StudentList()));
        model.getClassList().addClass(new ClassOfStudents("4Z",new StudentList()));
        model.getClassList().addClass(new ClassOfStudents("5Z",new StudentList()));
        model.getClassList().addClass(new ClassOfStudents("6Z",new StudentList()));

        Schedule schedule1 = new Schedule(
            LocalDate.of(2021, 8, 30),
            LocalDate.of(2021, 12, 17),
            class1);

        model.getScheduleList().addSchedule(schedule1);

        view = new ViewHandler(model);
        view.start(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        view.closeView();
    }
}