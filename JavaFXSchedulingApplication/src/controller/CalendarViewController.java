package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.*;
import model.calendar.Week;
import utilities.Logger;
import utilities.Util;
import view.*;


import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private AnchorPane navCalendar;
    @FXML
    private Button previousWeekButton;
    @FXML
    private Button nextWeekButton;
    @FXML
    private Button pasteButton;
    @FXML
    private Button pasteToAllButton;

    private Region root;
    private Model model;
    private ViewHandler viewHandler;
    private NavCalendarView navCalendarView;

    public void init(ViewHandler viewHandler, Model model, Region root) {
        this.model = model;
        this.viewHandler = viewHandler;
        this.root = root;
        this.navCalendarView = new NavCalendarView(this.model, this);
        initDates();
        initCopyPaste();
        initCalendar();
        initNavCalendar();
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

    private void initCopyPaste() {
        disablePasteButtons(true);
        model.removeCopiedWeek();
    }

    private void disablePasteButtons(boolean disable) {
        pasteButton.setDisable(disable);
        pasteToAllButton.setDisable(disable);
    }

    @FXML
    public void copyWeek() {
        model.setCopiedWeek(model.getCurrentWeek().copy());
        copiedWeekNumber.setText(model.getCurrentWeek().getStart() + " " + model.getCurrentWeek().getEnd());
        disablePasteButtons(false);
    }

    @FXML
    public void pasteCopiedWeek() {
        Week copiedWeek = model.getCopiedWeek();
        if (copiedWeek != null) {
            if (showConfirmAlert("Confirm pasting", "Confirm pasting to week", "Are you sure? This action will override all lessons in this week.")) {
                model.getCurrentWeek().setWeekLessons(copiedWeek);
                Logger.success("Copying successful");
                initCalendar();
            }
        }
    }

    public static boolean showConfirmAlert(String title, String header, String content) {
        //        https://code.makery.ch/blog/javafx-dialogs-official/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    @FXML
    public void pasteCopiedWeekToAll() {
        Week copiedWeek = model.getCopiedWeek();
        if (copiedWeek != null) {
            if (showConfirmAlert("Confirm pasting", "Confirm pasting to all weeks", "Are you sure? This action will override ALL other lessons in this semester!")) {
                for (Week week : model.getCurrentSchedule().getWeekList()) {
                    week.setWeekLessons(copiedWeek);
                }
                initCalendar();
            }
        }
    }

    @FXML
    public void onNextWeekClick() {
        if (model.hasNextWeek()) {
            nextWeekButton.setDisable(false);
            previousWeekButton.setDisable(false);
            currentDayOfWeek = currentDayOfWeek.plusDays(7);
            endDayOfWeek = endDayOfWeek.plusDays(7);
            model.goNextWeek();
            initDates();
            initCalendar();
            initDayForAll();
        }
        if (!model.hasNextWeek()) {
            nextWeekButton.setDisable(true);
        }
    }

    @FXML
    public void onPreviousWeekClick() {
        if (model.hasPreviousWeek()) {
            nextWeekButton.setDisable(false);
            previousWeekButton.setDisable(false);
            currentDayOfWeek = currentDayOfWeek.minusDays(7);
            endDayOfWeek = endDayOfWeek.minusDays(7);
            model.goPreviousWeek();
            initDates();
            initCalendar();
            initDayForAll();
        }
        if (!model.hasPreviousWeek()) {
            previousWeekButton.setDisable(true);
        }
    }

    @FXML
    public void onExportCurrentButtonClick() {
        this.model.exportWeekAsXML(this.model.getCurrentWeek());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully exported current week", ButtonType.OK);
        alert.setHeaderText("Information");
        alert.show();
    }

    @FXML
    public void onChooseFileButton() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
        this.model.getClasses().get(this.model.getCurrentlySelectedClass()).getStudentList().readStudentFromTXTFile(file);
    }

    public void initCalendar() {
        scrollpane.setFitToWidth(true);
        Logger.info(model.getCurrentSchedule().getClassOfStudents().toString());
        CalendarView calendarView = new CalendarView(model);
        scrollpane.setContent(calendarView.getFinalView());
        initButtons(calendarView, model.getCurrentWeek());
    }

    public void initButtons(CalendarView calendarView, Week week) {
        ArrayList<AnchorPaneNode> allEmptyBlocks = calendarView.getEmptyBlocks();
        ArrayList<AnchorPaneNode> allLessonBlocks = calendarView.getLessonBlocks();
        for (int i = 0; i < allLessonBlocks.size(); i++) {
            int finalI = i;
            calendarView.getLessonBlocks().get(i).returnAp().setOnMouseClicked(null);
            calendarView.getLessonBlocks().get(i).returnAp().setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY)
                    calendarView.getLessonBlocks().get(finalI).displayLesson();
                else calendarView.getLessonBlocks().get(finalI).editLessonDisplay();
                initCalendar();
            });
        }
        for (int i = 0; i < allEmptyBlocks.size(); i++) {
            int finalI = i;
            calendarView.getEmptyBlocks().get(i).returnAp().setOnMouseClicked(null);
            calendarView.getEmptyBlocks().get(i).returnAp().setOnMouseClicked(event -> {
                calendarView.getEmptyBlocks().get(finalI).addALessonDisplay();
                initCalendar();
            });
        }

    }

    public void initDayText(Text day, int forward) {
        day.setText(String.valueOf(currentDayOfWeek.plusDays(forward).getDayOfMonth()));
    }

    public void initDayForAll() {
        initDayText(day1, 0);
        initDayText(day2, 1);
        initDayText(day3, 2);
        initDayText(day4, 3);
        initDayText(day5, 4);
        initDayText(day6, 5);
        initDayText(day7, 6);
    }

    public void initNavCalendar() {
        navCalendar.getChildren().add(navCalendarView.getFinalView());
    }

    public void navViewNextWeek() {
        navCalendarView.navNextWeek();
    }

    public void navViewPrevWeek() {
        navCalendarView.navPrevWeek();
    }

    public void back() {
        this.model.getCurrentSchedule().initializeCurrentWeekIndex();
        viewHandler.openView("MainMenu");
    }
}