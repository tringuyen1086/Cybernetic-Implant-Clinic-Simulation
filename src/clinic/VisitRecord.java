package clinic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * The {@code VisitRecord} class
 * represents a record of a patient's visit to the clinic.
 * Each visit includes the registration date and time,
 * chief complaint, and body temperature.
 * Additional fields include the temperature unit (Celsius or Fahrenheit),
 * the visit status, and the discharge approval status.
 * <p>
 * Visit records can be compared
 * using the {@code equals} and {@code hashCode} methods,
 * which consider all fields, including enums and strings.
 * </p>
 */
public class VisitRecord {

  private final LocalDateTime registrationDateTime;
  private final String chiefComplaint;
  private final double bodyTemperature; // Now stored as a double
  private TemperatureUnit temperatureUnit; // TemperatureUnit enum
  private VisitStatus visitStatus; // VisitStatus enum
  private ApprovalStatus approvalStatus; // ApprovalStatus enum

  /**
   * Constructs a {@link VisitRecord} with the minimum required details.
   * This constructor assumes the body temperature is in Celsius,
   * the visit is in progress, and the discharge approval is pending.
   *
   * @param registrationDateTime The date and time of registration,
   *                             as a {@link LocalDateTime}.
   * @param chiefComplaint       The patient's chief complaint,
   *                             represented as a string.
   * @param bodyTemperature      The patient's body temperature
   *                             (in degrees Celsius).
   */
  public VisitRecord(LocalDateTime registrationDateTime,
                     String chiefComplaint,
                     double bodyTemperature) {
    this.registrationDateTime = registrationDateTime;
    this.chiefComplaint = chiefComplaint;
    this.bodyTemperature = bodyTemperature; // Stored as a double
    this.temperatureUnit = TemperatureUnit.CELSIUS; // Default to Celsius
    this.visitStatus = VisitStatus.IN_PROGRESS;     // Default to in progress
    this.approvalStatus = ApprovalStatus.PENDING;   // Default to pending
  }

  /**
   * Constructs a {@link VisitRecord} with all details,
   * including temperature unit, visit status, and approval status.
   *
   * @param registrationDateTime The date and time of registration,
   *                             as a {@link LocalDateTime}.
   * @param chiefComplaint       The patient's chief complaint,
   *                             represented as a string.
   * @param bodyTemperature      The patient's body temperature.
   * @param temperatureUnit      The unit of the temperature
   *                             (Celsius or Fahrenheit).
   * @param visitStatus          The status of the visit
   *                             (in progress, completed, etc.),
   *                             as a {@link VisitStatus}.
   * @param approvalStatus       The discharge approval status
   *                             (pending, approved, denied),
   *                             as an {@link ApprovalStatus}.
   */
  public VisitRecord(LocalDateTime registrationDateTime,
                     String chiefComplaint,
                     double bodyTemperature,
                     TemperatureUnit temperatureUnit,
                     VisitStatus visitStatus,
                     ApprovalStatus approvalStatus) {
    this.registrationDateTime = registrationDateTime;
    this.chiefComplaint = chiefComplaint;
    this.bodyTemperature = temperatureUnit == TemperatureUnit.FAHRENHEIT
            ? convertFahrenheitToCelsius(bodyTemperature)
            : bodyTemperature;
    this.temperatureUnit = TemperatureUnit.CELSIUS; // Always store in Celsius
    this.visitStatus = visitStatus;
    this.approvalStatus = approvalStatus;
  }

  // Method to convert Fahrenheit to Celsius
  private double convertFahrenheitToCelsius(double temperatureInFahrenheit) {
    return (temperatureInFahrenheit - 32) * 5 / 9;
  }

  /**
   * Gets the raw registration date and time of the visit.
   *
   * @return The registration date and time as a {@link LocalDateTime}.
   */
  public LocalDateTime getRegistrationDateTime() {
    return registrationDateTime;
  }

  /**
   * Gets the formatted registration date and time of the visit.
   * The format is "yyyy-MM-dd HH:mm:ss".
   *
   * @return The formatted registration date and time as a string.
   */
  public String getFormattedRegistrationDateTime() {
    return registrationDateTime
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

  /**
   * Gets the chief complaint of the patient during the visit.
   * If no chief complaint is available, a default message is returned.
   *
   * @return The chief complaint as a string.
   */
  public String getChiefComplaint() {
    return chiefComplaint != null && !chiefComplaint.isEmpty()
            ? chiefComplaint
            : "No Chief Complaint available.";
  }

  /**
   * Gets the body temperature of the patient during the visit.
   * The temperature is returned as a string formatted
   * with one decimal place in Celsius.
   *
   * @return The body temperature as a formatted string in Celsius.
   */
  public String getBodyTemperature() {
    // Format the temperature as a string with 1 decimal place
    return String.format("%.1f °C", bodyTemperature);
  }

  /**
   * Gets the temperature unit (Celsius or Fahrenheit) used for this visit.
   *
   * @return The temperature unit as a {@link TemperatureUnit}.
   */
  public TemperatureUnit getTemperatureUnit() {
    return temperatureUnit;
  }

  /**
   * Sets the temperature unit for this visit.
   *
   * @param temperatureUnit The temperature unit (Celsius or Fahrenheit).
   */
  public void setTemperatureUnit(TemperatureUnit temperatureUnit) {
    this.temperatureUnit = temperatureUnit;
  }

  /**
   * Gets the status of the visit (in progress, completed, etc.).
   *
   * @return The visit status as a {@link VisitStatus}.
   */
  public VisitStatus getVisitStatus() {
    return visitStatus;
  }

  /**
   * Sets the status of the visit.
   *
   * @param visitStatus The new status of the visit.
   */
  public void setVisitStatus(VisitStatus visitStatus) {
    this.visitStatus = visitStatus;
  }

  /**
   * Gets the discharge approval status of the visit (pending, approved, denied).
   *
   * @return The approval status as an {@link ApprovalStatus}.
   */
  public ApprovalStatus getApprovalStatus() {
    return approvalStatus;
  }

  /**
   * Sets the discharge approval status of the visit.
   *
   * @param approvalStatus The new approval status.
   */
  public void setApprovalStatus(ApprovalStatus approvalStatus) {
    this.approvalStatus = approvalStatus;
  }

  /**
   * Returns a string representation of the visit record.
   * The string includes the registration date, chief complaint,
   * temperature, visit status, and approval status.
   *
   * @return A formatted string representation of the visit record,
    meeting the project requirements.
   */
  @Override
  public String toString() {
    return String.format(
            "Registration: %s, Chief Complaint: %s, Temperature: %s",
            getFormattedRegistrationDateTime(),
            getChiefComplaint(),
            getBodyTemperature()
    );
  }

  /**
   * Compares this visit record to another object for equality.
   * Two visit records are considered equal
   * if all their fields (date, complaint, temperature, status, approval)
   * are identical.
   *
   * @param o The object to compare to.
   * @return {@code true} if the visit records are equal; {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VisitRecord that = (VisitRecord) o;
    return Double.compare(that.bodyTemperature, bodyTemperature) == 0
            && Objects.equals(registrationDateTime, that.registrationDateTime)
            && Objects.equals(chiefComplaint, that.chiefComplaint)
            && temperatureUnit == that.temperatureUnit
            && visitStatus == that.visitStatus
            && approvalStatus == that.approvalStatus;
  }

  /**
   * Generates a hash code for the visit record based on all its fields.
   *
   * @return The hash code as an {@code int}.
   */
  @Override
  public int hashCode() {
    return Objects.hash(registrationDateTime,
            chiefComplaint,
            bodyTemperature,
            temperatureUnit,
            visitStatus,
            approvalStatus);
  }
}
