package clinic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@link Patient} class represents a patient in the clinic.
 * Patients have a first name, last name, date of birth, visit records,
 * and a room assignment.
 */
public class Patient extends Person implements PatientInterface {
  private final int patientId;  // Final to ensure immutability
  private final String dob;
  private final List<VisitRecord> visitRecords; // Declare the visit records list
  private RoomInterface assignedRoom; // Current room assigned to the patient
  private VisitStatus visitStatus;  // VisitStatus enum

  /**
   * Constructs a {@link Patient} object for a patient.
   *
   * @param firstName   The first name of the patient. Must not be {@code null} or empty.
   * @param lastName    The last name of the patient. Must not be {@code null} or empty.
   * @param patientId   The unique ID of the patient.
   * @param dob         The date of birth of the patient. Must not be {@code null} or empty.
   * @param visitStatus The current status of the patient’s visit,
   *                    represented by {@link VisitStatus}.
   * @throws IllegalArgumentException if any of the parameters are {@code null} or empty.
   */
  public Patient(String firstName,
                 String lastName,
                 int patientId,
                 String dob,
                 VisitStatus visitStatus) {
    super(firstName, lastName);
    if (dob == null || dob.isBlank()) {
      throw new IllegalArgumentException("Date of birth cannot be null or empty.");
    }
    if (patientId < 0) {
      throw new IllegalArgumentException("Patient ID cannot be negative.");
    }
    this.patientId = patientId;
    this.dob = dob;
    // Default to IN_PROGRESS if no status is provided
    this.visitStatus = visitStatus != null ? visitStatus : VisitStatus.IN_PROGRESS;
    this.visitRecords = new ArrayList<>();
  }

  /**
   * Retrieves the patient ID.
   *
   * @return The unique ID of the patient.
   */
  @Override
  public int getPatientId() {
    return patientId;
  }

  /**
   * Retrieves the date of birth of the patient.
   *
   * @return A string representing the date of birth (DOB) of the patient.
   */
  @Override
  public String getDateOfBirth() {
    return dob;
  }

  /**
   * Retrieves the visit status of the patient.
   *
   * @return The current {@link VisitStatus} of the patient.
   */
  @Override
  public VisitStatus getVisitStatus() {
    return visitStatus;
  }

  /**
   * Sets the visit status of the patient.
   *
   * @param visitStatus The new {@link VisitStatus} to set.
   */
  @Override
  public void setVisitStatus(VisitStatus visitStatus) {
    this.visitStatus = visitStatus;
  }

  /**
   * Retrieves a list of the patient's visit records.
   * This method returns a defensive copy of the list to prevent external modification.
   *
   * @return A list of {@link VisitRecord} objects representing the patient's medical history.
   */
  @Override
  public List<VisitRecord> getVisitRecords() {
    // Defensive copy to prevent external changes
    return new ArrayList<>(visitRecords);
  }

  /**
   * Adds a new visit record to the patient's history.
   *
   * @param visitRecord The {@link VisitRecord} to be added. Must not be {@code null}.
   * @throws IllegalArgumentException if the visit record is {@code null}.
   */
  @Override
  public void addVisitRecord(VisitRecord visitRecord) {
    if (visitRecord == null) {
      throw new IllegalArgumentException("Visit record cannot be null.");
    }
    visitRecords.add(visitRecord);
  }

  /**
   * Provides a description of the patient,
   * including their full name, patient ID, DOB, and visit records.
   *
   * @return A string description
    including the patient's ID, full name, DOB, and visit records.
   */
  @Override
  public String getDescription() {
    StringBuilder description = new StringBuilder(getFullName())
            .append(" (Patient ID: ")
            .append(patientId)
            .append(", DOB: ").append(dob)
            // Include VisitStatus in the description
            .append(", Status: ").append(visitStatus)
            .append(")\n");

    description.append("Visit Records: ");
    if (visitRecords.isEmpty()) {
      description.append("No visit records available.");
    } else {
      for (VisitRecord record : visitRecords) {
        description.append("Registration Time: ")
                .append(record.getFormattedRegistrationDateTime())
                .append(", Chief Complaint: ").append(record.getChiefComplaint())
                .append(", Temperature: ").append(record.getBodyTemperature())
                .append("\n");
      }
    }
    return description.toString().trim(); // Remove trailing newline
  }

  /**
   * Retrieves the latest chief complaint from the most recent visit record.
   * A chief complaint (CC) is a brief statement that
   * describes the reason a patient is seeking medical care
   *
   * @return A string representing the chief complaint from the latest visit record,
    or "No Chief Complaint (CC) available." if no visits exist.
   */
  @Override
  public String getLatestVisitChiefComplaint() {
    if (visitRecords.isEmpty()) {
      return "No Chief Complaint (CC) available.";
    }
    return visitRecords.get(visitRecords.size() - 1).getChiefComplaint();
  }

  /**
   * Assigns a room to the patient.
   *
   * @param room The {@link RoomInterface} to assign.
   *             Can be {@code null} to remove the assignment.
   */
  @Override
  public void setAssignedRoom(RoomInterface room) {
    this.assignedRoom = room;
  }

  /**
   * Retrieves the room currently assigned to the patient.
   *
   * @return The {@link RoomInterface} object representing the patient's assigned room,
    or {@code null} if the patient is not assigned to any room.
   */
  @Override
  public RoomInterface getAssignedRoom() {
    return assignedRoom;
  }

  /**
   * Compares this patient with another object for equality.
   * Two patients are considered equal
   * if they have the same first name, last name, and date of birth.
   *
   * @param o The object to compare to.
   * @return {@code true} if the given object is a {@link Patient}
    with the same first name, last name,
    and date of birth; {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Patient patient = (Patient) o;
    return patientId == patient.patientId
            && Objects.equals(firstName, patient.firstName)
            && Objects.equals(lastName, patient.lastName)
            && Objects.equals(dob, patient.dob);
  }

  /**
   * Generates a hash code for a patient based on their first name, last name, and date of birth.
   *
   * @return The hash code of the patient.
   */
  @Override
  public int hashCode() {
    return Objects.hash(patientId, firstName, lastName, dob);
  }
}