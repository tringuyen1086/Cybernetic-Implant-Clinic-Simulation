package clinic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The Interface {@link Clinic} to define the general behavior of the clinic.
 * The clinic includes functionalities like loading data from a file,
 * registering patients, assigning rooms, and displaying a seating chart.
 */
public interface Clinic {
  /**
   * Loads clinic data from a file path.
   *
   * This method reads the clinic data from the specified file and initializes the
   * rooms, staff, and patients according to the file content. It is intended for
   * real-world use cases where the data comes from external files.
   *
   *
   * @param filePath The path to the clinic specification file.
   * @throws IOException If there is an error reading from the specified file path.
   */
  void loadClinicData(String filePath) throws IOException;

  /**
   * Loads clinic data from a {@link Readable} input source.
   *
   * This method provides more flexibility by allowing data to be read from various sources,
   * such as a {@link java.io.StringReader} for testing or a {@link java.io.FileReader} for
   * production use. It initializes the rooms, staff, and patients according to the input data.
   *
   *
   * @param input A {@code Readable} input source, such as a {@link java.io.StringReader}
   *              or {@link java.io.FileReader}.
   * @throws IOException If there is an error reading from the input source.
   */
  void loadClinicData(Readable input) throws IOException;

  /**
   * Retrieves the name of the clinic.
   *
   * @return The name of the clinic as a {@code String}.
   */

  String getClinicName();

  /**
   * Retrieves the list of rooms in the clinic.
   *
   * This method provides access to the list of rooms available in the clinic.
   * Each room contains details such as room type
   * (e.g., waiting room, exam room, procedure room), and name.
   * It can be used to obtain information about the clinic's layout
    or to manipulate room-related data.
   *
   * @return A {@code List<Room>} containing the room objects present in the clinic.
    If no rooms are available, an empty list is returned.
   */
  List<RoomInterface> getRooms();

  /**
   * Returns the list of staff members in the clinic.
   *
   * @return A list of staff members.
   */
  List<Staff> getStaff();

  /**
   * Sets the list of patients in the clinic.
   *
   * @param patients A list of {@link PatientInterface} objects representing the patients to be set.
   *                 Must not be {@code null}. The list can be empty if no patients are to be set.
   * @throws IllegalArgumentException if the provided list of patients is {@code null}.
   */
  public void setPatients(List<PatientInterface> patients);

  /**
   * Returns the list of patients in the clinic.
   *
   * @return A list of patients.
   */
  List<PatientInterface> getPatients();

  /**
   * Searches for a patient by their full name or patient ID.
   *
   * @param searchTerm The full name or patient ID to search for.
   * @return The {@link PatientInterface} object if found, null otherwise.
   */
  PatientInterface searchPatient(String searchTerm);

  /**
   * Registers a new patient or adds a new visit record for an existing patient.
   *
   * If a patient with the same first name, last name,
   * and date of birth already exists,
   * a new visit record is added to their visit history
   * without changing their existing ID
   * or personal details. If the patient does not exist,
   * a new patient is registered with
   * a unique ID, and they are assigned
   * to the primary waiting room (Room 1).
   *
   * @param firstName              The first name of the patient.
   * @param lastName               The last name of the patient.
   * @param dob                    The date of birth of the patient
   *                               (formatted as MM/dd/yyyy).
   * @param registrationDateAndTime The date and time of
   *                                the patient’s registration for the visit.
   * @param chiefComplaint         A description of
   *                               the patient’s chief complaint during the visit.
   * @param bodyTemperature        The patient’s
   *                               body temperature at the time of the visit.
   * @throws IllegalStateException If no rooms are available for assigning the patient.
   */
  void registerNewPatientVisit(String firstName,
                               String lastName,
                               String dob,
                               LocalDateTime registrationDateAndTime,
                               String chiefComplaint,
                               double bodyTemperature);

  void registerExistingPatientVisit(String firstName,
                                    String lastName, String dob,
                                    LocalDateTime registrationDateAndTime,
                                    String chiefComplaint,
                                    double bodyTemperature);

  /**
   * Sends a patient home. A staff member must approve the patient discharge.
   *
   * @param patient The patient to send home.
   * @param approver The clinical staff member approving the discharge.
   */
  void sendPatientHome(PatientInterface patient, ClinicalStaff approver);

  /**
   * Registers the clinical staff member in the clinic.
   *
   * @param staff The clinical staff member to be registered.
   */
  void registerClinicalStaff(ClinicalStaff staff);

  /**
   * Searches for a staff member
   * by their full name or identifier
   * (NPI for clinical staff or CPR for non-clinical staff).
   *
   * @param searchTerm The full name or identifier (NPI/CPR level) to search for.
   * @return The {@link Staff} object if found, null otherwise.
   */
  public List<Staff> searchStaff(String searchTerm);

  /**
   * Deactivates a clinical staff member in the clinic system.
   * After deactivation, the staff member will no longer be able to
   * perform any clinical duties or be assigned to patients.
   * This ensures that inactive staff members are no longer
   * part of the active workforce.
   *
   * @param staff The clinical staff member to be deactivated.
   *              This member must be registered
   *              in the clinic system and must not be null.
   *
   * @throws NullPointerException if the staff parameter is null.
   * @throws IllegalArgumentException if the staff member is not found in the clinic's records.
   */
  void deactivateStaff(ClinicalStaff staff);

  /**
   * Assigns a patient to a specific room by room number.
   *
   * @param roomNumber The number of the room to assign.
   * @param patientId The ID of the patient to assign to the room.
   */
  void assignRoom(int roomNumber, int patientId);

  /**
   * Removes the specified patient from their assigned room.
   *
   * @param patient The patient to remove from the room.
   */
  void removePatientFromRoom(PatientInterface patient);

  /**
   * Returns the room assignments of the clinic.
   * The map contains room numbers as keys
   * and lists of patients assigned to those rooms as values.
   *
   * @return A map of room assignments.
   */
  Map<Integer, List<? extends PatientInterface>> getRoomAssignments();

  /**
   * Retrieves a list of staff members assigned to the specified patient.
   * This method returns all the clinical staff
   * who are currently assigned to work with the given patient.
   * If no staff are assigned to the patient, an empty list is returned.
   *
   * @param patient The {@link PatientInterface} object representing
   *                the patient for whom to retrieve the assigned staff.
   * @return A list of {@link Staff} members assigned to the patient,
    or an empty list if no staff are assigned.
   */
  List<ClinicalStaff> getAssignedClinicalStaffForPatient(PatientInterface patient);

  /**
   * Assigns a clinical staff member to a patient.
   * This method associates a specific clinical staff member
   (e.g., physician, nurse) with a patient.
   * The staff member will be available for the patient's care.
   * If the patient has already been assigned staff members, the new staff member will be
   * added to the patient's list of assigned staff members.
   *
   * @param patient The {@code Patient} object
  representing the patient to whom the staff is being assigned.
   * @param staff The {@code ClinicalStaff} object
  representing the staff member being assigned to the patient.
   */
  void assignClinicalStaffToPatient(PatientInterface patient, ClinicalStaff staff);

  /**
   * Assigns multiple clinical staff members to a patient.
   * This method allows for the assignment of
   * several clinical staff members to a single patient.
   * The staff members are added to the patient's
   * list of assigned staff, which may already contain
   * other staff members. If any staff members have been deactivated,
   * they will not be added.
   *
   * @param patient The patient to whom the clinical staff members are assigned.
   *                This patient must be registered in the clinic system.
   * @param clinicalStaffMembers A list of {@link ClinicalStaff} members to
   *                             assign to the patient.
   *                             Any deactivated staff members
   *                             in this list will be skipped.
   * @throws NullPointerException If the patient or clinicalStaffMembers list is null.
   * @throws IllegalArgumentException If the patient is not registered in the clinic.
   */
  void assignMultipleClinicalStaffToPatient(PatientInterface patient,
                                            List<ClinicalStaff> clinicalStaffMembers);

  /**
   * Retrieves the map of patients to their assigned staff members.
   * This map stores patients as keys and lists of {@link Staff} members as values,
   * representing which staff members are assigned to each patient.
   *
   * @return A map where each key is a {@link PatientInterface} and
    each value is a {@code List()} of {@link Staff} assigned to that patient.
   */
  Map<PatientInterface, List<Staff>> getPatientStaffAssignments();

  /**
   * Retrieves detailed information about a specific room in the clinic.
   * This includes the patient assigned to the room
   * and any clinical staff assigned to that patient.
   * The information is returned as a formatted string.
   *
   * @param room The room for which information is being retrieved.
   *             This room must exist in the clinic and should not be null.
   *
   * @return A string containing information about the room,
    including the patient assigned to the room
    and the clinical staff members caring for the patient.
    If the room is empty or has no staff assigned, the string will indicate as much.
   *
   * @throws NullPointerException if the room parameter is null.
   * @throws IllegalArgumentException if the room does not exist in the clinic system.
   */
  String getRoomInfo(Room room);

  /**
   * Displays a seating chart that shows the assignment of patients to rooms.
   * This includes a text representation of each room and its occupants.
   */

  void displaySeatingChart();
}




