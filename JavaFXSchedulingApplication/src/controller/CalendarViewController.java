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
    private Text day1;
    @FXML
    private Text day2;
    @FXML
    private Text day3;
    @FXML
    private Text day4;
    @FXML
    private Text day5;
    @FXML
    private Text day6;
    @FXML
    private Text day7;
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
        initDayForAll();
    }

    public void reset() {
    }

    public Region getRoot() {
        return root;
    }

    public LocalDate currentDayOfWeek = Util.getMonday();
    public LocalDate endDayOfWeek = currentDayOfWeek.plusDays(6);

    public void initDates() {
        if (currentDayOfWeek.getMonth() == endDayOfWeek.getMonth()) {
            date1.setText(currentDayOfWeek.format(DateTimeFormatter.ofPattern("MMM")));
            date2.setText("- " + currentDayOfWeek.format(DateTimeFormatter.ofPattern("yyyy")));
        } else {
            date1.setText(currentDayOfWeek.format(DateTimeFormatter.ofPattern("MMM")));
            date2.setText(" - " + endDayOfWeek.format(DateTimeFormatter.ofPattern("MMM")) + " " + endDayOfWeek.format(DateTimeFormatter.ofPattern("yyyy")));
        }
    }

    @FXML
    public void onNextWeekClick() {
        currentDayOfWeek = currentDayOfWeek.plusDays(7);
        endDayOfWeek = endDayOfWeek.plusDays(7);
        model.goNextWeek();
        initDates();
        initCalendar();
        initDayForAll();
    }

    @FXML
    public void onPreviousWeekClick() {
        currentDayOfWeek = currentDayOfWeek.minusDays(7);
        endDayOfWeek = endDayOfWeek.minusDays(7);
        model.goPreviousWeek();
        initDates();
        initCalendar();
        initDayForAll();
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

    public void initDayText(Text day,int forward) {
        day.setText(String.valueOf(currentDayOfWeek.plusDays(forward).getDayOfMonth()));
    }
    public void initDayForAll() {
        initDayText(day1,0);
        initDayText(day2,1);
        initDayText(day3,2);
        initDayText(day4,3);
        initDayText(day5,4);
        initDayText(day6,5);
        initDayText(day7,6);
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