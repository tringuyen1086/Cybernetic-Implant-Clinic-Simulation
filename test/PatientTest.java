import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import clinic.Patient;
import clinic.Room;
import clinic.RoomType;
import clinic.VisitStatus;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the {@link Patient} class.
 * This class verifies the correct behavior of the Patient class,
 * ensuring that patient details, room assignments,
 * and equality checks work as expected.
 */
public class PatientTest {
  // Common test data
  private static final String FIRST_NAME = "Aandi";
  private static final String LAST_NAME = "Acute";
  private static final int PATIENT_ID = 1;
  private static final String DOB = "1/1/1981";
  private static final VisitStatus VISIT_STATUS = VisitStatus.IN_PROGRESS;

  private static final String DIFFERENT_FIRST_NAME = "Beth";
  private static final String DIFFERENT_LAST_NAME = "Bunion";
  private static final int DIFFERENT_PATIENT_ID = 2;
  private static final String DIFFERENT_DOB = "2/2/1982";

  private static final String ROOM_NAME = "Front Waiting Room";
  private static final RoomType ROOM_TYPE = RoomType.WAITING;
  private static final int X1 = 0;
  private static final int Y1 = 0;
  private static final int X2 = 10;
  private static final int Y2 = 10;

  private Patient patient;
  private Patient samePatient;
  private Patient differentPatient;
  private Room room;

  /**
   * Sets up the test environment by creating patient objects before each test.
   */
  @Before
  public void setUp() {
    patient = new Patient(FIRST_NAME,
            LAST_NAME,
            PATIENT_ID,
            DOB,
            VISIT_STATUS);
    samePatient = new Patient(FIRST_NAME,
            LAST_NAME,
            PATIENT_ID,
            DOB,
            VISIT_STATUS);
    differentPatient = new Patient(DIFFERENT_FIRST_NAME,
            DIFFERENT_LAST_NAME,
            DIFFERENT_PATIENT_ID,
            DIFFERENT_DOB, VISIT_STATUS);
    room = new Room(ROOM_NAME, ROOM_TYPE, X1, Y1, X2, Y2);
  }

  /**
   * Tests the constructor and ensures that the {@link Patient} object
   * is created with valid parameters.
   */
  @Test
  public void testConstructor() {
    assertEquals(FIRST_NAME, patient.getFirstName());
    assertEquals(LAST_NAME, patient.getLastName());
    assertEquals(DOB, patient.getDateOfBirth());
  }

  /**
   * Tests the {@code getFullName} method
   * Verifies if the patient's full name is correctly returned.
   */
  @Test
  public void testGetFullName() {
    String expectedFullName = FIRST_NAME + " " + LAST_NAME;
    assertEquals(expectedFullName, patient.getFullName());
  }

  /**
   * Tests the {@code getDateOfBirth} method
   * Verifies if the patient's date of birth is correctly returned.
   */
  @Test
  public void testGetDateOfBirth() {
    assertEquals(DOB, patient.getDateOfBirth());
  }

  /**
   * Tests the {@code getDescription} method
   * Verifies that it returns the full name and date of birth as expected.
   */
  @Test
  public void testGetDescription() {
    String expectedDescription = FIRST_NAME + " " + LAST_NAME
            + " (Patient ID: " + PATIENT_ID + ", DOB: "
            + DOB + ", Status: " + VISIT_STATUS + ")\n"
            + "Visit Records: "
            + "No visit records available.";

    assertEquals(expectedDescription, patient.getDescription());
  }

  /**
   * Tests the {@code setAssignedRoom} and {@code getAssignedRoom}
   * Verifies the setting and getting assigned room for the patient.
   */
  @Test
  public void testAssignedRoom() {
    patient.setAssignedRoom(room);
    assertEquals(room, patient.getAssignedRoom());
  }

  /**
   * Tests both {@code equals} and {@code hashCode} to ensure
   * consistency between logically equal {@link Patient} objects and their hash codes.
   */
  @Test
  public void testEqualsAndHashCode() {
    // Test equality of the same object and equivalent objects
    assertTrue(patient.equals(samePatient));
    assertEquals(patient.hashCode(), samePatient.hashCode());

    // Test inequality with different patients
    assertFalse(patient.equals(differentPatient));
    assertNotEquals(patient.hashCode(), differentPatient.hashCode());

    // Test inequality with null and different class
    assertFalse(patient.equals(null)); // Null case
    assertFalse(patient.equals(new Object())); // Different type
  }
}
