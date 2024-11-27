package clinic;

import java.util.Objects;

/**
 * The {@link ClinicalStaff} class represents a clinical staff member in the clinic.
 * Clinical staff members have an NPI (National Provider Identifier) and job titles
 * such as physicians or nurses.
 */
public class ClinicalStaff extends Staff {

  // Activation status using enum
  private ActivationStatus activationStatus;

  /**
   * Constructs a {@link ClinicalStaff} object for a clinical staff member.
   *
   * @param firstName      The first name of the clinical staff member.
   * @param lastName       The last name of the clinical staff member.
   * @param jobTitle       The job title of the clinical staff member
   *                       (e.g., physician, nurse).
   * @param educationLevel The education level of the clinical staff member.
   * @param npi            The ten-digit NPI of the clinical staff member.
   * @throws IllegalArgumentException if the NPI is not valid.
   */
  public ClinicalStaff(String firstName,
                       String lastName,
                       JobTitle jobTitle,
                       EducationLevel educationLevel,
                       String npi) {
    super(firstName, lastName, jobTitle, educationLevel, Identifier.NPI, npi);
    this.activationStatus = ActivationStatus.ACTIVE;  // Default to active when created
    this.educationLevel = educationLevel;
    setNpi(npi);  // Ensure NPI is validated
  }

  /**
   * Returns the title prefix of the clinical staff member.
   * This will return "Dr." for physicians and "Nurse" for nurses.
   *
   * @return The clinical staff member's title prefix.
   */
  @Override
  public String getTitlePrefix() {
    return jobTitle.getTitlePrefix();
  }

  /**
   * Returns the full name of the clinical staff member with the appropriate title.
   *
   * @return A string with the staff member's title and full name.
   */
  @Override
  public String getFullName() {
    return super.getFullName();
  }

  /**
   * Returns the {@link JobTitle} enum for the clinical staff member.
   *
   * @return The {@link JobTitle} enum representing the clinical staff's job.
   */
  @Override
  public JobTitle getJobTitle() {
    return this.jobTitle;
  }

  /**
   * Returns the type of the identifier (e.g., NPI).
   *
   * @return The string value for the identifier type.
   */
  public String getIdentifier() {
    return this.identifierValue;
  }

  // Method to get the identifier type (NPI or CPR_LEVEL)
  public Identifier getIdentifierType() {
    return this.identifierType;
  }

  /**
   * Sets the NPI (National Provider Identifier) of the clinical staff member.
   * Ensures the NPI is a valid ten-digit number.
   *
   * @param npi The NPI to set, which must be a valid 10-digit number.
   * @throws IllegalArgumentException if the provided NPI is null or not a 10-digit number.
   */
  public void setNpi(String npi) {
    if (npi == null || npi.length() != 10 || !npi.matches("\\d{10}")) {
      throw new IllegalArgumentException("NPI must be a 10-digit number.");
    }
    this.identifierValue = npi;
  }

  /**
   * Returns the NPI (National Provider Identifier) of the clinical staff member.
   *
   * @return The NPI of the clinical staff member.
   */
  public String getNpi() {
    return this.identifierValue;  // identifierValue stores the NPI
  }

  /**
   * Returns a description of the clinical staff member.
   *
   * @return A string description
    including the staff member's title, full name, job title, and NPI.
   */
  @Override
  public String getDescription() {
    String titlePrefix = getTitlePrefix();
    return String.format("%s %s - job title: %s - "
                    + "education level: %s - Identifier: %s (%s)",
            titlePrefix, getFullName(),
            jobTitle.getTitle(), educationLevel.getLevel(),
            identifierType, identifierValue);
  }

  /**
   * Returns the education level of the clinical staff member.
   *
   * @return The education level of the clinical staff member.
   */
  @Override
  public String getEducationLevel() {
    return educationLevel.getLevel();
  }

  /**
   * Returns the role of the clinical staff member.
   *
   * @return The role of the staff member, which is "Clinical Staff".
   */
  @Override
  public String getStaffRole() {
    return "Clinical Staff";
  }

  /**
   * Deactivates the clinical staff member
   * by setting the activation status to DEACTIVATED.
   * Prints a confirmation message,
   * and if already deactivated, it prints a warning message.
   */
  public void deactivateStaff() {
    if (this.activationStatus == ActivationStatus.DEACTIVATED) {
      System.out.printf("%s is already deactivated.%n", getFullName());
      return;
    }
    this.activationStatus = ActivationStatus.DEACTIVATED;
    System.out.printf("%s has been deactivated.%n", getFullName());
  }

  /**
   * Checks if two clinical staff members are equal
   * based on their first name, last name, job title, and NPI.
   *
   * @param o The other object to compare to.
   * @return True if the clinical staff members
    have the same first name, last name, job title, and NPI.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClinicalStaff that = (ClinicalStaff) o;
    return Objects.equals(identifierValue, that.identifierValue);
  }

  /**
   * Generates a hash code for a clinical staff member
   * based on their first name, last name, job title, and NPI.
   *
   * @return The hash code of the clinical staff member.
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), identifierValue);
  }
}
