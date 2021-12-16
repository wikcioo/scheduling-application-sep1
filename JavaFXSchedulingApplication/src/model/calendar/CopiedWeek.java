package model.calendar;

import java.io.Serializable;

/**
 * This class is a representation of a copied week used to copy weeks around the schedule.
 * It implements Serializable interface for persistence purposes.
 */
public class CopiedWeek implements Serializable {
    private Week copiedWeek;

    /**
     * Initializes the copied week to null.
     */
    public CopiedWeek() {
        copiedWeek = null;
    }

    /**
     * @param week the week to be set
     */
    public void setCopiedWeek(Week week) {
        this.copiedWeek = week.copy();
    }

    /**
     * @return the copy of the copied week
     */
    public Week getCopiedWeek() {
        return copiedWeek.copy();
    }

    /**
     * Assigns null to the copied week
     */
    public void removeCopiedWeek() {
        copiedWeek = null;
    }
}
