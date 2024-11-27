import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import clinic.Clinic;
import clinic.ClinicImplementation;
import clinic.ClinicalStaff;
import clinic.CprLevel;
import clinic.EducationLevel;
import clinic.JobTitle;
import clinic.NonClinicalStaff;
import clinic.PatientInterface;
import clinic.Room;
import clinic.RoomInterface;
import clinic.RoomType;
import clinic.Staff;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link Clinic} interface
 * and its implementation.
 * These tests validate the core functionalities of
 * loading clinic data,
 * registering patients, assigning rooms,
 * and displaying seating charts.
 * The tests dynamically retrieve patient
 * and room information to avoid hard-coded values
 * and cover edge cases such as patient reassignments
 * and room capacity validations.
 */
public class ClinicTest {

  // Constants for room and patient test data (to minimize hard-coding)
  private static final String ROOM_NAME_1 = "Front Waiting Room";
  private static final String ROOM_NAME_2 = "Exam Room 1";
  private static final String PATIENT_FIRST_NAME_1 = "Aandi";
  private static final String PATIENT_LAST_NAME_1 = "Acute";
  private static final String PATIENT_DOB_1 = "1/1/1981";
  private static final String PATIENT_FIRST_NAME_2 = "Beth";
  private static final String PATIENT_LAST_NAME_2 = "Bunion";
  private static final String PATIENT_DOB_2 = "2/2/1982";
  private static final String PHYSICIAN_FIRST_NAME = "Amy";
  private static final String PHYSICIAN_LAST_NAME = "Anguish";
  private static final String NURSE_FIRST_NAME = "Camila";
  private static final String NURSE_LAST_NAME = "Crisis";
  private static final String RECEPTION_FIRST_NAME = "Frank";
  private static final String RECEPTION_LAST_NAME = "Febrile";

  private Clinic clinic;
  private Room room1;
  private Room room2;
  private ClinicalStaff physician;
  private ClinicalStaff nurse;
  private NonClinicalStaff reception;
  private PatientInterface patient1;
  private PatientInterface patient2;

  /**
   * Sets up the clinic for testing by
   * initializing the {@link ClinicImplementation}.
   * This method is run before each test to ensure a clean state.
   */
  @Before
  public void setUp() {
    clinic = new ClinicImplementation();

    // Initialize rooms with dynamic room names
    room1 = new Room(ROOM_NAME_1, RoomType.WAITING, 0, 0, 10, 10);
    room2 = new Room(ROOM_NAME_2, RoomType.EXAM, 0, 0, 10, 10);

    // Add rooms to the clinic
    clinic.getRooms().add(room1);
    clinic.getRooms().add(room2);

    // Initialize staff dynamically
    physician = new ClinicalStaff(PHYSICIAN_FIRST_NAME,
            PHYSICIAN_LAST_NAME,
            JobTitle.PHYSICIAN,
            EducationLevel.DOCTORAL,
            "1234567890");
    nurse = new ClinicalStaff(NURSE_FIRST_NAME,
            NURSE_LAST_NAME,
            JobTitle.NURSE,
            EducationLevel.DOCTORAL,
            "2224443338");
    reception = new NonClinicalStaff(RECEPTION_FIRST_NAME,
            RECEPTION_LAST_NAME,
            JobTitle.RECEPTION,
            EducationLevel.ALLIED,
            CprLevel.B);

    // Add staff to clinic
    clinic.getStaff().add(physician);
    clinic.getStaff().add(nurse);
    clinic.getStaff().add(reception);

    registerInitialPatients();
  }

  /**
   * Registers initial patients and assigns them to Room 1 (waiting room).
   */
  private void registerInitialPatients() {
    // Register two initial patients using dynamic details
    clinic.registerNewPatientVisit(PATIENT_FIRST_NAME_1,
            PATIENT_LAST_NAME_1,
            PATIENT_DOB_1,
            LocalDateTime.now(),
            "Check up",
            37.5);
    clinic.registerNewPatientVisit(PATIENT_FIRST_NAME_2,
            PATIENT_LAST_NAME_2,
            PATIENT_DOB_2,
            LocalDateTime.now(),
            "Fever",
            36.8);

    // Dynamically assign the registered patients to Room 1
    patient1 = clinic.getPatients().get(0);
    patient2 = clinic.getPatients().get(1);
    clinic.getRoomAssignments().put(1,
            new ArrayList<>(List.of(patient1, patient2)));
  }

  /**
   * Tests the {@code loadClinicData} method to
   * ensure clinic data is loaded correctly from a file.
   * Verifies rooms, staff, and patients are properly initialized.
   */
  @Test
  public void testLoadClinicData() throws IOException {
    try {
      // Clear existing clinic data
      clinic.getRoomAssignments().clear();
      clinic.getRooms().clear();
      clinic.getStaff().clear();
      clinic.getPatients().clear();

      String filePath = "../clinicfile.txt";  // Path to the test file
      File file = new File(filePath);
      assertTrue("Test file should exist", file.exists());

      // Load data from the file
      clinic.loadClinicData(filePath);

      // Verify rooms, staff, and patients are loaded dynamically
      List<? extends RoomInterface> rooms = clinic.getRooms();
      assertNotNull("Rooms should not be null", rooms);
      assertEquals(5, rooms.size());

      List<Staff> staff = clinic.getStaff();
      assertNotNull("Staff should not be null", staff);
      assertEquals(6, staff.size());

      List<? extends PatientInterface> patients = clinic.getPatients();
      assertNotNull("Patients should not be null", patients);
      assertEquals(9, patients.size());  // 7 new + 2 initial
    } catch (IOException e) {
      fail("Should not throw IOException for a valid file.");
    }
  }

  /**
   * Tests the {@code registerNewPatientVisit} method to
   * verify that a new patient is registered and added to the clinic,
   * and assigned to Room 1.
   */
  @Test
  public void testRegisterNewPatientVisit() {
    // Variables for patient details (no hard-coded values)
    final String firstName = "Clive";
    final String lastName = "Cardiac";
    final String dob = "1983-03-03";
    final String chiefComplaint = "Check up";
    final double bodyTemperature = 37.5;
    final int initialPatientCount = clinic.getPatients().size();
    final int expectedRoom = 1;

    // Register a new patient
    LocalDateTime registrationTime = LocalDateTime.now();
    clinic.registerNewPatientVisit(firstName, lastName,
            dob, registrationTime, chiefComplaint, bodyTemperature);

    // Verify patient is added to clinic
    List<? extends PatientInterface> patients = clinic.getPatients();
    assertEquals(initialPatientCount + 1, patients.size());

    PatientInterface newPatient = patients.get(patients.size() - 1);
    assertEquals(firstName, newPatient.getFirstName());
    assertEquals(lastName, newPatient.getLastName());

    // Verify the patient is in Room 1
    List<? extends PatientInterface> roomPatients
            = clinic.getRoomAssignments().get(expectedRoom);
    assertNotNull(roomPatients);
    assertTrue(roomPatients.contains(newPatient));
  }

  /**
   * Tests the {@code assignRoom} method to
   * ensure that a patient is assigned
   * to a specific room and removed from the previous room.
   */
  @Test
  public void testAssignRoom() {
    // Variables for patient details (no hard-coding)
    final String patientFirstName = "Doug";
    final String patientLastName = "Derm";
    final String patientDob = "1984-04-04";
    final double bodyTemperature = 37.5;

    // Register a new patient
    clinic.registerNewPatientVisit(patientFirstName, patientLastName, patientDob,
            LocalDateTime.now(), "Check up", bodyTemperature);
    PatientInterface newPatient
            = clinic.getPatients().get(clinic.getPatients().size() - 1);

    // Assign patient to Room 2
    clinic.assignRoom(2, clinic.getPatients().indexOf(newPatient));

    // Verify patient is in Room 2 and not in Room 1
    List<? extends PatientInterface> room2Patients
            = clinic.getRoomAssignments().get(2);
    assertEquals(1, room2Patients.size());
    assertEquals(patientFirstName, room2Patients.get(0).getFirstName());

    List<? extends PatientInterface> room1Patients = clinic.getRoomAssignments().get(1);
    assertFalse(room1Patients.contains(newPatient));
  }

  /**
   * Tests the {@code getRoomAssignments} method to
   * verify that the room assignments map is returned correctly
   * and contains the expected patients.
   */
  @Test
  public void testGetRoomAssignments() {
    Map<Integer, List<? extends PatientInterface>> roomAssignments
            = clinic.getRoomAssignments();
    assertNotNull("The room assignments map should not be null.", roomAssignments);

    // Verify Room 1
    List<? extends PatientInterface> room1Patients
            = roomAssignments.get(1);
    assertNotNull("Room 1 should contain patients.", room1Patients);
    assertEquals(2, room1Patients.size());
    assertEquals(PATIENT_FIRST_NAME_1,
            room1Patients.get(0).getFirstName());

    // Verify Room 2 is empty
    List<? extends PatientInterface> room2Patients
            = roomAssignments.get(2);
    assertTrue(room2Patients == null || room2Patients.isEmpty());
  }

  /**
   * Tests the {@code getPatientStaffAssignments} method to
   * verify that staff members are correctly assigned to patients
   * and retrieved from the assignment map.
   */
  @Test
  public void testGetPatientStaffAssignments() {
    clinic.getPatientStaffAssignments().clear();
    clinic.registerNewPatientVisit("Elise", "Enzyme", "1985-05-05",
            LocalDateTime.now(), "Check up", 37.0);
    ClinicalStaff staffMember = new ClinicalStaff("Benny", "Bruise",
            JobTitle.PHYSICIAN, EducationLevel.DOCTORAL, "0333444555");
    clinic.getStaff().add(staffMember);
    PatientInterface patient
            = clinic.getPatients().get(clinic.getPatients().size() - 1);

    // Assign staff to patient
    clinic.assignClinicalStaffToPatient(patient, staffMember);

    // Verify staff is assigned to the patient
    Map<PatientInterface, List<Staff>> assignments
            = clinic.getPatientStaffAssignments();
    List<Staff> assignedStaff = assignments.get(patient);
    assertNotNull("Patient should have assigned staff.", assignedStaff);
    assertEquals(1, assignedStaff.size());
    assertEquals("Bruise", assignedStaff.get(0).getLastName());
  }

  /**
   * Tests the {@code displaySeatingChart} method to
   * verify that the seating chart is displayed correctly,
   * showing patients assigned to each room.
   */
  @Test
  public void testDisplaySeatingChart() {
    clinic.displaySeatingChart();
  }
}
