package clinic;

import java.util.Objects;

/**
 * The abstract {@link Staff} class represents a staff member in the clinic.
 * Staff members include both clinical and non-clinical staff.
 * Each staff member has a job title, education level,
 * and a unique identifier such as an NPI or CPR number.
 */
public abstract class Staff extends Person {
  protected JobTitle jobTitle; // Jobtitle enum
  protected EducationLevel educationLevel; // EducationLevel enum

  protected Identifier identifierType; // Identifier enum (e.g., NPI, CPR)
  protected String identifierValue; // NPI number or CPR level (B)

  /**
   * Constructs a {@link Staff} object for a staff member.
   *
   * @param firstName The first name of the staff member.
   * @param lastName The last name of the staff member.
   * @param jobTitle The job title of the staff member
   *                 (e.g., physician, nurse, receptionist).
   * @param educationLevel The education level of the staff member
   *                       (e.g., doctoral, masters, allied).
   * @param identifierType The type of identifier for the staff member
   *                       (e.g., NPI, CPR).
   * @param identifierValue The actual identifier value
   *                        (e.g., NPI number, CPR level).
   */
  public Staff(String firstName,
               String lastName,
               JobTitle jobTitle,
               EducationLevel educationLevel,
               Identifier identifierType,
               String identifierValue) {
    super(firstName, lastName);
    this.jobTitle = jobTitle;
    this.educationLevel = educationLevel;
    this.identifierType = identifierType;
    this.identifierValue = identifierValue;
  }

  /**
   * Returns the job title of the staff member.
   *
   * @return The {@link JobTitle} of the staff member.
   */
  public JobTitle getJobTitle() {
    return jobTitle;
  }

  /**
   * Returns the title prefix of the staff member
   * based on their job title.
   * For example, physicians have the prefix "Dr.",
   * and nurses have the prefix "Nurse".
   *
   * @return The title prefix (e.g., "Dr.", "Nurse") of the staff member.
   */
  public String getTitlePrefix() {
    return jobTitle.getTitlePrefix();
  }

  /**
   * Returns the education level of the staff member.
   *
   * @return The education level of the staff member as a {@link String}.
   */
  public String getEducationLevel() {
    return educationLevel.getLevel();
  }

  /**
   * Retrieves the identifier type of the staff member, such as NPI or CPR Level.
   *
   * @return The {@link Identifier} type of the staff member.
   */
  public Identifier getIdentifierType() {
    return identifierType;
  }

  /**
   * Retrieves the actual identifier value of the staff member.
   *
   * @return The identifier value of the staff member as a {@link String}.
   */
  public String getIdentifierValue() {
    return identifierValue;
  }

  /**
   * Returns the role of the staff member,
   * which could be clinical or non-clinical.
   * Each subclass of {@link Staff} can override this method if necessary.
   *
   * @return A string representing the staff member's role.
   */
  public String getStaffRole() {
    return jobTitle == JobTitle.RECEPTION ? "Non-Clinical Staff" : "Clinical Staff";
  }

  /**
   * Returns a description of the staff member including their title, name,
   * job title, education level, and identifier.
   *
   * @return A string containing the staff member's description.
   */
  public String getDescription() {
    String titlePrefix = getTitlePrefix();
    return String.format("%s %s - %s - %s - %s (%s: %s)",
            titlePrefix, getFullName(), jobTitle.getTitle(),
            educationLevel.getLevel(), getStaffRole(),
            identifierType.name(), identifierValue);
  }

  /**
   * Compares this staff member to another object for equality
   * based on their first name, last name, job title,
   * education level, identifier type, and identifier value.
   *
   * @param o The other object to compare against.
   * @return {@code true} if the two staff members are considered equal;
    {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Staff)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Staff staff = (Staff) o;
    return jobTitle == staff.jobTitle
            && educationLevel == staff.educationLevel
            && identifierType == staff.identifierType
            && Objects.equals(identifierValue, staff.identifierValue);
  }

  /**
   * Returns a hash code for this staff member based on their first name,
   * last name, job title, education level, identifier type, and identifier value.
   *
   * @return The hash code for the staff member.
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(),
            jobTitle,
            educationLevel,
            identifierType,
            identifierValue);
  }
}