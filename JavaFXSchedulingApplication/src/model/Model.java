package model;

import java.util.ArrayList;

public interface Model {
    Week getCurrentWeek();
    int getCurrentWeekIndex();
    void goNextWeek();
    void goPreviousWeek();
    ArrayList<Week> getWeekList();
    int getNumberOfWeeksBetween();
    void initializeCurrentWeekIndex();
    int getCurrentYear();
    void initializeAllWeeks();
}
