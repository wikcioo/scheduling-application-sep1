package model.calendar;

import java.io.Serializable;

public class CopiedWeek implements Serializable
{
  private Week copiedWeek;

  public CopiedWeek() {
    copiedWeek = null;
  }
  public void setCopiedWeek(Week week) {
    this.copiedWeek = week.copy();
  }

  public Week getCopiedWeek() {
    return copiedWeek.copy();
  }

  public void removeCopiedWeek() {
    copiedWeek = null;
  }
}
