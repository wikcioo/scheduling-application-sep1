package model;

import java.io.File;
import java.util.ArrayList;

public interface Model {
    void readStudentFromTXTFile(File file);
    Week getCurrentWeek();
    int getCurrentWeekIndex();
    void goNextWeek();
    boolean hasNextWeek();
    void goPreviousWeek();
    boolean hasPreviousWeek();
    ArrayList<Week> getWeekList();
    int getNumberOfWeeksBetween();
    void initializeCurrentWeekIndex();
    int getCurrentYear();
    void initializeAllWeeks();
}
