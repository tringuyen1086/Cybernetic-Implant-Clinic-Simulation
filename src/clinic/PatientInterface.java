package clinic;

import java.util.List;

/**
 * The {@code PatientInterface} defines the contract for a patient entity
 * in the clinic system. It ensures that patient-related operations,
 * such as retrieving personal details, managing visit records,
 * and handling room assignments, are available
 * to all implementations of this interface.
 */
public interface PatientInterface {

  /**
   * Retrieves the first name of the patient.
   *
   * @return The first name of the patient as a {@code String}.
   */
  String getFirstName();  // Add this method

  /**
   * Retrieves the last name of the patient.
   *
   * @return The last name of the patient as a {@code String}.
   */
  String getLastName();  // Add this method

  /**
   * Retrieves the full name of the patient, typically a concatenation of
   * the first and last name.
   *
   * @return The full name of the patient as a {@code String}.
   */
  String getFullName(); // Explicitly declare it in the interface

  /**
   * Retrieves the unique identifier of the patient.
   *
   * @return The patient ID as an {@code int}.
   */
  int getPatientId();

  /**
   * Retrieves the date of birth of the patient.
   *
   * @return The date of birth of the patient as a {@code String}.
   */
  String getDateOfBirth();

  /**
   * Provides a description of the patient, which may include details
   * such as the full name, patient ID, date of birth, and visit records.
   *
   * @return A {@code String} description of the patient.
   */
  String getDescription();

  /**
   * Retrieves the list of visit records for the patient, detailing their
   * previous visits to the clinic.
   *
   * @return A list of {@link VisitRecord} objects,
    representing the patient's visit history.
   */
  List<VisitRecord> getVisitRecords();

  /**
   * Adds a new visit record to the patient's visit history.
   *
   * @param visitRecord The {@link VisitRecord} object
   *                    representing the patient's latest visit.
   *                    Must not be {@code null}.
   */
  void addVisitRecord(VisitRecord visitRecord);

  /**
   * Retrieves the chief complaint from the patient's most recent visit record.
   * This complaint typically describes the main reason for the visit.
   *
   * @return The chief complaint from the latest visit record as a {@code String},
   *         or "No visit records available" if the patient has no visit records.
   */
  String getLatestVisitChiefComplaint();

  /**
   * Sets the room assigned to the patient, allowing the clinic to manage
   * patient room assignments.
   *
   * @param room The {@link RoomInterface} object representing the room to assign.
   *             Must not be {@code null}.
   */
  void setAssignedRoom(RoomInterface room);

  /**
   * Retrieves the room currently assigned to the patient.
   * This could be a waiting room,
   * exam room, or another room within the clinic.
   *
   * @return The assigned {@link RoomInterface} object,
    or {@code null} if no room is assigned.
   */
  RoomInterface getAssignedRoom();

  /**
   * Retrieves the visit status of the patient,
   * indicating their current treatment status.
   *
   * @return The {@link VisitStatus} of the patient.
   */
  VisitStatus getVisitStatus();

  /**
   * Sets the visit status of the patient, allowing the clinic to update the current
   * treatment status of the patient.
   *
   * @param visitStatus The {@link VisitStatus} to set for the patient.
   */
  void setVisitStatus(VisitStatus visitStatus);
}