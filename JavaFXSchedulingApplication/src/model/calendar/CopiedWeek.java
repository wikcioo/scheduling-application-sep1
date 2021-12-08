package model.calendar;

public class CopiedWeek
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
