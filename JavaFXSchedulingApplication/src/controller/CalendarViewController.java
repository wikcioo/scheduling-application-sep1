package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import model.calendar.Day;
import model.calendar.Lesson;
import model.calendar.Week;
import model.courses.Course;
import model.courses.CourseList;
import utilities.Util;
import view.*;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Optional;

public class CalendarViewController extends ViewController {
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
    private Text copiedWeekNumber;
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
    @FXML
    private Button previousWeekButton;
    @FXML
    private Button nextWeekButton;
    @FXML
    private Button copyButton;
    @FXML
    private Button pasteButton;
    @FXML
    private Button pasteToAllButton;
    @FXML
    private AnchorPane filter;

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
        initDates();
        initCopyPaste();
        initCalendar(this.model.getCurrentWeek());
        initNavCallendar();
        initDayForAll();
        initCourseListView();
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

    private void initCopyPaste() {
        disablePasteButtons(true);
        model.getCopiedWeekWrapper().removeCopiedWeek();
    }

    private void disablePasteButtons(boolean disable) {
        pasteButton.setDisable(disable);
        pasteToAllButton.setDisable(disable);
    }

    @FXML
    public void copyWeek() {
        model.getCopiedWeekWrapper().setCopiedWeek(model.getCurrentWeek().copy());
        copiedWeekNumber.setText(model.getCurrentWeek().getStart() + " " + model.getCurrentWeek().getEnd());
        disablePasteButtons(false);
    }

    @FXML
    public void pasteCopiedWeek() {
        Week copiedWeek = model.getCopiedWeekWrapper().getCopiedWeek();
        if(copiedWeek != null) {
            if(showConfirmAlert("Confirm pasting","Confirm pasting to week","Are you sure? This action will override all lessons in this week."))
            {
                model.getCurrentWeek().copyWeekLessons(copiedWeek);
                System.out.println("Copying successful");
                initCalendar(model.getCurrentWeek());
            }
        }
    }

    public boolean showConfirmAlert(String title,String header, String content) {
        //        https://code.makery.ch/blog/javafx-dialogs-official/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return  result.get() == ButtonType.OK;
    }

    @FXML
    public void pasteCopiedWeekToAll() {
        Week copiedWeek = model.getCopiedWeekWrapper().getCopiedWeek();
        if(copiedWeek != null){
            if(showConfirmAlert("Confirm pasting","Confirm pasting to all weeks","Are you sure? This action will override ALL other lessons in this semester!")) {
                for(Week week : model.getScheduleList().getCurrentSchedule().getWeekList()) {
                    week.copyWeekLessons(copiedWeek);
                }
                initCalendar(model.getCurrentWeek());
            }
        }
    }

    @FXML
    public void onNextWeekClick() {
        if(model.hasNextWeek()){
            nextWeekButton.setDisable(false);
            previousWeekButton.setDisable(false);
            currentDayOfWeek = currentDayOfWeek.plusDays(7);
            endDayOfWeek = endDayOfWeek.plusDays(7);
            model.goNextWeek();
            initDates();
            initCalendar(this.model.getCurrentWeek());
            initDayForAll();
            initCourseListView();
        }
        if (!model.hasNextWeek()){
            nextWeekButton.setDisable(true);
        }
    }

    @FXML
    public void onPreviousWeekClick() {
        if(model.hasPreviousWeek()){
            nextWeekButton.setDisable(false);
            previousWeekButton.setDisable(false);
            currentDayOfWeek = currentDayOfWeek.minusDays(7);
            endDayOfWeek = endDayOfWeek.minusDays(7);
            model.goPreviousWeek();
            initDates();
            initCalendar(this.model.getCurrentWeek());
            initDayForAll();
            initCourseListView();
        }
        if (!model.hasPreviousWeek()) {
            previousWeekButton.setDisable(true);
        }
    }

    @FXML
    public void onAddLessonClick() {
        int _start = Integer.parseInt(start.getText());
        int _end = Integer.parseInt(end.getText());
        addLesson(new Lesson(course.getText(), LocalTime.of(_start, 0), LocalTime.of(_end, 0)), Integer.parseInt(day.getText()));
        initCalendar(this.model.getCurrentWeek());
    }

    @FXML
    public void onChooseFileButton() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        this.model.readStudentFromTXTFile(file);
    }

    public void initCalendar(Week week) {
        scrollpane.setFitToWidth(true);
        System.out.println(model.getScheduleList().getCurrentSchedule().getClassOfStudents());
        CalendarView calendarView = new CalendarView(week);
        scrollpane.setContent(calendarView.getFinalView());
        initButtons(calendarView,week);
    }

    public void initButtons(CalendarView calendarView,Week week) {
        ArrayList<AnchorPaneNode> allEmptyBlocks = calendarView.getEmptyBlocks();
        ArrayList<AnchorPaneNode> allLessonBlocks = calendarView.getLessonBlocks();
        for (int i=0;i<allLessonBlocks.size();i++) {
            int finalI = i;
            calendarView.getLessonBlocks().get(i).returnAp().setOnMouseClicked(null);
            calendarView.getLessonBlocks().get(i).returnAp().setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY) calendarView.getLessonBlocks().get(finalI).displayLesson();
                else calendarView.getLessonBlocks().get(finalI).editlesson();
                initCalendar(week);
            });
        }
        for (int i=0;i<allEmptyBlocks.size();i++) {
            int finalI = i;
            calendarView.getEmptyBlocks().get(i).returnAp().setOnMouseClicked(null);
            calendarView.getEmptyBlocks().get(i).returnAp().setOnMouseClicked(event -> {
                calendarView.getEmptyBlocks().get(finalI).addALesson();
                initCalendar(week);
            });
        }

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

    public void back(){
        this.model.getScheduleList().getCurrentSchedule().initializeCurrentWeekIndex();
        viewHandler.openView("MainMenu");
    }

    //FILTER FOR MAIN CALENDAR
    public void initCourseListView() {
        CoursesListView coursesListView = new CoursesListView(this.model.getCurrentWeek());
        filter.getChildren().add(coursesListView.getFinalView());
        for (Button button:coursesListView.getButtonsForEachCourse()) {
            button.setOnMouseClicked(event -> {
                initCalendar(this.model.getCurrentWeek().filterBasedOnCourse(button.getText()));
            });
        }
    }
    // Previously from from anchor pane node

}