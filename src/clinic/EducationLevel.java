package clinic;

/**
 * Represents the various education levels for clinical staff.
 */
public enum EducationLevel {
  DOCTORAL("doctoral"),
  MASTERS("masters"),
  ALLIED("allied");

  private final String level;

  EducationLevel(String level) {
    this.level = level;
  }

  public String getLevel() {
    return level;
  }
}
