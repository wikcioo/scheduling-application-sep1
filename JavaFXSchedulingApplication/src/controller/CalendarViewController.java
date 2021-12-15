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

/**
 * The purpose of this class is used to initialize the UI elements of CalendarView.fxml
 */
public class CalendarViewController extends ViewController
{
  @FXML private ScrollPane scrollpane;
  @FXML private Text date1;
  @FXML private Text date2;
  @FXML private Text day1;
  @FXML private Text day2;
  @FXML private Text day3;
  @FXML private Text day4;
  @FXML private Text day5;
  @FXML private Text day6;
  @FXML private Text day7;
  @FXML private Text copiedWeekNumber;
  @FXML private AnchorPane navCalendar;
  @FXML private Button previousWeekButton;
  @FXML private Button nextWeekButton;
  @FXML private Button pasteButton;
  @FXML private Button pasteToAllButton;

  private Region root;
  private Model model;
  private ViewHandler viewHandler;
  private NavCalendarView navCalendarView;

  /**
   * @param viewHandler;
   * @param model;
   * @param root;        The purpose of this method is to lunch the window and initialize all
   *                     controls of the window
   */
  public void init(ViewHandler viewHandler, Model model, Region root)
  {
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

  public void reset()
  {
  }

  /**
   * @return the root of the controller
   */
  public Region getRoot()
  {
    return root;
  }

  public LocalDate currentDayOfWeek = Util.getMonday();
  public LocalDate endDayOfWeek = currentDayOfWeek.plusDays(6);

  /**
   * The purpose of this method is to initialize all dates
   */
  public void initDates()
  {
    if (currentDayOfWeek.getMonth() == endDayOfWeek.getMonth())
    {
      date1.setText(
          currentDayOfWeek.format(DateTimeFormatter.ofPattern("MMM")));
      date2.setText(
          "- " + currentDayOfWeek.format(DateTimeFormatter.ofPattern("yyyy")));
    }
    else
    {
      date1.setText(
          currentDayOfWeek.format(DateTimeFormatter.ofPattern("MMM")));
      date2.setText(
          " - " + endDayOfWeek.format(DateTimeFormatter.ofPattern("MMM")) + " "
              + endDayOfWeek.format(DateTimeFormatter.ofPattern("yyyy")));
    }
  }

  /**
   * The purpose of this method is to initialize the copy and paste
   */
  private void initCopyPaste()
  {
    disablePasteButtons(true);
    model.removeCopiedWeek();
  }

  /**
   * The purpose of this method is to make the paste buttons inactive
   *
   * @param disable state of a button
   */
  private void disablePasteButtons(boolean disable)
  {
    pasteButton.setDisable(disable);
    pasteToAllButton.setDisable(disable);
  }

  /**
   * The purpose of this method is to copy a week
   */
  @FXML public void copyWeek()
  {
    model.setCopiedWeek(model.getCurrentWeek().copy());
    copiedWeekNumber.setText(
        model.getCurrentWeek().getStart() + " " + model.getCurrentWeek()
            .getEnd());
    disablePasteButtons(false);
  }

  /**
   * The purpose of this method is to paste the week copied
   */
  @FXML public void pasteCopiedWeek()
  {
    Week copiedWeek = model.getCopiedWeek();
    if (copiedWeek != null)
    {
      if (showConfirmAlert("Confirm pasting", "Confirm pasting to week",
          "Are you sure? This action will override all lessons in this week."))
      {
        model.getCurrentWeek().setWeekLessons(copiedWeek);
        Logger.success("Copying successful");
        initCalendar();
      }
    }
  }

  /**
   * The purpose of this method is to display a confirmation pop-up
   *
   * @param title   the title of the alert
   * @param header  the header of the alert
   * @param content the contet of the alert
   * @return the result of the alert
   */
  public static boolean showConfirmAlert(String title, String header,
      String content)
  {
    //        https://code.makery.ch/blog/javafx-dialogs-official/
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    Optional<ButtonType> result = alert.showAndWait();
    return result.get() == ButtonType.OK;
  }

  /**
   * The purpose of this method is to paste the copied week to all the other
   */
  @FXML public void pasteCopiedWeekToAll()
  {
    Week copiedWeek = model.getCopiedWeek();
    if (copiedWeek != null)
    {
      if (showConfirmAlert("Confirm pasting", "Confirm pasting to all weeks",
          "Are you sure? This action will override ALL other lessons in this semester!"))
      {
        for (Week week : model.getCurrentSchedule().getWeekList())
        {
          week.setWeekLessons(copiedWeek);
        }
        initCalendar();
      }
    }
  }

  /**
   * The purpose of this method is to navigate to the next week
   */
  @FXML public void onNextWeekClick()
  {
    if (model.hasNextWeek())
    {
      nextWeekButton.setDisable(false);
      previousWeekButton.setDisable(false);
      currentDayOfWeek = currentDayOfWeek.plusDays(7);
      endDayOfWeek = endDayOfWeek.plusDays(7);
      model.goNextWeek();
      initDates();
      initCalendar();
      initDayForAll();
    }
    if (!model.hasNextWeek())
    {
      nextWeekButton.setDisable(true);
    }
  }

  /**
   * The purpose of this method is to navigate to the previous week
   */
  @FXML public void onPreviousWeekClick()
  {
    if (model.hasPreviousWeek())
    {
      nextWeekButton.setDisable(false);
      previousWeekButton.setDisable(false);
      currentDayOfWeek = currentDayOfWeek.minusDays(7);
      endDayOfWeek = endDayOfWeek.minusDays(7);
      model.goPreviousWeek();
      initDates();
      initCalendar();
      initDayForAll();
    }
    if (!model.hasPreviousWeek())
    {
      previousWeekButton.setDisable(true);
    }
  }

  /**
   * The purpose of this method is to export and save the calendar data and display an alert
   */
  @FXML public void onExportCurrentButtonClick()
  {
    this.model.exportWeekAsXML(this.model.getCurrentWeek());
    Alert alert = new Alert(Alert.AlertType.INFORMATION,
        "Successfully exported current week", ButtonType.OK);
    alert.setHeaderText("Information");
    alert.show();
  }

  /**
   * The purpose of this method is to choose a file from your computer to import
   */
  @FXML public void onChooseFileButton()
  {
    FileChooser fileChooser = new FileChooser();
    File file = fileChooser.showOpenDialog(viewHandler.getPrimaryStage());
    this.model.getClasses().get(this.model.getCurrentlySelectedClass())
        .getStudentList().readStudentFromTXTFile(file);
  }

  /**
   * The purpose of this method is to initialize the calendar
   */
  public void initCalendar()
  {
    scrollpane.setFitToWidth(true);
    Logger.info(model.getCurrentSchedule().getClassOfStudents().toString());
    CalendarView calendarView = new CalendarView(model);
    scrollpane.setContent(calendarView.getFinalView());
    initButtons(calendarView, model.getCurrentWeek());
  }

  /**
   * The purpose of this method is to initialize the buttons of the calendar
   *
   * @param calendarView the view of the calendar
   * @param week         a week of the calendar
   */
  public void initButtons(CalendarView calendarView, Week week)
  {
    ArrayList<AnchorPaneNode> allEmptyBlocks = calendarView.getEmptyBlocks();
    ArrayList<AnchorPaneNode> allLessonBlocks = calendarView.getLessonBlocks();
    for (int i = 0; i < allLessonBlocks.size(); i++)
    {
      int finalI = i;
      calendarView.getLessonBlocks().get(i).returnAp().setOnMouseClicked(null);
      calendarView.getLessonBlocks().get(i).returnAp()
          .setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY)
              calendarView.getLessonBlocks().get(finalI).displayLesson();
            else
              calendarView.getLessonBlocks().get(finalI).editLessonDisplay();
            initCalendar();
          });
    }
    for (int i = 0; i < allEmptyBlocks.size(); i++)
    {
      int finalI = i;
      calendarView.getEmptyBlocks().get(i).returnAp().setOnMouseClicked(null);
      calendarView.getEmptyBlocks().get(i).returnAp()
          .setOnMouseClicked(event -> {
            calendarView.getEmptyBlocks().get(finalI).addALessonDisplay();
            initCalendar();
          });
    }

  }

  /**
   * The purpose of this method is to initialize the text of a day
   *
   * @param day     a day of the calendar
   * @param forward the next day
   */
  public void initDayText(Text day, int forward)
  {
    day.setText(
        String.valueOf(currentDayOfWeek.plusDays(forward).getDayOfMonth()));
  }

  public void initDayForAll()
  {
    initDayText(day1, 0);
    initDayText(day2, 1);
    initDayText(day3, 2);
    initDayText(day4, 3);
    initDayText(day5, 4);
    initDayText(day6, 5);
    initDayText(day7, 6);
  }

  /**
   * The purpose of this method is to initialize the navigation of the calendar
   */
  public void initNavCalendar()
  {
    navCalendar.getChildren().add(navCalendarView.getFinalView());
  }

  /**
   * The purpose of this method is to initialize the navigation to the next week
   * of the calendar
   */
  public void navViewNextWeek()
  {
    navCalendarView.navNextWeek();
  }

  /**
   * The purpose of this method is to initialize the navigation to the previous week
   * of the calendar
   */
  public void navViewPrevWeek()
  {
    navCalendarView.navPrevWeek();
  }

  /**
   * The purpose of this method is to open the Main Menu window when it is called
   */
  public void back()
  {
    this.model.getCurrentSchedule().initializeCurrentWeekIndex();
    viewHandler.openView("MainMenu");
  }
}