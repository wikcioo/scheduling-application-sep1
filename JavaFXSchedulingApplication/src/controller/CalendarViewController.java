package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.*;
import utilities.Util;
import view.CalendarView;
import view.NavCalendarView;
import view.ViewHandler;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CalendarViewController {
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Text date1;
    @FXML
    private Text date2;
    @FXML
    private TextField course;
    @FXML
    private TextField day;
    @FXML
    private TextField start;
    @FXML
    private TextField end;
    @FXML
    private AnchorPane navCalendar;

    private Region root;
    private Model model;
    private ViewHandler viewHandler;
    private NavCalendarView navCalendarView = new NavCalendarView();

    public void CreateCalendarViewController() {

    }

    public void init(ViewHandler viewHandler, Model model, Region root) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
        this.addLesson(new Lesson("SDJ", LocalTime.of(8, 0), LocalTime.of(10, 0)), 0);
        initDates();
        initCalendar();
        initNavCallendar();
    }

    public void reset() {
    }

    public Region getRoot() {
        return root;
    }

    public LocalDate currentDayOfWeek = Util.getMonday();
    public LocalDate endDayOfWeek = currentDayOfWeek.plusDays(6);

    public void initDates() {
        date1.setText(currentDayOfWeek.format(DateTimeFormatter.ofPattern("dd-MMM-yy")));
        date2.setText(endDayOfWeek.format(DateTimeFormatter.ofPattern("dd-MMM-yy")));
    }

    @FXML
    public void onNextWeekClick() {
        currentDayOfWeek = currentDayOfWeek.plusDays(7);
        endDayOfWeek = endDayOfWeek.plusDays(7);
        model.goNextWeek();
        initDates();
        initCalendar();
    }

    @FXML
    public void onPreviousWeekClick() {
        currentDayOfWeek = currentDayOfWeek.minusDays(7);
        endDayOfWeek = endDayOfWeek.minusDays(7);
        model.goPreviousWeek();
        initDates();
        initCalendar();
    }

    @FXML
    public void onAddLessonClick() {
        int _start = Integer.parseInt(start.getText());
        int _end = Integer.parseInt(end.getText());
        addLesson(new Lesson(course.getText(), LocalTime.of(_start, 0), LocalTime.of(_end, 0)), Integer.parseInt(day.getText()));
        initCalendar();
    }

    @FXML
    public void onChooseFileButton() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        this.model.readStudentFromTXTFile(file);
    }

    public void initCalendar() {
        scrollpane.setFitToWidth(true);
        scrollpane.setContent(new CalendarView(this.model.getCurrentWeek()).getFinalView());
    }

    public void addLesson(Lesson lesson, int index) {
        this.model.getCurrentWeek().addLesson(lesson, index);
    }

    //NAV CALENDAR
    public void initNavCallendar() {
        navCalendar.getChildren().add(navCalendarView.getFinalView());
    }

    public void navViewNextWeek() {
        navCalendarView.navNextWeek();

    }

    public void navViewPrevWeek() {
        navCalendarView.navPrevWeek();

    }

}