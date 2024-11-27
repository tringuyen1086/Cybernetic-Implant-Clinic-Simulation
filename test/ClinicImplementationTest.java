import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import clinic.ClinicImplementation;
import clinic.ClinicalStaff;
import clinic.EducationLevel;
import clinic.JobTitle;
import clinic.PatientInterface;
import clinic.Room;
import clinic.RoomInterface;
import clinic.RoomType;
import clinic.VisitRecord;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link ClinicImplementation} class.
 * This test class covers the main functionalities of the clinic system,
 * ensuring correctness and
 * robustness of each method.
 */
public class ClinicImplementationTest {

  private ClinicImplementation clinic;
  private ClinicImplementation clinic1;
  private ClinicImplementation clinic2;
  private LocalDate dob;
  private LocalDateTime registrationDateTime;

  /**
   * Setup the clinic instance before each test.
   * Initialize date of birth and registration date/time for tests.
   */
  @Before
  public void setUp() {
    clinic = new ClinicImplementation("Test Clinic");
    dob = LocalDate.of(1990, 1, 1);
    registrationDateTime = LocalDateTime.now();

    // Add rooms to the clinic to avoid "No rooms available" errors.
    RoomInterface waitingRoom = new Room("Front Waiting Room",
            RoomType.WAITING, 0, 0, 10, 10);
    waitingRoom.setRoomNumber(1);
    RoomInterface examRoom = new Room("Exam Room 1",
            RoomType.EXAM, 10, 10, 20, 20);
    examRoom.setRoomNumber(2);

    clinic.getRooms().add(waitingRoom);
    clinic.getRooms().add(examRoom);
  }

  /**
   * Helper method to create a Readable from a URL.
   *
   * @param urlString The URL as a string.
   * @return A BufferedReader as the Readable.
   * @throws IOException If an I/O error occurs while opening the connection.
   */
  private Readable getReadableFromUrl(String urlString) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    if (connection.getResponseCode() != 200) {
      throw new IOException("Failed to fetch data. HTTP Response Code: "
              + connection.getResponseCode());
    }

    return new BufferedReader(new InputStreamReader(connection.getInputStream()));
  }

  /**
   * Test loading clinic data from a URL.
   * Verifies that clinic name, rooms, staff, and patients are parsed correctly.
   */
  @Test
  public void testLoadClinicDataFromUrl() throws IOException {
    String url
            = "https://drive.google.com/uc?export=download&id=19o_vIAUEXyqZr951AIK7jECKzwHCQdzq";

    // Use the helper method to get the Readable from the URL
    Readable reader = getReadableFromUrl(url);
    clinic.loadClinicData(reader);

    // Verify the loaded data
    // The Clinic Name was intentionally changed compare to
    // the original sample file
    // Make sure the new clinic name is being parsed correctly
    // All other info from the sample clinic are the same
    assertEquals("Cybernetic Implant Clinic Testing",
            clinic.getClinicName());

    // Verify rooms
    List<RoomInterface> rooms = clinic.getRooms();
    assertEquals(7, rooms.size()); // 5 room from file parsing + 2 added in setup
    assertEquals("Front Waiting Room", rooms.get(0).getRoomName());
    assertEquals("Exam Room 1", rooms.get(1).getRoomName());

    // Verify staff
    assertEquals(6, clinic.getStaff().size());


    // Verify patient
    assertEquals(7, clinic.getPatients().size());
    assertEquals("Aandi Acute", clinic.getPatients().get(0).getFullName());
  }

  /**
   * Test loading clinic data with missing clinic name.
   * Verifies that an IOException is thrown for invalid clinic description.
   */
  @Test(expected = IOException.class)
  public void testInvalidClinicDescription() throws IOException {
    String invalidClinicData = "\n"
            + "2\n"
            + "0 0 10 10 WAITING Front Waiting Room\n";

    StringReader reader = new StringReader(invalidClinicData);
    clinic.loadClinicData(reader);
  }

  /**
   * Test loading clinic data with invalid room information.
   * Verifies that an IOException is thrown for invalid or incomplete room data.
   */
  @Test(expected = IOException.class)
  public void testInvalidRoomDescription_MissingData() throws IOException {
    String invalidRoomData = "Sample Clinic\n"
            + "2\n"
            + "0 0 10 10 WAITING\n"; // Missing room name

    StringReader reader = new StringReader(invalidRoomData);
    clinic.loadClinicData(reader);
  }

  /**
   * Test loading clinic data with invalid staff information.
   * Verifies that an IOException is thrown for missing or incorrect staff data.
   */
  @Test(expected = IOException.class)
  public void testInvalidStaffDescription_MissingData() throws IOException {
    String invalidStaffData = "Sample Clinic\n"
            + "1\n"
            + "0 0 10 10 WAITING Front Waiting Room\n"
            + "2\n"
            + "Physician John\n"; // Missing last name and other details

    StringReader reader = new StringReader(invalidStaffData);
    clinic.loadClinicData(reader);
  }

  /**
   * Test registering a new patient
   * and ensuring the patient is properly assigned to the clinic.
   * Validate that the patient is correctly assigned to Room 1
   * (primary waiting room).
   */
  @Test
  public void testRegisterNewPatientVisit() {
    // Register a new patient
    clinic.registerNewPatientVisit("John", "Doe", dob.toString(),
            registrationDateTime, "Flu symptoms", 37.5);

    // Verify the patient is registered in the clinic
    List<PatientInterface> patients = clinic.getPatients();
    assertEquals(1, patients.size());

    // Verify the visit record is added
    PatientInterface patient = patients.get(0);
    assertEquals("John", patient.getFirstName());
    assertEquals("Doe", patient.getLastName());
    assertEquals(dob.toString(), patient.getDateOfBirth());

    // Check visit record details
    List<VisitRecord> visitRecords = patient.getVisitRecords();
    assertEquals(1, visitRecords.size());
    VisitRecord visitRecord = visitRecords.get(0);
    assertEquals("Flu symptoms", visitRecord.getChiefComplaint());
    assertEquals("37.5 °C", visitRecord.getBodyTemperature());

    // Check that the patient is assigned to Room 1
    Map<Integer, List<? extends PatientInterface>> roomAssignments
            = clinic.getRoomAssignments();
    List<? extends PatientInterface> room1Patients
            = roomAssignments.get(1);
    assertNotNull(room1Patients);
    assertTrue(room1Patients.contains(patient));
  }

  // Add other tests below

  /**
   * Test assigning multiple clinical staff members to a patient.
   */
  @Test
  public void testAssignMultipleClinicalStaffToPatient() {
    // Register a new patient
    clinic.registerNewPatientVisit("Peter", "Parker", dob.toString(),
            registrationDateTime, "Spider bite", 38.0);

    // Create and register clinical staff
    ClinicalStaff physician = new ClinicalStaff("Dr.",
            "Strange",
            JobTitle.PHYSICIAN,
            EducationLevel.DOCTORAL,
            "1234567890");
    ClinicalStaff nurse = new ClinicalStaff("Nurse",
            "Jane",
            JobTitle.NURSE,
            EducationLevel.MASTERS,
            "2345678901");
    clinic.registerClinicalStaff(physician);
    clinic.registerClinicalStaff(nurse);

    // Assign multiple clinical staff to the patient
    PatientInterface patient = clinic.searchPatient("Peter Parker");
    clinic.assignMultipleClinicalStaffToPatient(patient,
            List.of(physician, nurse));

    // Verify that the patient has both clinical staff members assigned
    List<ClinicalStaff> assignedStaff
            = clinic.getAssignedClinicalStaffForPatient(patient);
    assertTrue(assignedStaff.contains(physician));
    assertTrue(assignedStaff.contains(nurse));
  }


  /**
   * Test assigning no staff to a patient.
   * Ensures that the system handles patients
   * without assigned staff correctly.
   */
  @Test
  public void testNoStaffAssignedToPatient() {
    // Register a new patient
    clinic.registerNewPatientVisit("Chris",
            "Evans",
            dob.toString(),
            registrationDateTime,
            "Back pain",
            36.5);

    // Verify no staff is assigned initially
    PatientInterface patient
            = clinic.searchPatient("Chris Evans");
    List<ClinicalStaff> assignedStaff
            = clinic.getAssignedClinicalStaffForPatient(patient);
    assertTrue(assignedStaff.isEmpty());
  }


  /**
   * Test adding a visit record for an existing patient.
   * Ensure that the visit record is added
   * without changing the patient's ID.
   */
  @Test
  public void testRegisterExistingPatientVisit() {
    // Register a new patient first
    clinic.registerNewPatientVisit("Jane",
            "Doe",
            dob.toString(),
            registrationDateTime,
            "Cold",
            36.8);

    // Add another visit record for the existing patient
    clinic.registerExistingPatientVisit("Jane",
            "Doe",
            dob.toString(),
            LocalDateTime.now(),
            "Follow-up",
            37.0);

    // Verify that there is still only one patient
    List<PatientInterface> patients = clinic.getPatients();
    assertEquals(1, patients.size());

    // Verify that the existing patient has two visit records now
    PatientInterface patient = patients.get(0);
    List<VisitRecord> visitRecords = patient.getVisitRecords();
    assertEquals(2, visitRecords.size());

    // Verify the second visit record
    VisitRecord secondVisit = visitRecords.get(1);
    assertEquals("Follow-up", secondVisit.getChiefComplaint());
    assertEquals("37.0 °C", secondVisit.getBodyTemperature());
  }

  /**
   * Test assigning a patient to a room
   * and ensuring that the room is properly assigned.
   * Validate that non-waiting rooms cannot have more than one patient.
   */
  @Test
  public void testAssignRoom() {
    // Register a new patient
    clinic.registerNewPatientVisit("Alice",
            "Smith",
            dob.toString(),
            registrationDateTime,
            "Headache",
            37.2);

    // Assign the patient to Room 2
    clinic.assignRoom(2, 0);

    // Verify the patient is assigned to Room 2
    Map<Integer, List<? extends PatientInterface>> roomAssignments
            = clinic.getRoomAssignments();
    List<? extends PatientInterface> room2Patients
            = roomAssignments.get(2);
    assertNotNull(room2Patients);
    assertEquals(1, room2Patients.size());

    // Check that Room 2 cannot accept another patient if it's non-waiting
    clinic.registerNewPatientVisit("Bob",
            "Smith",
            dob.toString(),
            registrationDateTime,
            "Fever",
            38.5);
    clinic.assignRoom(2, 1); // This should fail for non-waiting rooms

    // Verify that Bob Smith is not assigned to Room 2
    room2Patients = roomAssignments.get(2);
    assertEquals(1, room2Patients.size());
  }

  /**
   * Test searching for a patient by name.
   * Ensure that searching by full name returns the correct patient.
   */
  @Test
  public void testSearchPatientByName() {
    // Register a new patient
    clinic.registerNewPatientVisit("Chris",
            "Evans",
            dob.toString(),
            registrationDateTime,
            "Back pain",
            36.5);

    // Search for the patient by full name
    PatientInterface foundPatient = clinic.searchPatient("Chris Evans");
    assertNotNull(foundPatient);
    assertEquals("Chris", foundPatient.getFirstName());
    assertEquals("Evans", foundPatient.getLastName());
  }

  /**
   * Test searching for a patient by ID.
   * Ensure that searching by patient ID returns the correct patient.
   */
  @Test
  public void testSearchPatientById() {
    // Register a new patient
    clinic.registerNewPatientVisit("Emma",
            "Watson",
            dob.toString(),
            registrationDateTime,
            "Stomach ache",
            36.7);

    // Retrieve the registered patient and get their ID
    List<PatientInterface> patients = clinic.getPatients();
    assertEquals(1, patients.size());  // Ensure there is only one patient
    PatientInterface registeredPatient = patients.get(0);  // Get the registered patient
    String patientId = String.valueOf(registeredPatient.getPatientId());

    // Search for the patient by ID
    PatientInterface foundPatient = clinic.searchPatient(patientId);
    assertNotNull(foundPatient);
    assertEquals("Emma", foundPatient.getFirstName());
    assertEquals("Watson", foundPatient.getLastName());
  }

  /**
   * Test sending a patient home and verifying
   * that they are removed from the clinic's active list.
   */
  @Test
  public void testSendPatientHome() {
    // Register a new patient
    clinic.registerNewPatientVisit("Lily",
            "Collins",
            dob.toString(),
            registrationDateTime,
            "Migraine",
            37.0);

    // Search for the patient
    PatientInterface patient = clinic.searchPatient("Lily Collins");
    assertNotNull(patient);

    // Send the patient home (approved by a physician)
    ClinicalStaff physician = new ClinicalStaff("Dr.",
            "Strange",
            JobTitle.PHYSICIAN,
            EducationLevel.DOCTORAL,
            "1234567890");
    clinic.sendPatientHome(patient, physician);

    // Verify the patient is no longer assigned to any room
    assertNull(patient.getAssignedRoom());

    // Verify the patient is removed from the active patient list
    List<PatientInterface> patients = clinic.getPatients();
    assertFalse(patients.contains(patient));
  }

  /**
   * Test assigning a clinical staff member to a patient.
   * Ensure that the staff member is properly assigned
   * and available in the patient's staff list.
   */
  @Test
  public void testAssignClinicalStaffToPatient() {
    // Register a new patient
    clinic.registerNewPatientVisit("Tom",
            "Hardy",
            dob.toString(),
            registrationDateTime,
            "Sore throat",
            37.3);

    // Assign a physician to the patient
    ClinicalStaff physician = new ClinicalStaff("Dr.",
            "House",
            JobTitle.PHYSICIAN,
            EducationLevel.DOCTORAL,
            "9876543210");
    clinic.registerClinicalStaff(physician);
    clinic.assignClinicalStaffToPatient(clinic
            .searchPatient("Tom Hardy"), physician);

    // Verify that the patient has the assigned staff member
    PatientInterface patient = clinic.searchPatient("Tom Hardy");
    List<ClinicalStaff> assignedStaff
            = clinic.getAssignedClinicalStaffForPatient(patient);
    assertTrue(assignedStaff.contains(physician));
  }

  /**
   * Test the equals method to
   * verify two clinic instances with the same data are equal.
   */
  @Test
  public void testEqualsForClinicImplementation() throws IOException {
    // Create first clinic instance and add data
    ClinicImplementation clinic1
            = new ClinicImplementation("Test Clinic 1");
    RoomInterface waitingRoom1
            = new Room("Front Waiting Room",
            RoomType.WAITING, 0, 0, 10, 10);
    RoomInterface examRoom1
            = new Room("Exam Room 1",
            RoomType.EXAM, 10, 10, 20, 20);
    clinic1.getRooms().add(waitingRoom1);
    clinic1.getRooms().add(examRoom1);
    ClinicalStaff physician1
            = new ClinicalStaff("Dr.", "John",
            JobTitle.PHYSICIAN, EducationLevel.DOCTORAL, "1234567890");
    clinic1.registerClinicalStaff(physician1);
    clinic1.registerNewPatientVisit("Jane", "Doe", dob.toString(),
            registrationDateTime, "Cold", 36.8);

    // Create second clinic instance and add the same data
    ClinicImplementation clinic2
            = new ClinicImplementation("Test Clinic 1");
    RoomInterface waitingRoom2
            = new Room("Front Waiting Room",
            RoomType.WAITING, 0, 0, 10, 10);
    RoomInterface examRoom2
            = new Room("Exam Room 1",
            RoomType.EXAM, 10, 10, 20, 20);
    clinic2.getRooms().add(waitingRoom2);
    clinic2.getRooms().add(examRoom2);
    ClinicalStaff physician2
            = new ClinicalStaff("Dr.", "John",
            JobTitle.PHYSICIAN, EducationLevel.DOCTORAL, "1234567890");
    clinic2.registerClinicalStaff(physician2);
    clinic2.registerNewPatientVisit("Jane", "Doe", dob.toString(),
            registrationDateTime, "Cold", 36.8);

    // Verify both clinics are equal
    assertEquals(clinic1, clinic2);

    // Modify clinic2 to ensure they are not equal
    clinic2.registerNewPatientVisit("John", "Smith", dob.toString(),
            registrationDateTime, "Fever", 37.0);

    // Assert that the clinics are now different
    assertNotEquals(clinic1, clinic2);

    // Change the name of clinic2 to ensure they are not equal
    clinic2 = new ClinicImplementation("Test Clinic 2");
    assertNotEquals(clinic1, clinic2);
  }

  /**
   * Test the hashCode method to verify two clinic instances
   * with the same data have the same hash code.
   */
  @Test
  public void testHashCodeForClinicImplementation() throws IOException {
    ClinicImplementation clinic1
            = new ClinicImplementation("Test Clinic");
    ClinicImplementation clinic2
            = new ClinicImplementation("Test Clinic");

    // Create and add a room to both clinics
    Room room = new Room("Front Waiting Room",
            RoomType.WAITING, 0, 0, 10, 10);
    clinic1.getRooms().add(room);
    clinic2.getRooms().add(room);

    // Create a clinical staff member
    ClinicalStaff physician
            = new ClinicalStaff("John", "Doe",
            JobTitle.PHYSICIAN, EducationLevel.DOCTORAL, "1234567890");

    // Add the same staff member to both clinics
    clinic1.registerClinicalStaff(physician);
    clinic2.registerClinicalStaff(new ClinicalStaff("John", "Doe",
            JobTitle.PHYSICIAN, EducationLevel.DOCTORAL, "1234567890"));

    // Add the same patient to both clinics
    clinic1.registerNewPatientVisit("Jane", "Doe", dob.toString(),
            registrationDateTime, "Cold", 36.8);
    clinic2.registerNewPatientVisit("Jane", "Doe", dob.toString(),
            registrationDateTime, "Cold", 36.8);

    // Ensure the hash codes are equal after setup
    assertEquals(clinic1.hashCode(), clinic2.hashCode());

    // Modify clinic2 to ensure they are not equal
    clinic2.registerNewPatientVisit("John", "Smith", dob.toString(),
            registrationDateTime, "Flu", 37.5);

    // Assert that the hash codes are now different
    assertNotEquals(clinic1.hashCode(), clinic2.hashCode());
  }

  /**
   * Tests the ability to assign a clinical staff member to multiple patients.
   * Verifies that the physician is correctly assigned to both patients.
   */
  @Test
  public void testAssignClinicalStaffToMultiplePatients() {
    // Register two new patients (Alice and Bob)
    clinic.registerNewPatientVisit("Alice", "Smith", dob.toString(),
            registrationDateTime, "Headache", 37.2);
    clinic.registerNewPatientVisit("Bob", "Smith", dob.toString(),
            registrationDateTime, "Fever", 38.5);

    // Create and register a clinical staff member
    ClinicalStaff physician = new ClinicalStaff("Dr.", "House",
            JobTitle.PHYSICIAN, EducationLevel.DOCTORAL, "9876543210");
    clinic.registerClinicalStaff(physician);

    // Assign the clinical staff member to both patients
    clinic.assignClinicalStaffToPatient(clinic.searchPatient("Alice Smith"), physician);
    clinic.assignClinicalStaffToPatient(clinic.searchPatient("Bob Smith"), physician);

    // Verify that the clinical staff member is assigned to both patients
    List<ClinicalStaff> aliceStaff = clinic
            .getAssignedClinicalStaffForPatient(clinic.searchPatient("Alice Smith"));
    List<ClinicalStaff> bobStaff = clinic
            .getAssignedClinicalStaffForPatient(clinic.searchPatient("Bob Smith"));

    assertTrue(aliceStaff.contains(physician));
    assertTrue(bobStaff.contains(physician));
  }
}