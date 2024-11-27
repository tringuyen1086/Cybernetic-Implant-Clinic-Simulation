package clinic;

import java.util.Objects;

/**
 * The {@link NonClinicalStaff} class
 * represents a non-clinical staff member in the clinic.
 * Non-clinical staff members have an identifier (e.g., CPR certification).
 * Non-clinical staff (such as administrative personnel) handle operations
 * that do not involve direct patient care.
 */
public class NonClinicalStaff extends Staff {

  /**
   * Constructs a {@link NonClinicalStaff} object for a non-clinical staff member.
   *
   * @param firstName      The first name of the non-clinical staff member.
   * @param lastName       The last name of the non-clinical staff member.
   * @param jobTitle       The job title of the non-clinical staff member.
   * @param educationLevel The education level of the non-clinical staff member.
   * @param cprLevel       The CPR level of the non-clinical staff member (A, B, C, or BLS).
   */
  public NonClinicalStaff(String firstName,
                          String lastName,
                          JobTitle jobTitle,
                          EducationLevel educationLevel,
                          CprLevel cprLevel) {
    super(firstName, lastName, jobTitle, educationLevel, Identifier.CPR, cprLevel.name());
  }

  /**
   * Returns the title prefix of the non-clinical staff member.
   *
   * @return The title prefix of the staff member (e.g., "Reception").
   */
  @Override
  public String getTitlePrefix() {
    return jobTitle.getTitlePrefix();
  }

  /**
   * Returns the full name of the staff member (first name + last name).
   *
   * @return The full name of the staff member.
   */

  public String getFullName() {
    return this.getFirstName() + " " + this.getLastName();
  }

  /**
   * Returns the job title of the non-clinical staff member.
   *
   * @return The job title of the staff member (as JobTitle enum).
   */
  @Override
  public JobTitle getJobTitle() {
    return jobTitle;
  }

  /**
   * Returns the education level of the non-clinical staff member.
   * This method overrides the inherited method from the {@link Staff} class.
   *
   * @return The education level of the staff member (e.g., doctoral, masters, allied).
   */
  @Override
  public String getEducationLevel() {
    return educationLevel.getLevel();
  }


  /**
   * Returns the identifier type of the non-clinical staff member (e.g., CPR level).
   *
   * @return The identifier type.
   */
  public Identifier getIdentifierType() {
    return identifierType;
  }

  /**
   * Returns the identifier value of the non-clinical staff member (e.g., CPR Levels).
   *
   * @return The identifier value.
   */
  public String getIdentifierValue() {
    return identifierValue;
  }

  /**
   * Returns the CPR level of the non-clinical staff member.
   *
   * @return The CPR level of the non-clinical staff member.
   */
  public String getCprLevel() {
    return this.identifierValue;  // identifierValue stores the CPR level
  }

  /**
   * Returns a description of the non-clinical staff member.
   *
   * @return A string description including the staff member's full name, job title,
   *         educational level, and CPR level.
   */
  @Override
  public String getDescription() {
    return String.format("%s - job title: %s - education level: %s - Identifier: %s (%s)",
            getFullName(),
            jobTitle.getTitle(),
            educationLevel.getLevel(),
            identifierType,
            identifierValue);
  }

  /**
   * Returns the role of the non-clinical staff member.
   *
   * @return The role of the staff member, which is "Non-Clinical Staff".
   */
  @Override
  public String getStaffRole() {
    return "Non-Clinical Staff";
  }

  /**
   * Checks if two non-clinical staff members are equal
   * based on their first name, last name, job title, and CPR level.
   *
   * @param o The other object to compare to.
   * @return True if the non-clinical staff members have the same first name,
   *         last name, job title, and CPR level.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NonClinicalStaff that = (NonClinicalStaff) o;
    // CPR Level is in identifierValue
    return Objects.equals(identifierValue, that.identifierValue);
  }

  /**
   * Generates a hash code for a non-clinical staff member
   * based on their first name, last name, job title, and CPR level.
   *
   * @return The hash code of the non-clinical staff member.
   */
  @Override
  public int hashCode() {
    // CPR Level is in identifierValue
    return Objects.hash(super.hashCode(), identifierValue);
  }
}