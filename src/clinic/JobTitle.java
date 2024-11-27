package clinic;

/**
 * Represents the various job titles for staff within the clinic system.
 */
public enum JobTitle {
  PHYSICIAN("physician", "Dr."),
  NURSE("nurse", "Nurse"),
  RECEPTION("reception", "Reception");

  private final String title;
  private final String titlePrefix;

  JobTitle(String title, String titlePrefix) {
    this.title = title;
    this.titlePrefix = titlePrefix;
  }

  public String getTitle() {
    return title;
  }

  public String getTitlePrefix() {
    return titlePrefix;
  }
}

