package clinic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The {@code ClinicImplementation} class provides the concrete implementation of
 * the {@link Clinic} interface. It manages the core operations of a clinic, including:
 * - Loading clinic data from a file or URL.
 * - Registering patients and staff.
 * - Assigning rooms to patients.
 * - Assigning clinical staff to patients.
 * - Managing patient discharges.
 * - Displaying seating charts and room information.
 *
 * This class provides the necessary methods to ensure the functioning of a typical
 * clinic environment.
 */
public class ClinicImplementation implements Clinic {
  private String clinicName;
  private final List<RoomInterface> rooms;
  private final List<Staff> staffList;
  private final List<PatientInterface> patients;
  // Room number -> List of patients
  private final Map<Integer, List<PatientInterface>> roomAssignments;
  // Patient -> List of assigned staff
  private final Map<PatientInterface, List<Staff>> patientStaffAssignments;
  // List of deactivated staff members
  private final List<Staff> deactivatedStaffList;

  /**
   * Constructs a new {@code ClinicImplementation} with the provided clinic name.
   *
   * @param clinicName The name of the clinic.
   * @throws IllegalArgumentException If the clinic name is null or empty.
   */
  public ClinicImplementation(String clinicName) {
    if (clinicName == null || clinicName.isBlank()) {
      throw new IllegalArgumentException("Clinic name cannot be null or empty.");
    }
    this.clinicName = clinicName;

    // Initialize the rooms and other instance variables
    this.rooms = new ArrayList<>();
    this.staffList = new ArrayList<>();
    this.patients = new ArrayList<>();
    this.roomAssignments = new HashMap<>();
    this.patientStaffAssignments = new HashMap<>();
    this.deactivatedStaffList = new ArrayList<>();
  }

  // No-argument constructor for testing purposes with a default clinic name
  public ClinicImplementation() {
    this("Default Clinic Name"); // Call the parameterized constructor
  }

  /**
   * Loads clinic data from the specified file path.
   * <p>
   * This method reads the clinic specification from the provided file path and initializes
   * the rooms, staff, and patients according to the content of the file.
   * It leverages the {@code loadClinicData(Readable)} method to handle the actual parsing logic.
   * </p>
   *
   * @param filePath The path to the clinic specification file.
   * @throws IOException If there is an error reading from the file.
   */
  @Override
  public void loadClinicData(String filePath) throws IOException {
      Readable input = getReadableFromPath(filePath);  // Handles both URL and local file
      loadClinicData(input);  // Delegate to the method that handles Readable input
  }

  /**
   * Loads clinic data from a {@link Readable} input source.
   *
   * This method allows the clinic data to be loaded from various input sources,
   * such as a {@link java.io.StringReader} or {@link java.io.FileReader}.
   * It parses the input to initialize the rooms, staff, and patients of the clinic.
   *
   *
   * @param input A {@code Readable} input source containing the clinic data.
   * @throws IOException If there is an error reading from the input source.
   */
  @Override
  public void loadClinicData(Readable input) throws IOException {
    BufferedReader br = new BufferedReader((Reader) input);
    String line;

    // Read and validate clinic name
    this.clinicName = br.readLine();
    if (this.clinicName == null || this.clinicName.isBlank()) {
      throw new IOException("Invalid Clinic Name: Clinic name is missing or invalid.");
    }

    // Load rooms
    line = br.readLine();
    if (line == null || line.isBlank()) {
      throw new IOException("Invalid Room Description: "
              + "Number of rooms is missing or invalid.");
    }
    int numRooms = Integer.parseInt(line.trim());
    for (int i = 0; i < numRooms; i++) {
      // Helper Method: loadRoom
      loadRoom(br.readLine(), i + 1);
    }

    // Load staff
    line = br.readLine();
    if (line == null || line.isBlank()) {
      throw new IOException("Invalid Staff Description: "
              + "Number of staff members is missing or invalid.");
    }
    int numStaff = Integer.parseInt(line.trim());
    for (int i = 0; i < numStaff; i++) {
      // Helper Method: loadStaff
      loadStaff(br.readLine());
    }

    // Load patients
    line = br.readLine();
    if (line == null || line.isBlank()) {
      throw new IOException("Invalid Patient Description: "
              + "Number of patients is missing or invalid.");
    }
    int numPatients = Integer.parseInt(line.trim());
    for (int i = 0; i < numPatients; i++) {
      // Helper Method: loadPatient
      loadPatient(br.readLine());
    }
  }

  /**
   * Helper Method to create a {@code Readable} from a URL or local file path.
   * If the path starts with {@code http://} or {@code https://},
   * it fetches the content as a URL.
   * For local paths, it normalizes separators and returns a {@link FileReader}.
   *
   * @param path The URL or local file path.
   * @return A {@link Readable} for the given resource.
   * @throws IOException If fetching or reading the resource fails.
   */
  private static Readable getReadableFromPath(String path) throws IOException {
    if (path.startsWith("http://") || path.startsWith("https://")) {
      URL url = new URL(path);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      // Check for a successful response
      if (connection.getResponseCode() != 200) {
        throw new IOException("Failed to fetch data. HTTP Response Code: "
                + connection.getResponseCode());
      }
      return new InputStreamReader(connection.getInputStream());
    } else {
      // Handle local file path with normalization
      path = path.replace("\\", "/");
      return new FileReader(new File(path)); // Local file path
    }
  }

  /**
   * Helper method to load a room from a string input.
   * Parses the room details and creates a {@link Room} object.
   *
   * @param roomDetails The string containing the room details.
   * @param roomNumber  The room number to assign to the room.
   * @throws IOException If the room data is invalid or incomplete.
   */
  private void loadRoom(String roomDetails, int roomNumber) throws IOException {
    // Split the room details into 6 parts
    String[] parts = roomDetails.split("\\s+", 6);

    // Validate the number of parts
    if (parts.length < 6) {
      throw new IOException("Invalid Room Description: Room data is incomplete.");
    }

    try {
      // Parse coordinates
      int x1 = Integer.parseInt(parts[0]);
      int y1 = Integer.parseInt(parts[1]);
      int x2 = Integer.parseInt(parts[2]);
      int y2 = Integer.parseInt(parts[3]);

      // Convert the roomType string to an enum
      RoomType roomType = RoomType.valueOf(parts[4].toUpperCase());

      // Use the room name directly as a string
      String roomName = parts[5];

      // Validate room type (optional: if you have predefined room types)
      if (!isValidRoomType(roomType)) {
        throw new IOException("Invalid Room Type: " + roomType);
      }

      // Create and assign the room using the room name as a string
      Room room = new Room(roomName, roomType, x1, y1, x2, y2);
      room.setRoomNumber(roomNumber);
      rooms.add(room); // Add the room to the rooms list
    } catch (NumberFormatException e) {
      throw new IOException("Invalid Room Coordinates: Coordinates must be integers.", e);
    } catch (IllegalArgumentException e) {
      throw new IOException(String.format("Invalid Room Type: %s",
              e.getMessage()), e);
    }
  }

  /**
   * Helper method to validate room types.
   * @return RoomType
   */

  private boolean isValidRoomType(RoomType roomType) {
    return roomType == RoomType.WAITING
            || roomType == RoomType.EXAM
            || roomType == RoomType.PROCEDURE;
  }

  /**
   * Helper method to load a staff member from a string input.
   * Parses the staff details and adds them to the staff list.
   *
   * @param staffDetails The string containing the staff details.
   * @throws IOException If the staff data is invalid or incomplete.
   */
  private void loadStaff(String staffDetails) throws IOException {
    String[] parts = staffDetails.split("\\s+");
    if (parts.length < 5) {
      throw new IOException("Invalid Staff Description: Staff data is incomplete.");
    }

    String jobTitleStr = parts[0];
    String firstName = parts[1];
    String lastName = parts[2];
    String educationLevelStr = parts[3];
    String identifier = parts[4];  // NPI for clinical, CPR Level for non-clinical

    // Convert the job title and education level from String to Enum
    JobTitle jobTitle;
    EducationLevel educationLevel;

    try {
      jobTitle = JobTitle.valueOf(jobTitleStr.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IOException(String.format("Invalid Job Title: %s",
              jobTitleStr), e);
    }

    try {
      educationLevel = EducationLevel.valueOf(educationLevelStr.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IOException(String.format("Invalid Education Level: %s",
              educationLevelStr), e);
    }

    // If the job title is reception (non-clinical), convert the identifier to CprLevel enum
    if (jobTitle == JobTitle.RECEPTION) {
      CprLevel cprLevel;
      try {
        cprLevel = CprLevel.valueOf(identifier.toUpperCase());
      } catch (IllegalArgumentException e) {
        throw new IOException(String.format("Invalid CPR Level: %s",
                identifier), e);
      }

      // Register non-clinical staff
      staffList.add(new NonClinicalStaff(firstName, lastName,
              jobTitle, educationLevel, cprLevel));
    } else {
      // Register clinical staff (physicians, nurses)
      staffList.add(new ClinicalStaff(firstName, lastName,
              jobTitle, educationLevel, identifier));
    }
  }

  /**
   * Helper method to load a patient from a string input.
   * Parses the patient details and assigns them to a room.
   *
   * @param patientDetails The string containing the patient details.
   * @throws IOException If the patient data is invalid or incomplete.
   */
  private void loadPatient(String patientDetails) throws IOException {
    String[] parts = patientDetails.split("\\s+");
    if (parts.length < 4) {
      throw new IOException("Invalid Patient Description: Patient data is incomplete.");
    }

    // Parse room number and validate it
    int roomNumber = Integer.parseInt(parts[0]);
    if (roomNumber <= 0 || roomNumber > rooms.size()) {
      throw new IOException(String.format("Invalid room number for patient: %s",
              roomNumber));
    }

    String firstName = parts[1];
    String lastName = parts[2];
    String dob = parts[3];
    int patientId = patients.size(); // Assign next available patient ID

    // Create a new patient object
    Patient patient = new Patient(firstName, lastName,
            patientId, dob, VisitStatus.IN_PROGRESS);
    RoomInterface room = rooms.get(roomNumber - 1); // Get the room by index

    // Handle non-waiting room assignments
    if (room.getRoomType() != RoomType.WAITING) {
      List<PatientInterface> assignedPatients = roomAssignments.get(roomNumber);
      if (assignedPatients != null && !assignedPatients.isEmpty()) {
        throw new IOException
                (String.format("Room %s is already occupied by another patient.",
                        roomNumber));
      }
    }

    // Add the patient to the clinic and assign them to the room
    patients.add(patient);
    room.assignPatient(patient);
    roomAssignments.computeIfAbsent(roomNumber, k -> new ArrayList<>()).add(patient);
  }

  /**
   * Returns the name of the clinic.
   *
   * @return The name of the clinic.
   */
  @Override
  public String getClinicName() {
    return clinicName;
  }

  /**
   * Gets the list of rooms in the clinic.
   *
   * @return the list of rooms.
   */
  @Override
  public List<RoomInterface> getRooms() {
    return rooms;
  }

  /**
   * Gets the list of staff in the clinic.
   *
   * @return the list of staff members.
   */
  @Override
  public List<Staff> getStaff() {
    return staffList;
  }

  /**
   * Sets the list of patients in the clinic by clearing the existing list
   * and adding all the provided patients to it.
   *
   * @param patients the list of patients to be added to the clinic.
   */
  @Override
  public void setPatients(List<PatientInterface> patients) {
    this.patients.clear();
    this.patients.addAll(patients);
  }

  /**
   * Gets the list of patients in the clinic.
   *
   * @return the list of patients.
   */
  @Override
  public List<PatientInterface> getPatients() {
    // Return the list of patients as List<PatientInterface>
    return new ArrayList<>(patients);
  }

  /**
   * Searches for a patient by their full name or patient ID.
   *
   * @param searchTerm The full name or patient ID to search for.
   * @return The {@link PatientInterface} object if found, null otherwise.
   */
  @Override
  public PatientInterface searchPatient(String searchTerm) {
    if (searchTerm == null || searchTerm.isBlank()) {
      System.out.println("Invalid search term: "
              + "The search term cannot be null or empty.");
      return null;
    }

    // Try to parse the searchTerm as an integer to search by ID.
    try {
      int patientId = Integer.parseInt(searchTerm.trim());
      // Search by patient ID
      for (PatientInterface patient : patients) {
        if (patient.getPatientId() == patientId) {
          return patient;
        }
      }
    } catch (NumberFormatException e) {
      // If it's not a number, we assume it's a name and search by full name
      for (PatientInterface patient : patients) {
        if (patient.getFullName().equalsIgnoreCase(searchTerm.trim())) {
          return patient;
        }
      }
    }

    // If no patient is found, return null and notify the user
    System.out.println("No patient found with the given search term.");
    return null;
  }

  /**
   * Registers a new patient or adds a visit record to an existing patient.
   *
   * If a patient with the same name and date of birth exists, a new visit record
   * is added to that patient. Otherwise, a new patient is registered, and the
   * visit record is created for them.
   *
   * @param firstName               The first name of the patient.
   * @param lastName                The last name of the patient.
   * @param dob                     The date of birth of the patient in "yyyy-MM-dd" format.
   * @param registrationDateAndTime The registration date and time of the visit.
   * @param chiefComplaint          The patient's chief complaint.
   * @param bodyTemperature         The patient's body temperature (in Celsius).
   */
  @Override
  public void registerNewPatientVisit(String firstName, String lastName, String dob,
                                      LocalDateTime registrationDateAndTime, String chiefComplaint,
                                      double bodyTemperature) {
    // Reuse the search functionality to ensure the patient doesn't already exist
    PatientInterface existingPatient = this.patients.stream()
            .filter(p -> p.getFirstName().equals(firstName)
                    && p.getLastName().equals(lastName)
                    && p.getDateOfBirth().equals(dob))
            .findFirst()
            .orElse(null);

    if (existingPatient != null) {
      System.out.println("Error: A patient with the same name and date of birth already exists.");
      return;
    }

    // Find the maximum patient ID currently in the list and assign the next ID
    int newPatientId = patients.stream()
            .map(PatientInterface::getPatientId)
            .max(Integer::compareTo)
            .orElse(0) + 1;

    // Ensure there is at least one room (Room 1) available for assignment
    if (rooms.isEmpty() || rooms.size() < 1) {
      throw new IllegalStateException("Error: No rooms available to assign the patient.");
    }

    // Validate and create the visit record, using VisitRecord's validation
    VisitRecord visitRecord
            = new VisitRecord(registrationDateAndTime, chiefComplaint, bodyTemperature);

    // Create the new patient
    Patient newPatient
            = new Patient(firstName, lastName,
            newPatientId, dob, VisitStatus.IN_PROGRESS);

    // Add the visit record to the patient's visit history
    newPatient.addVisitRecord(visitRecord);

    // Add the new patient to the list of patients
    this.patients.add(newPatient);

    // Assign the patient to Room 1 (primary waiting room)
    roomAssignments.computeIfAbsent(1, k -> new ArrayList<>()).add(newPatient);
    System.out.println("Registered a new patient: " + firstName + " " + lastName);
  }

  @Override
  public void registerExistingPatientVisit(String firstName, String lastName, String dob,
                                           LocalDateTime registrationDateAndTime,
                                           String chiefComplaint,
                                           double bodyTemperature) {
    // Find the patient using the search logic
    PatientInterface existingPatient = this.patients.stream()
            .filter(p -> p.getFirstName().equals(firstName)
                    && p.getLastName().equals(lastName)
                    && p.getDateOfBirth().equals(dob))
            .findFirst()
            .orElse(null);

    if (existingPatient == null) {
      System.out.println("Error: "
              + "No existing patient found with the provided name and date of birth.");
      return;
    }

    // Validate and create the visit record, using VisitRecord's validation
    VisitRecord visitRecord = new VisitRecord(registrationDateAndTime,
            chiefComplaint, bodyTemperature);

    // Add the visit record to the existing patient
    existingPatient.addVisitRecord(visitRecord);

    System.out.printf("Added a new visit record for the existing patient: %s %s%n",
            firstName, lastName);
  }



  /**
   * Sends a patient home from the clinic.
   * This method checks if the patient exists in the clinic's list of registered patients.
   * If the patient is found, they will be removed from any room assignments,
    and their discharge will be approved by a clinical staff member.
   The approver's details will be logged for auditing purposes.
   *
   * @param patient  The patient to be sent home. This patient must be registered
   *                 in the clinic system for the discharge to proceed.
   * @param approver The clinical staff member approving the discharge. This member
   *                 must be authorized to send patients home.
   *
   * @throws IllegalArgumentException if the patient is not found in the clinic's records.
   * @throws NullPointerException if either the patient or approver is null.
   */
  @Override
  public void sendPatientHome(PatientInterface patient, ClinicalStaff approver) {
    if (patient == null || approver == null) {
      throw new NullPointerException("Patient or approver cannot be null.");
    }

    if (!patients.contains(patient)) {
      throw new IllegalArgumentException("Patient not found in the clinic.");
    }

    // Ensure the approver is a physician
    if (approver.getJobTitle() != JobTitle.PHYSICIAN) {
      throw new IllegalArgumentException("Only physicians can approve discharge.");
    }

    // Remove the patient from any room assignment
    for (Map.Entry<Integer, List<PatientInterface>> entry : roomAssignments.entrySet()) {
      if (entry.getValue().contains(patient)) {
        entry.getValue().remove(patient);  // Remove patient from room assignment list
        rooms.get(entry.getKey() - 1).removePatient(patient);  // Remove from Room object
        System.out.printf("%s has been removed from room %s%n",
                patient.getFullName(), entry.getKey());
        break;
      }
    }

    // Set patient's visit status to DISCHARGED
    patient.setVisitStatus(VisitStatus.DISCHARGED);

    // Remove patient from clinic's active patient list
    if (patients.remove(patient)) {
      System.out.println(patient.getFullName()
              + " was sent home and removed from the clinic patient list.");
    }
  }

  /**
   * Registers a clinical staff member in the clinic system.
   * This method adds the staff member to the list of active clinical staff,
   * allowing them to perform duties and be assigned to patients.
   * A confirmation message is printed
   * once the staff member is successfully registered.
   *
   * @param staff The clinical staff member to be registered.
   *              The staff member must not be null
   *              and must contain valid information
   *              such as name and role within the clinic.
   *
   * @throws NullPointerException if the staff parameter is null.
   */
  @Override
  public void registerClinicalStaff(ClinicalStaff staff) {
    // Check if the staff member is null
    if (staff == null) {
      throw new NullPointerException("Staff member cannot be null.");
    }

    // Add the staff member to the staff list
    staffList.add(staff);
    System.out.printf("Staff member %s - %s has been registered.%n",
            staff.getFullName(), staff.getJobTitle());
  }

  /**
   * Searches for a staff member by their full name or identifier
   * (NPI for clinical staff or CPR level for non-clinical staff)
   * and returns the list of matching staff members.
   *
   * @param searchTerm The full name or identifier (NPI/CPR) to search for.
    @return A list of {@link Staff} objects matching the search term.
    If no matches are found, the list will be empty.
   */
  @Override
  public List<Staff> searchStaff(String searchTerm) {
    List<Staff> matchedStaff = new ArrayList<>();

    if (searchTerm == null || searchTerm.isBlank()) {
      System.out.println("Invalid search term: The search term cannot be null or empty.");
      return matchedStaff;
    }

    // Normalize the search term to handle case insensitivity and extra spaces
    searchTerm = searchTerm.trim().toLowerCase();

    for (Staff staff : staffList) {
      // Determine the identifier based on the staff type
      // (NPI for clinical staff or CPR level for non-clinical staff)
      String identifier = (staff instanceof ClinicalStaff)
              ? ((ClinicalStaff) staff).getNpi()
              : ((NonClinicalStaff) staff).getCprLevel();

      // Normalize both the full name and identifier for comparison
      String fullNameLower = staff.getFullName().toLowerCase();
      String identifierLower = (identifier != null) ? identifier.toLowerCase() : "";

      // Match either by full name or identifier
      if (fullNameLower.equals(searchTerm) || identifierLower.equals(searchTerm)) {
        matchedStaff.add(staff);
      }
    }

    return matchedStaff;
  }

  /**
   * Deactivates a clinical staff member in the clinic system. This method ensures that
    the staff member can no longer perform clinical duties by changing their active status.
   * A deactivated staff member will not be assigned to any new patients or tasks within the clinic.
   *
   * @param staff The clinical staff member to be deactivated. This staff member must be
   *              registered in the clinic system and should not be null.
   *
   * @throws NullPointerException if the staff parameter is null.
   * @throws IllegalArgumentException if the staff member is not found in the clinic's records.
   */
  @Override
  public void deactivateStaff(ClinicalStaff staff) {
    if (staff == null) {
      throw new NullPointerException("Staff member cannot be null.");
    }

    if (!staffList.contains(staff)) {
      throw new IllegalArgumentException("Staff member not found in the clinic system.");
    }

    // Add the staff to the deactivated staff list
    deactivatedStaffList.add(staff);

    // Log that the staff member has been deactivated
    System.out.printf("Staff member %s - %s has been deactivated.%n",
            staff.getFullName(), staff.getJobTitle());
  }

  /**
   * Assigns a patient to a specific room in the clinic.
   * - If the patient is already assigned to a room, they are removed from that room.
   * - If the room is a waiting room, multiple patients can be assigned.
   * - If the room is a non-waiting room (like an exam room), only one patient can occupy it.
   *
   * Preconditions:
   * - The room number should be valid (within range of existing rooms).
   * - The patient ID should be valid (patient must be registered).
   *
   * Postconditions:
   * - The patient is removed from any previously assigned room and assigned to the new one.
   * - If the room is full (non-waiting) or invalid, the assignment does not proceed.
   *
   * @param roomNumber The number of the room to assign the patient to.
   * @param patientId  The ID of the patient to be assigned to the room.
   */
  @Override
  public void assignRoom(int roomNumber, int patientId) {
    // Validate room number
    if (roomNumber <= 0 || roomNumber > rooms.size()) {
      System.out.printf("Error: Invalid room number %s.%n", roomNumber);
      return;
    }

    // Validate patient ID
    if (patientId < 0 || patientId >= patients.size()) {
      System.out.printf("Error: Invalid patient ID %s.%n", patientId);
      return;
    }

    // Retrieve patient and room objects
    PatientInterface patient = patients.get(patientId);
    RoomInterface newRoom = rooms.get(roomNumber - 1); // Adjust for 1-based index

    // Check if the patient is already in the new room
    List<PatientInterface> patientsInNewRoom = roomAssignments.get(roomNumber);
    if (patientsInNewRoom != null && patientsInNewRoom.contains(patient)) {
      System.out.println("Patient is already assigned to this room.");
      return;
    }

    // If the new room is non-waiting and already has a patient, do not assign
    if (newRoom.getRoomType() != RoomType.WAITING
            && patientsInNewRoom != null
            && !patientsInNewRoom.isEmpty()) {
      System.out.printf("Error: Room %d is already occupied by %s (Patient ID: %d).%n",
              roomNumber,
              patientsInNewRoom.get(0).getFullName(),
              patientsInNewRoom.get(0).getPatientId());
      return;
    }

    // Find and remove the patient from their current room (if assigned)
    removePatientFromRoom(patient);

    // Assign the patient to the new room
    patientsInNewRoom = roomAssignments.computeIfAbsent(roomNumber, k -> new ArrayList<>());
    patientsInNewRoom.add(patient);
    patient.setAssignedRoom(newRoom);

    System.out.printf("Patient %s (ID: %d) has been assigned to Room %d (%s).%n",
            patient.getFullName(), patient.getPatientId(), roomNumber, newRoom.getRoomName());
  }

  /**
   * Remove a patient from their currently assigned room.
   * This method scans all room assignments and removes the patient from
   * their current room, if assigned to one.
   *
   * @param patient The patient to remove from their current room.
   */
  @Override
  public void removePatientFromRoom(PatientInterface patient) {
    Integer originalRoomNumber = null;

    // Loop through the room assignments to find the room where the patient is assigned
    for (Map.Entry<Integer, List<PatientInterface>> entry : roomAssignments.entrySet()) {
      if (entry.getValue().contains(patient)) {
        originalRoomNumber = entry.getKey();
        break;
      }
    }

    // If the patient is assigned to a room, proceed to remove them
    if (originalRoomNumber != null) {
      List<PatientInterface> patientsInOriginalRoom = roomAssignments.get(originalRoomNumber);
      patientsInOriginalRoom.remove(patient);

      // Get the room object to check the room type
      RoomInterface room = rooms.get(originalRoomNumber - 1); // Adjust for 1-based index

      // If it's a non-waiting room and is now empty, remove the room from the map
      if (room.getRoomType() != RoomType.WAITING
              && patientsInOriginalRoom.isEmpty()) {
        roomAssignments.remove(originalRoomNumber);
      }

      System.out.printf("Patient %s (ID: %d) has been removed from room %d.%n",
              patient.getFullName(), patient.getPatientId(), originalRoomNumber);

      // Clear the patient's assigned room
      patient.setAssignedRoom(null);
    } else {
      System.out.println("Error: Patient is not assigned to any room.");
    }
  }

  /**
   * Gets the room assignments,
   * mapping room numbers to lists of assigned patients.
   *
   * @return a map of room assignments.
   */
  @Override
  public Map<Integer, List<? extends PatientInterface>> getRoomAssignments() {
    return new HashMap<>(roomAssignments);
  }

  /**
   * Retrieves the list of clinical staff assigned to a patient.
   *
   * @param patient The patient whose assigned clinical staff members you want to retrieve.
   * @return A list of clinical staff members assigned to the patient.
   */
  @Override
  public List<ClinicalStaff> getAssignedClinicalStaffForPatient(PatientInterface patient) {
    // Retrieve the assigned staff list
    List<Staff> staffList = patientStaffAssignments.getOrDefault(patient, new ArrayList<>());

    // Filter and cast the list to only include ClinicalStaff members
    List<ClinicalStaff> clinicalStaffList = new ArrayList<>();
    for (Staff staff : staffList) {
      if (staff instanceof ClinicalStaff) {
        clinicalStaffList.add((ClinicalStaff) staff);
      }
    }
    return clinicalStaffList;
  }

  /**
   * Assigns a clinical staff member to a patient.
   * This method associates a specific clinical staff member with a patient,
   * making the staff member responsible for the patient's care.
   * If the patient has previously been assigned other clinical staff members,
   * the new clinical staff member will be added to
   * the list of clinical staff assigned to the patient.
   *
   * The staff-to-patient assignment is stored in the {@code patientStaffAssignments} map.
   *
   * @param patient The {@link Patient} object
    representing the patient to whom the staff is being assigned.
   * @param staff The {@link ClinicalStaff} object
    representing the staff member being assigned to the patient.
   */
  @Override
  public void assignClinicalStaffToPatient(PatientInterface patient, ClinicalStaff staff) {
    // Ensure the clinical staff member has not been deactivated
    if (deactivatedStaffList.contains(staff)) {
      System.out.printf("Cannot assign deactivated staff member: %s%n",
              staff.getFullName());
      return;
    }

    // Ensure the patient has an entry in the map with List<Staff>
    patientStaffAssignments.computeIfAbsent(patient, k -> new ArrayList<>());

    // Add staff to the patient's assigned staff list
    patientStaffAssignments.get(patient).add(staff);
  }

  /**
   * Assigns multiple clinical staff members to a patient.
   * This method allows for the assignment of several clinical staff members to a single patient.
   * The staff members are added to the patient's list of assigned staff, which may already contain
   * other staff members. If any staff members have been deactivated, they will not be added.
   *
   * @param patient The patient to whom the staff members are assigned.
   * @param clinicalStaffMembers A list of clinical staff members to assign to the patient.
   */
  @Override
  public void assignMultipleClinicalStaffToPatient(PatientInterface patient,
                                           List<ClinicalStaff> clinicalStaffMembers) {
    // Ensure the patient has an entry in the map
    patientStaffAssignments.computeIfAbsent(patient, k -> new ArrayList<>());

    // Get the assigned staff list
    List<Staff> assignedStaff = patientStaffAssignments.get(patient);

    // Add each clinical staff member to the assigned staff list,
    // but skip any deactivated staff members
    for (ClinicalStaff staff : clinicalStaffMembers) {
      if (deactivatedStaffList.contains(staff)) {
        System.out.printf("Cannot assign deactivated staff member: %s%n",
                staff.getFullName());
      } else {
        assignedStaff.add(staff);
      }
    }
  }

  /**
   * Retrieves the mapping of patients to their assigned staff members.
   *
   * This method returns a map where each {@link Patient} is associated with a list of
   * {@link Staff} members. The map represents the staff assignments for the patients
   * currently in the clinic. Each patient can have zero or more staff members assigned to them.
   *
   * If a patient has no staff assigned, their entry in the map may contain an empty list or
   * the map may not have an entry for that patient at all.
   *
   * <p>This is useful when you need to see which staff members are currently attending to
   * each patient in the clinic. For example, in a healthcare setting, a doctor and nurse
   * may be assigned to a particular patient, and this method provides a way to retrieve
   * such information programmatically.
   *
   * This method is particularly useful when generating reports or visualizing seating charts,
   * allowing clinic administrators to check which staff members are attending to patients at any
   * given time
   *
   * @return A map containing {@link Patient} objects as keys and lists of {@link Staff} objects
   *         as values, representing the staff members assigned to each patient.
   *         The map may be empty if no patients have been assigned staff members.
   */
  @Override
  public Map<PatientInterface, List<Staff>> getPatientStaffAssignments() {
    // Return a defensive copy of the map to avoid external modification
    Map<PatientInterface, List<Staff>> copy = new HashMap<>();
    for (Map.Entry<PatientInterface, List<Staff>> entry : patientStaffAssignments.entrySet()) {
      copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
    }
    return copy;
  }

  /**
   * Retrieves detailed information about a specific room in the clinic.
   * This includes the patient assigned to the room
   * and any clinical staff members assigned to care for that patient.
   * The method formats the room information into a readable string.
   *
   * @param room The room for which information is being retrieved.
   *             The room must exist in the clinic and should not be null.
   *
   * @return A string containing detailed information about the room, including the patient
   *         assigned to the room and the clinical staff caring for them. If no patient is
   *         assigned, it will indicate the room is empty. If no staff is assigned to the patient,
   *         it will also reflect that. If the room does not exist, an empty string is returned.
   *
   * @throws NullPointerException if the room parameter is null.
   * @throws IllegalArgumentException if the room does not exist in the clinic system.
   */
  @Override
  public String getRoomInfo(Room room) {
    // Check if the room is null
    if (room == null) {
      throw new NullPointerException("Room cannot be null.");
    }

    // Check if the room exists in the clinic
    if (!rooms.contains(room)) {
      throw new IllegalArgumentException("Room not found in the clinic system.");
    }

    // Return the room info fetched from the room object
    return room.getRoomInfo();
  }

  /**
   * Displays a seating chart of the clinic.
   * This method prints the current seating chart,
    including the details of the rooms and the patients
   * assigned to each room. It also prints the staff assigned to each patient, if any.
   * The seating chart is formatted as follows:
   * - Room information (room type, name, and coordinates) is displayed first.
   * - If patients are assigned to the room, their details (first name, last name, DOB) are printed.
   * - If staff members are assigned to a patient,
   * their details (e.g., "Dr. Smith") are also printed.
   * - If no patients are assigned to the room, "No patients assigned" is printed.
   * - If no staff are assigned to a patient, "No staff assigned to this patient" is printed.
   *
   * Assumptions:
   * - Room numbers in the `roomAssignments` map are 1-based
   * (i.e., room 1 corresponds to index 0 in the rooms list).
   * - `roomAssignments` and `patientStaffAssignments` maps may contain `null` entries,
   * so null checks are performed.
   *
   */
  @Override
  public void displaySeatingChart() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("\n========== %s SEATING CHART ==========\n\n", clinicName));

    for (RoomInterface room : rooms) {
      sb.append(String.format("Room %s: %s %s %s%n",
              room.getRoomNumber(), room.getRoomType(),
              room.getRoomName(), room.getRoomCoordinates()));
      sb.append("------------------------------------------------------\n");

      List<PatientInterface> patientsInRoom = roomAssignments.get(room.getRoomNumber());
      if (patientsInRoom == null || patientsInRoom.isEmpty()) {
        sb.append("  No patients assigned.\n");
      } else {
        for (PatientInterface patient : patientsInRoom) {
          // Check if the patient has been discharged before displaying
          if (patient.getVisitStatus() != VisitStatus.DISCHARGED) {
            sb.append(String.format("Patient: \n\t%s (Patient ID: %s , DOB: %s)%n",
                    patient.getFullName(), patient.getPatientId(), patient.getDateOfBirth()));

            // Visit records
            List<VisitRecord> visitRecords = patient.getVisitRecords();
            if (visitRecords.isEmpty()) {
              sb.append("\tNo visit records found.\n");
            } else {
              sb.append("\tVisit Records:\n");
              for (VisitRecord record : visitRecords) {
                sb.append(String.format("\t\tRegistration: %s, Chief Complaint: %s, " +
                                "Body Temperature: %.1f%n",
                        record.getFormattedRegistrationDateTime(),
                        record.getChiefComplaint(),
                        record.getBodyTemperature()));
              }
            }

            // Assigned staff for patient
            List<Staff> assignedStaff = patientStaffAssignments.get(patient);
            if (assignedStaff == null || assignedStaff.isEmpty()) {
              sb.append("\tNo staff assigned to this patient.\n");
            } else {
              sb.append("\tAssigned Staff:\n");
              for (Staff staff : assignedStaff) {
                if (staff instanceof ClinicalStaff) {
                  ClinicalStaff clinicalStaff = (ClinicalStaff) staff;
                  String title = clinicalStaff.getJobTitle()
                          == JobTitle.PHYSICIAN ? "Dr." : "Nurse";
                  sb.append(String.format("\t\t%s %s (%s: %s)%n",
                          title, clinicalStaff.getFullName(),
                          clinicalStaff.identifierType, clinicalStaff.identifierValue));
                }
              }
            }
          }
        }
      }
      sb.append("--------------------------------------------\n\n");
    }

    sb.append("========== END OF SEATING CHART ==========\n");
    System.out.println(sb.toString());
  }

  /**
   * Compares two ClinicImplementation objects for equality.
   * Two clinics are considered equal if they have the same rooms, staff, and patients.
   *
   * @param o The other object to compare.
   * @return True if the clinics have the same rooms, staff, and patients, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClinicImplementation that = (ClinicImplementation) o;

    // Compare each field, ensuring lists are compared deeply
    return Objects.equals(clinicName, that.clinicName)
            && Objects.equals(rooms, that.rooms)
            && Objects.equals(staffList, that.staffList)
            && Objects.equals(patients, that.patients)
            && Objects.equals(roomAssignments, that.roomAssignments)
            && Objects.equals(patientStaffAssignments, that.patientStaffAssignments)
            && Objects.equals(deactivatedStaffList, that.deactivatedStaffList);
  }

  /**
   * Generates a hash code for the clinic based on its rooms, staff, and patients.
   * The hash code is generated based on the clinic's rooms, staff, and patients.
   *
   * @return The hash code of the clinic.
   */
  @Override
  public int hashCode() {
    return Objects.hash(
            clinicName,
            rooms,
            staffList,
            patients,
            roomAssignments,
            patientStaffAssignments,
            deactivatedStaffList
    );
  }
}
